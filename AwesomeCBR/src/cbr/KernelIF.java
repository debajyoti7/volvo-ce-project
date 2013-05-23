package cbr;

import java.util.List;

import org.apache.commons.math3.ml.clustering.DoublePoint;

/**
 * Interface for case clustering and probability classification.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/DBSCAN">DBSCAN on Wikipedia</a>
 * @see <a href="http://en.wikipedia.org/wiki/M-tree">M-tree on Wikipedia</a> 
 */
public interface KernelIF {

	/**
	 * Returns the names of the attributes Initializes the kernel from the given file of cases.  
	 */
	String[] getAttributeNames();
	
	/**
	 * Returns the <code>NaiveBayesClassifier</code>.
	 */
	NaiveBayesClassifier getClassifier();
	
	/**
	 * Performs a k-Nearest Neighbor search 
	 * for the given k-value and attributes.
	 * 
	 * @param k				Required number of neighbors.
	 * @param attributes	The feature vector.
	 * @return	The found cases
	 * @throws 	NullPointerException
	 *			if attributes is null
	 * @throws	IllegalArgumentException
	 *			if attributes contains any {@link java.lang.Double#NaN Double.NaN}
	 * @throws	IndexOutOfBoundsException
	 * 			if k < 1 or if the attributes array is of zero length
	 */
	List<DoublePoint> kNNQuery(int k, double... attributes); 
}