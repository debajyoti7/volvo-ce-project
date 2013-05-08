import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.*;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;


public class Main {
	
	static double[] mean(List<DataPoint> dataPoints) {
		double[] result = new double[dataPoints.get(0).getValues().length];
		for (DataPoint dataPoint : dataPoints) {
			double[] values = dataPoint.getValues();
			for (int i = 0; i < values.length; i++) {
				result[i] += values[i];
			}
		}
		for (int i = 0; i < result.length; i++) {
			result[i] /= dataPoints.size();
		}
		return result;
	}
	
	static double[] stdDev(List<DataPoint> dataPoints) {
		double[] result = new double[dataPoints.get(0).getValues().length];
		
		double[] mean = mean(dataPoints);
		for (DataPoint dataPoint : dataPoints) {
			double[] values = dataPoint.getValues();
			for (int i = 0; i < values.length; i++) {
				double d = mean[i] - values[i];
				result[i] += d*d;
			}
		}
		for (int i = 0; i < result.length; i++) {
			result[i] = Math.sqrt(result[i]);
		}
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final int SIZE = 100;
		List<DataPoint> dataPoints = new ArrayList<>();
		for (int i = 0; i < SIZE; i++) {
			dataPoints.add(new DataPoint(2));
		}
		double eps = 0.12;
		int minPts = 4;
		DBSCAN dbScan = new DBSCAN(dataPoints);
		LinkedList<Cluster> clusters = dbScan.buildClusters(eps, minPts);
		
		Plot2DPanel plot = new Plot2DPanel();
		plot.setFixedBounds(0, 0.0, 1.0);
		plot.setFixedBounds(1, 0.0, 1.0);
				
		for (Cluster cluster : clusters) {
			plot.addScatterPlot(cluster.toString(), Color.BLACK, cluster.toArray());
		}

		List<DataPoint> noise = dbScan.noise();
		double xy[][] = new double[noise.size()][];
		for (int i = 0; i < xy.length; i++) {			
			xy[i] = noise.get(i).getValues();
		}
		if (xy.length > 0)
			plot.addScatterPlot("Noise", Color.BLUE, xy);
		
		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("AI");
		frame.add(plot);
		frame.setSize(1000, 750);
		frame.setLocationRelativeTo(null);	// Center on screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
