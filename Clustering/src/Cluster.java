import java.util.*;

class Cluster {
	
	static final int NOISE = -1;
	
	private static int instanceCounter = 1;
	
	private final int ordinal;
	private final List<DataPoint> members = new ArrayList<>();
	
	Cluster() {
		ordinal = instanceCounter++;
	}
	
	void add(DataPoint dataPoint) {
		dataPoint.setClusterId(ordinal);
		members.add(dataPoint);
	}
	
	int id() {
		return ordinal;
	}
	
	List<DataPoint> members() {
		return members;
	}
	
	double[][] toArray() {
		double[][] result = new double[members.size()][];
		for (int i = 0; i < result.length; i++) {
			result[i] = members.get(i).getValues();
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "Cluster " + ordinal;
	}
}
