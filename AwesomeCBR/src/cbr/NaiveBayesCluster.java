package cbr;

import org.apache.commons.math3.ml.clustering.*;

/**
 * Encapsulates a cluster of cases 
 */
@SuppressWarnings("serial")
public class NaiveBayesCluster extends Cluster<DoublePoint> {
	

	private final int ordinal;	
	private final double[] mean;
	private final double[] variance;
	
//	public double[] mean() { 
//		return mean; 
//	}
//	
//	public double[] variance() { 
//		return variance;
//	}
	
	/**
	 * Returns the name of the Naive Bayes cluster. 
	 */
	public String name() {
		return "Cluster" + ordinal;
	}
	
	/**
	 * Might be used as an identification for this cluster.
	 */
	public int ordinal() {
		return ordinal;
	}
	
	/**
	 * Creates a NaiveBayesCluster.
	 * 
	 * @param ordinal must be unique
	 * @param cluster cluster to base this on
	 */
	NaiveBayesCluster(int ordinal, Cluster<DoublePoint> cluster) {
		this.ordinal = ordinal;
		//probability = (double)cluster.getPoints().size() / totalNumberOfDataPoints;
		
		// Add all data points from the given cluster to this cluster
		getPoints().addAll(cluster.getPoints());
		
		// initialization of the mean and standard deviation arrays
		// an empty cluster will throw a IndexOutOfBoundsException 
		mean = new double[getPoints().get(0).getPoint().length];
		variance = new double[getPoints().get(0).getPoint().length];
		
		//SummaryStatistics ss[] = new SummaryStatistics[getPoints().get(0).getPoint().length];
				
		for (DoublePoint dataSet : getPoints()) {
			
			double[] featureVector = dataSet.getPoint();				
			for (int k = 0; k < featureVector.length; k++) {
				mean[k] += featureVector[k];
			}
		}			
		
		for (int k = 0; k < mean.length; k++) {
			mean[k] /= getPoints().size();
		}
		
		for (DoublePoint dataSet : getPoints()) {
			
			double[] featureVector = dataSet.getPoint();				
			for (int k = 0; k < featureVector.length; k++) {
				double d = featureVector[k] - mean[k];
				variance[k] += d*d;
			}
		}
		
		for (int k = 0; k < mean.length; k++) {
			variance[k] /= getPoints().size();
		}
	}
	
	private double conditionalFeatureProbability(DoublePoint dp, int index) {
		double denominator = Math.sqrt(2 * Math.PI) * Math.sqrt( variance[index] );
		
		double d = dp.getPoint()[index] - mean[index];
		
		return ( 1 / denominator ) * Math.pow( Math.E, -( (d * d) / (2 * variance[index]) ) ); 
	}
	
	/**
	 * Calculates the product over conditional probability for a given case.
	 * 
	 * <pre>
	 * &#8719 = [ ( p(x1|c) * p(x2|c) * ... * p(xK|c) ) * p(c) ] 
	 * </pre>
	 * <p>
	 * Calculations are performed via sum of log to not loose number precision.
	 * 
	 * @param dp the given case
	 * @param totalClusteredDataPoints 
	 * 			the total number of data points (<em>omitting</em> noise data!)
	 */ 
	public double conditionalProbability(DoublePoint dp, int totalClusteredDataPoints) {
		
		// calculate the product of all conditional probabilities for every feature
		double sum = conditionalFeatureProbability(dp, 0);
		for (int i = 1; i < dp.getPoint().length; i++) {
			sum += Math.log(conditionalFeatureProbability(dp, i));
		}
		
		// multiply by the cluster probability (omitting the not classified noise data points)
		return Math.pow(Math.E, sum) * ( (double)getPoints().size() / totalClusteredDataPoints );
	}
}
