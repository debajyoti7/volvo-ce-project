package cbr;

public interface PersistencyIF extends PersistencyQueryIF {
	
	/**
	 * Puts the given case in the persistent storage.
	 * @param primaryKey	the primary key for the attributes
	 * @param attributes	attributes for the primary key
	 * @throws 	NullPointerException
	 * 			if primaryKeys is null
	 */
	void putCase(int primaryKey, double[] attributes);	
}
