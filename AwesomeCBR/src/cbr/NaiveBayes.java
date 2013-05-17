package cbr;

import java.util.*;

import org.apache.commons.math3.ml.clustering.*;
import org.apache.commons.math3.ml.distance.*;

public class NaiveBayes {
	
	public NaiveBayes(List<? extends Cluster<DoublePoint>> clusters, int sizeOfFeatureVector) {
		
		double[][] mean = new double[clusters.size()][];
		//List<double[]> stdDev = new ArrayList<>();
		
		for (int m = 0; m < clusters.size(); m++) {
			
			Cluster<DoublePoint> cluster = clusters.get(m);			
			mean[m] = new double[sizeOfFeatureVector];
			//mean.add(new Cluster<DoublePoint>());
			//stdDev.add(new Cluster<DoublePoint>());
			
			for (DoublePoint dataSet : cluster.getPoints()) {
				
				double[] featureVector = dataSet.getPoint();				
				for (int k = 0; k < featureVector.length; k++) {
					mean[m][k] += featureVector[k];
				}
			}			
		}
		
		for (int m = 0; m < mean.length; m++) {
			for (int k = 0; k < mean[0].length; k++) {
				mean[m][k] /= k;
			}			
		}
//		for (int i = 0; i < clusters.size(); i++) {
//			Cluster<DoublePoint> cluster = clu
//		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
