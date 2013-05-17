package cbr;

import java.util.*;

import org.apache.commons.math3.stat.descriptive.*;

import org.apache.commons.math3.ml.clustering.*;
import org.apache.commons.math3.ml.distance.*;

@SuppressWarnings("serial")
public class NaiveBayesCluster extends Cluster<DoublePoint> {
	
	private static int counter = 0;
	private final int ordinal;
	
	private final double[] mean;
	private final double[] variance;
	
//	private final double probability;
	
	public double[] mean() { 
		return mean; 
	}
	
	public double[] variance() { 
		return variance;
	}
	
	public String name() {
		return "Cluster" + ordinal;
	}
	
	public int ordinal() {
		return ordinal;
	}
	
	public NaiveBayesCluster(Cluster<DoublePoint> cluster) {
		ordinal = counter++;
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
	
//	double probability() {
//		return probability;
//	}
	
	private double conditionalFeatureProbability(DoublePoint dp, int index) {
		double denominator = Math.sqrt(2 * Math.PI) * Math.sqrt( variance[index] );
		
		double d = dp.getPoint()[index] - mean[index];
		
		return ( 1 / denominator ) * Math.pow( Math.E, -( (d * d) / (2 * variance[index]) ) ); 
	}
	
	// TODO use sum of log instead of multiplication 
	double conditionalProbability(DoublePoint dp, int totalClusteredDataPoints) {
		
		// calculate the product of all conditional probabilities for every feature
		double result = conditionalFeatureProbability(dp, 0);
		for (int i = 1; i < dp.getPoint().length; i++) {
			result *= conditionalFeatureProbability(dp, i); 
		}
		
		// multiply by the cluster probability (omitting the not classified noise data points)
		return result * ( (double)getPoints().size() / totalClusteredDataPoints );
	}
}
