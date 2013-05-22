package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Collection;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.math.plot.Plot2DPanel;

import cbr.NaiveBayesClassifier;
import cbr.NaiveBayesCluster;

@SuppressWarnings("serial")
public class ClassifierPanel extends Plot2DPanel {

	public ClassifierPanel() {}
	
	public ClassifierPanel(NaiveBayesClassifier classifier) {
		setFixedBounds(0, 0.0, 1.0);
		setFixedBounds(1, 0.0, 1.0);
		
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//this.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		
		//this.add(new JLabel("123"));
			
		for (NaiveBayesCluster c : classifier.clusters()) {
			plot(c.name(), Color.RED, c.getPoints());
		}
	
		plot("Noise", Color.BLUE, classifier.noise());
	}
	
	public void plot(String name, Color color, Collection<DoublePoint> dataPoints) {
		double xy[][] = new double[dataPoints.size()][];
		int i = 0;
		for (DoublePoint dp : dataPoints) {
			xy[i++] = dp.getPoint();
		}
		if (xy.length > 0)
			this.addScatterPlot(name, color, xy);
	}
	
	public void plot(String name, Color color, DoublePoint dataPoint) {
		plot(name, color, Collections.singleton(dataPoint));
	}
}
