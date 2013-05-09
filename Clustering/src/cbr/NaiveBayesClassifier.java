package cbr;

import java.awt.Color;
import java.util.*;

import javax.swing.JFrame;

import org.apache.commons.math3.ml.clustering.*;
import org.math.plot.Plot2DPanel;

public class NaiveBayesClassifier {

	private final Set<DoublePoint> dataPoints;
	private final Set<DoublePoint> noise;
	private final List<NaiveBayesCluster> clusters = new ArrayList<>();
	
	public NaiveBayesClassifier(Clusterer<DoublePoint> clusterer, Collection<DoublePoint> dataPoints) {
		this.dataPoints = Collections.unmodifiableSet(new HashSet<>(dataPoints));
		this.noise = new HashSet<>(dataPoints);
				
		for (Cluster<DoublePoint> cluster : clusterer.cluster(dataPoints)) {						
			clusters.add(new NaiveBayesCluster(cluster));
		}
		
		for (Cluster<DoublePoint> c : clusters) {
			noise.removeAll(c.getPoints());
		}
	}
	
	public List<NaiveBayesCluster> clusters() {
		return clusters;
	}
	
	/**
	 * Returns an unmodifiable view of the data points used by this classifier.
	 */
	public Set<DoublePoint> dataPoints() {
		return dataPoints;
	}
	
	public Set<DoublePoint> noise() {
		return noise;
	}
	/*
	public double[] conditionalProbability(DoublePoint dataPoint, int index) {
		double[] result = new double[clusters.size()];
		double sumOfNormalizedValues = 0.0;
		
		for (int i = 0; i < clusters.size(); i++) {
			NaiveBayesCluster cluster = clusters.get(i); 
			result[i] = cluster.conditionalFeatureProbability(dataPoint, index);
			sumOfNormalizedValues += result[i];
			
			double clusterProbability = (double)cluster.getPoints().size() / ( dataPoints.size() - noise.size() );
			
			result[i] *= clusterProbability;
		}
		for (int i = 0; i < result.length; i++) {
			result[i] /= sumOfNormalizedValues;
		}
		return result;
	}
	*/
	public double caseProbability(DoublePoint dataPoint) {
		double result = 0.0;
		for (NaiveBayesCluster cluster : clusters) {
			int totalClusteredDataPoints = dataPoints.size() - noise.size();
			result += cluster.conditionalProbability(dataPoint, totalClusteredDataPoints);
		}
		return result;
	}
	
	public double[] conditionalClassProbability(DoublePoint dataPoint) {
		double[] result = new double[clusters().size()];
		
		double sum = 0.0;
		for (int i = 0; i < clusters.size(); i++) {
			
			int totalClusteredDataPoints = dataPoints.size() - noise.size();
			result[i] = clusters.get(i).conditionalProbability(dataPoint, totalClusteredDataPoints);
			sum += result[i];
		}
		for (int i = 0; i < clusters.size(); i++)
			result[i] /= sum;
		
		return result;
	}
	
	public static void main(String[] args) {
		
		final Random RANDOM = new Random();
		final int SIZE = 100;
		Set<DoublePoint> dataPoints = new HashSet<>();
		for (int i = 0; i < SIZE; i++) {
			dataPoints.add(new DoublePoint(new double[]{RANDOM.nextDouble(), RANDOM.nextDouble()}));
		}
		double eps = 0.12;
		int minPts = 4;
		DBSCANClusterer<DoublePoint> dbScan = new DBSCANClusterer<>(eps, minPts);
		
		NaiveBayesClassifier classifier = new NaiveBayesClassifier(dbScan, dataPoints);
		
//		try (FileWriter writer = new FileWriter("cluster.csv")) {
//			
//			for (Cluster<DoublePoint> cluster : dbScan.cluster(dataPoints)) {
//				
//				NaiveBayesCluster nb = new NaiveBayesCluster(cluster);			
//				naiveBayes.add(nb);
//				 
//				writer.write(nb.name() + "," + Arrays.toString(nb.mean()) + "\n");			
//				writer.write(nb.name() + "," + Arrays.toString(nb.variance()) + "\n");
//			}
//		}
		
		Plot2DPanel plot = new Plot2DPanel();
		plot.setFixedBounds(0, 0.0, 1.0);
		plot.setFixedBounds(1, 0.0, 1.0);
			
		for (NaiveBayesCluster c : classifier.clusters()) {			
			plot(plot, c.name(), Color.RED, c.getPoints());
		}

		plot(plot, "Noise", Color.BLUE, classifier.noise());
		
		DoublePoint lookup = new DoublePoint(new double[]{RANDOM.nextDouble(), RANDOM.nextDouble()});
		plot(plot, "Lookup", Color.PINK, Collections.singleton(lookup));
		
		for (int i = 0; i < lookup.getPoint().length; i++) {
			double[] probability = classifier.conditionalProbability(lookup, i);
			System.out.println("probability for index: " + i + ": " + Arrays.toString(probability)); 
		}
		
		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("AI");
		frame.add(plot);
		frame.setSize(1000, 750);
		frame.setLocationRelativeTo(null);	// Center on screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private static void plot(Plot2DPanel plot, String name, Color color, Collection<DoublePoint> dataPoints) {
		double xy[][] = new double[dataPoints.size()][];
		int i = 0;
		for (DoublePoint dp : dataPoints) {
			xy[i++] = dp.getPoint();
		}
		if (xy.length > 0)
			plot.addScatterPlot(name, color, xy);
	}
}
