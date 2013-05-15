package cbr;

/**
 * Read only persistence interface. 
 */
public interface PersistencyQueryIF {

	/**
	 * Returns the names of the attributes
	 */
	String[] attributeNames();
	
	/**
	 * Retrieves the case for the given primary key.
	 * @param primaryKey	The primary key
	 * @return				The retrieved cases
	 * @throws	IllegalArgumentException
	 *			if primaryKeys contains an unknown primary key
	 */
	double[] getCase(int primaryKey);
}
