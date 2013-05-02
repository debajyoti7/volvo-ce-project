import java.awt.Color;

import java.util.*;
import javax.swing.*;

import org.math.plot.*;
import org.math.plot.plots.ScatterPlot;

//import com.rapidminer.operator.clustering.clusterer.KMeans;

class Data {
	
	private final static Random RANDOM = new Random();
	
	Data() {
		x = RANDOM.nextGaussian();
		y = RANDOM.nextGaussian();
	}
	double x, y;
	Color color;
}

public class Clustering {
	
	private final static Random RANDOM = new Random();
	
	Clustering() {
		//Data[] data = new Data[1000];
		
		double x[] = new double[1000];
		double y[] = new double[1000];
		
		for (int i = 0; i < x.length; i++) {
			x[i] = RANDOM.nextGaussian();
			y[i] = RANDOM.nextGaussian();
		}
		
		Plot2DPanel plot = new Plot2DPanel();
		/*
		ScatterPlot sp = new ScatterPlot("sp");
		int id = plot.addScatterPlot("Scatter Plot", x, y);
		plot.
		*/
		
		//KMeans kmeans = new KMeans()
		
		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("AI");
		frame.setContentPane(plot);
		frame.setSize(1000, 750);
		frame.setLocationRelativeTo(null);	// Center on screen
		frame.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Clustering();
	}
}
