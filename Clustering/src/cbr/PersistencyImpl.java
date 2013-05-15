package cbr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersistencyImpl implements PersistencyIF {
	
	private final HashMap<Integer, double[]> storage = new HashMap<>();
	private final String[] attributeNames;
	
	public PersistencyImpl(String[] attributeNames) {
		this.attributeNames = attributeNames;
	}

	/** {@inheritDoc} */
	@Override
	public String[] attributeNames() {
		return attributeNames.clone();
	}
	
	/** {@inheritDoc} */
	@Override
	public double[] getCase(int primaryKey) {
		double[] result = storage.get(primaryKey);
		if (result == null)
			throw new IllegalArgumentException("no primary key of value: " + primaryKey);
		return result;
	}

	/**
	 * Retrieves the cases for the given primary keys.
	 * @param primaryKeys	The primary keys
	 * @return				The list of retrieved cases
	 * @throws 	NullPointerException	
	 * 			if primaryKeys is null
	 * @throws	IndexOutOfBoundsException
	 *			if the length of the primaryKeys array is zero
	 * @throws	IllegalArgumentException
	 *			if primaryKeys contains an unknown primary key
	 */
	public List<double[]> getCases(int... primaryKeys) {
		List<double[]> result = new ArrayList<>();
		for (int pk : primaryKeys) {
			result.add(getCase(pk));
		}
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public void putCase(int primaryKey, double[] attributes) {
		storage.put(primaryKey, attributes);
	}	
}
