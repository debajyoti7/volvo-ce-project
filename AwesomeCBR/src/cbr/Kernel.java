package cbr;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import mtree.MTree;

import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.xml.sax.SAXException;

import xml.XmlData;

/**
 * TODO Document me maddafakka!!!!
 * @author LordSpace
 *
 */
public class Kernel implements KernelIF {
	
	private String[] attributeNames;
	
	private final NaiveBayesClassifier classifier;
	
	private final DoubleMTree tree;
	
	// Holds a mapping between the case probabilities for 
	// DoublePoint values and the original DoublePoint value
	private final Map<Double, DoublePoint> dataPointsMap = new HashMap<>();
	
	/**
	 *
	 * @param dataFile The file containing the data points.
	 */
	public Kernel(File dataFile, double eps, int minPts) throws IOException, ParserConfigurationException, SAXException {
		this(populateFromXmlFile(dataFile, new HashSet<DoublePoint>()), eps, minPts);
	}
	
	public Kernel(int size, double eps, int minPts) {
		this(populateRandom(new HashSet<DoublePoint>(), 100), eps, minPts);
	}
	
	public Kernel(Set<DoublePoint> dataPoints, double eps, int minPts) {
		if (dataPoints.isEmpty())
			throw new IllegalArgumentException("empty data set");

		DBSCANClusterer<DoublePoint> dbScan = new DBSCANClusterer<>(eps, minPts);
		
		classifier = new NaiveBayesClassifier(dbScan, dataPoints);
		
		tree = new DoubleMTree();
		
		// TODO add the case probabilities for all data points
		for (DoublePoint doublePoint : dataPoints) {
			if (attributeNames == null) {
				attributeNames = new String[doublePoint.getPoint().length];
				for (int i = 0; i < attributeNames.length; i++) {
					attributeNames[i] = "attribute" + (i + 1);
				}
			}
			double classifiedDataPoint = classifier.caseProbability(doublePoint);
						
			dataPointsMap.put(classifiedDataPoint, doublePoint);
			
			tree.add(classifiedDataPoint);
		}
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
		
		System.out.println("kNN search on " + Arrays.toString(attributes));
		
		// These steps are followed:
		// Encapsulate the attributes array in a DoublePoint
		// Use the naive bayes classifier to get the case probability for the DoublePoint
		// Perform a M-tree nearest neighbor search
		// Convert the result to DoublePoint space via the stored data points map
		DoublePoint queryDoublePoint = new DoublePoint(attributes);
		
		double queryDouble = classifier.caseProbability(queryDoublePoint);
		
		MTree<Double>.Query query = tree.getNearestByLimit(queryDouble, k);
		List<DoublePoint> result = new ArrayList<>();
		for (MTree<Double>.ResultItem item : query) {
			DoublePoint dp = dataPointsMap.get(item.data);
			if (dp == null)
				throw new RuntimeException("No mapping for " + item.data);
			result.add(dp);
		}
		
		return result;
	}
	
	private static Set<DoublePoint> populateFromXmlFile(File dataFile, Set<DoublePoint> dataPoints) throws ParserConfigurationException, SAXException, IOException{
		XmlData xmlDataSet = new XmlData(dataFile);
		
		double[] dB1 = xmlDataSet.getSamples().get(0).getYValues();
		double[] dB2 = xmlDataSet.getSamples().get(1).getYValues();
		
		for (int i = 0; i < dB1.length; i++) {
			dataPoints.add(new DoublePoint(new double[]{dB1[i], dB2[i]}));
		}
		return dataPoints;
	}
	
	private static Set<DoublePoint> populateRandom(Set<DoublePoint> dataPoints, int size) {
		final Random RANDOM = new Random();
		for (int i = 0; i < size; i++) {
			dataPoints.add(new DoublePoint(new double[]{RANDOM.nextDouble(), RANDOM.nextDouble()}));
		}
		return dataPoints;
	}
}
