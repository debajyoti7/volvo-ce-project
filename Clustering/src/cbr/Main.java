package cbr;

import java.awt.Color;
import java.io.*;
import java.util.*;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

import org.apache.commons.math3.ml.clustering.*;

public class Main {
	
	private static final Random RANDOM = new Random();

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

		final int SIZE = 100;
		Set<DoublePoint> dataPoints = new HashSet<>();
		for (int i = 0; i < SIZE; i++) {
			dataPoints.add(new DoublePoint(new double[]{RANDOM.nextDouble(), RANDOM.nextDouble()}));
		}
		double eps = 0.12;
		int minPts = 4;
		DBSCANClusterer<DoublePoint> dbScan = new DBSCANClusterer<>(eps, minPts);
		
		List<NaiveBayesCluster> naiveBayes = new ArrayList<>();
		
		try (FileWriter writer = new FileWriter("cluster.csv")) {
			
			for (Cluster<DoublePoint> cluster : dbScan.cluster(dataPoints)) {
				
				NaiveBayesCluster nb = new NaiveBayesCluster(cluster, dataPoints.size());			
				naiveBayes.add(nb);
				 
				writer.write(nb.name() + "," + Arrays.toString(nb.mean()) + "\n");			
				writer.write(nb.name() + "," + Arrays.toString(nb.variance()) + "\n");
			}
		}
		
		Plot2DPanel plot = new Plot2DPanel();
		plot.setFixedBounds(0, 0.0, 1.0);
		plot.setFixedBounds(1, 0.0, 1.0);
			
		for (NaiveBayesCluster nbc : naiveBayes) {
			dataPoints.removeAll(nbc.getPoints());
			
			plot(plot, nbc.name(), Color.RED, nbc.getPoints());
		}

		plot(plot, "Noise", Color.BLUE, dataPoints);
		
		DoublePoint lookup = new DoublePoint(new double[]{RANDOM.nextDouble(), RANDOM.nextDouble()});
		plot(plot, "Lookup", Color.PINK, Collections.singleton(lookup));
		
		for (NaiveBayesCluster naiveBayesCluster : naiveBayes) {
			for (int i = 0; i < lookup.getPoint().length; i++) {
				double probability = naiveBayesCluster.normalizedValue(lookup, i);
				System.out.println("Cluster" + naiveBayesCluster.ordinal() + ", index: " + i + ", probability: " + probability); 
			}
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