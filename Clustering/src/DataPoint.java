import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class DataPoint {

	private final Random RANDOM = new Random();
	
	private final double[] values; 
	private int neighbors = -1;
	private int clusterId;

	DataPoint(int noOfValues) {
		this.values = new double[noOfValues];
		
		for (int i = 0; i < values.length; i++) {
			values[i] = RANDOM.nextDouble();
		}
	}
	
	DataPoint(double[] values) {
		this.values = values;
	}
	
	// properties
	
	int getNeighborCount() { 
		return neighbors;
	}/*
	void setNeighborCount(int value) {
		if (neighbors != -1)
			throw new IllegalStateException();
		this.neighbors = value;
	}*/
	
	int getClusterId() { 
		return clusterId;
	}
	void setClusterId(int value) {
		if (isMemberOfCluster())	// Cannot be member of another cluster 
			throw new IllegalStateException("was " + clusterId + " new value " + value);
		this.clusterId = value;
	}
	
	double[] getValues() {
		return values;
	}
	
	boolean isMemberOfCluster() {
		return clusterId > 0;
	}
//	boolean isNoiseMemberOfCluster() {
//		return clusterId > 0;
//	}
	boolean isVisited() {
		return neighbors != -1;		
	}	
	
	List<DataPoint> regionQuery(List<DataPoint> dataPoints, double eps) {
		List<DataPoint> result = new ArrayList<>();
		
		for (DataPoint dp : dataPoints) {			
			if (euclideanDistance(dp) < eps) {
				
				// Note that the data point itself will be in the result
				result.add(dp);
			}
		}
		neighbors = result.size() - 1;
		return result;
	}
	
	private double euclideanDistance(DataPoint other) {
		
		double squaredSum = 0.0;
		for (int i = 0; i < values.length; i++) {
			double d = values[i] - other.values[i];
			squaredSum += d*d;
		}
		return Math.sqrt(squaredSum);
	}
/*	
	void reset() {
		visited = false;
		reachability = Cluster.Density.None;
		classId = 0;
	}
	*/
	
	/*
	//Function to get random number
	private static readonly Random random = new Random();
	private static readonly object syncLock = new object();
	public static int RandomNumber(int min, int max)
	{
	    lock(syncLock) { // synchronize
	        return random.Next(min, max);
	    }
	}*/
}