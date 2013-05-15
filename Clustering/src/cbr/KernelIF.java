package cbr;

public interface KernelIF {

	// TODO File is too specific?! Use URL?!
	
	/**
	 * Returns the names of the attributes Initializes the kernel from the given file of data points.  
	 * 
	 * @return	The named attributes for the data set
	 */
	String[] attributeNames();
	
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
	double[][] kNNQuery(int k, double... attributes); 
}