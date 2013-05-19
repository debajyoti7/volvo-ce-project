package cbr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import mtree.MTree;

import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.ml.clustering.DoublePoint;

/**
 * TODO
 * Document me maddafakka!!!!
 * @author LordSpace
 *
 */
public class Kernel implements KernelIF {
	
	private final String[] attributeNames;
	
	private final NaiveBayesClassifier classifier;
	
	private final DoublePointMTree tree;
	
	/**
	 *
	 * @param dataSet	The file containing the data points.
	 */
	public Kernel(File dataSet) throws IOException {
		// TODO implement
		throw new RuntimeException("not implemented yet");
	}
	
	public Kernel() {
		// TODO make real
		attributeNames = new String[]{"attribute1", "attribute2"};
		
		final Random RANDOM = new Random();
		final int SIZE = 100;
		Set<DoublePoint> dataPoints = new HashSet<>();
		for (int i = 0; i < SIZE; i++) {
			dataPoints.add(new DoublePoint(new double[]{RANDOM.nextDouble(), RANDOM.nextDouble()}));
		}
		
		// TODO how to configure these values
		double eps = 0.12;
		int minPts = 4;
		DBSCANClusterer<DoublePoint> dbScan = new DBSCANClusterer<>(eps, minPts);
		
		classifier = new NaiveBayesClassifier(dbScan, dataPoints);
		
		tree = new DoublePointMTree();
		
		// TODO add the case probabilities for all data points
//		for (DoublePoint doublePoint : dataPoints) {
//			classifier.
//			tree.add(doublePoint);
//		}		
	}
	
	@Override
	public String[] getAttributeNames() {
		return attributeNames;
	}	

	@Override
	public NaiveBayesClassifier getClassifier() {
		return classifier;
	}

	@Override
	public List<DoublePoint> kNNQuery(int k, double... attributes) {
		DoublePoint queryData = new DoublePoint(attributes);
		
		MTree<DoublePoint>.Query query = tree.getNearestByLimit(queryData, k);
		List<DoublePoint> result = new ArrayList<>();
		for (MTree<DoublePoint>.ResultItem item : query) {
			result.add(item.data);
		}
		
		return result;
	}}
