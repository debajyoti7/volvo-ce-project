package cbr;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import mtree.MTree;

import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.ml.clustering.DoublePoint;

/**
 * Implementation of the KernelIF. Refer to the constructors and the
 * interface of how to use this class.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/DBSCAN">DBSCAN on Wikipedia</a>
 * @see <a href="http://en.wikipedia.org/wiki/M-tree">M-tree on Wikipedia</a>
 */
public class Kernel implements KernelIF {
	
	private String[] attributeNames;
	
	private final NaiveBayesClassifier classifier;
	
	private final DoubleMTree tree;
	
	// Holds a mapping between the case probabilities for 
	// DoublePoint values and the original DoublePoint value
	private final Map<Double, DoublePoint> dataPointsMap = new HashMap<>();
	
	/**
	 * Creates random 2D data points of a given size, useful constructor for test purposes.
	 * @param size number of random samples in the produced data
	 * @param eps the radius for the DBSCAN neighbor search
	 * @param minPts the minimum number of neighboring data points
	 * 				 for the DBSCAN to create a cluster
	 */
	public Kernel(int size, double eps, int minPts) {
		this(populateRandom(new HashSet<DoublePoint>(), 100), eps, minPts);
	}
	
	/**
	 * Creates a kernel from the given data points.
	 * @param dataPoints 
	 * @param eps the radius for the DBSCAN neighbor search
	 * @param minPts the minimum number of neighboring data points
	 * 				 for the DBSCAN to create a cluster
	 */
	public Kernel(Set<DoublePoint> dataPoints, double eps, int minPts) {
		if (dataPoints.isEmpty())
			throw new IllegalArgumentException("empty data set");

		DBSCANClusterer<DoublePoint> dbScan = new DBSCANClusterer<>(eps, minPts);
		
		classifier = new NaiveBayesClassifier(dbScan, dataPoints);
		
		tree = new DoubleMTree();
		
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
		
		//System.out.println("kNN search on " + Arrays.toString(attributes));
		
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
				
		try {
			String fileName = new SimpleDateFormat("yyyyMMddhhmmss'_kNNQuery.csv'").format(new Date());
			try (PrintWriter out = new PrintWriter(fileName)) {
				out.println(trim(Arrays.toString(attributes)));
				for (DoublePoint dp : result) {
					out.println(trim(Arrays.toString(dp.getPoint())));
				}
			}
			System.out.println("kNN search stored in file: " + new File(fileName).getCanonicalPath());
		} catch (Exception e) {
			System.err.println("Error writing to file");
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static String trim(String s) {
		if (s.length() < 2)
			return s;
		return s.substring(1, s.length() - 1);
	}
	
//	private static Set<DoublePoint> populateFromXmlFile(File dataFile, Set<DoublePoint> dataPoints) throws ParserConfigurationException, SAXException, IOException{		
//		System.out.println("Reading data from " + dataFile.getCanonicalPath());
//		XmlData xmlDataSet = new XmlData(dataFile);
//		
//		double[] dB1 = xmlDataSet.getSamples().get(0).getYValues();
//		double[] dB2 = xmlDataSet.getSamples().get(1).getYValues();
//		
//		for (int i = 0; i < dB1.length; i++) {
//			dataPoints.add(new DoublePoint(new double[]{dB1[i], dB2[i]}));
//		}
//		return dataPoints;
//	}
	
	private static Set<DoublePoint> populateRandom(Set<DoublePoint> dataPoints, int size) {
		final Random RANDOM = new Random();
		for (int i = 0; i < size; i++) {
			dataPoints.add(new DoublePoint(new double[]{RANDOM.nextDouble(), RANDOM.nextDouble()}));
		}
		return dataPoints;
	}
}
