import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//http://en.wikipedia.org/wiki/DBSCAN
public class DBSCAN {

//	public static LinkedList<Cluster> execute(List<DataPoint> dataPoints, double eps, int minPts) {
//		return new DBSCAN(dataPoints).buildClusters(eps, minPts);
//	}

	private List<DataPoint> dataPoints;
		
	DBSCAN(List<DataPoint> dataPoints) {
		this.dataPoints = dataPoints;
	}
	
	LinkedList<Cluster> buildClusters(double eps, int minPts) {
		LinkedList<Cluster> result = new LinkedList<>(); // LinkedList got the useful getLast() method
		
		for (DataPoint dataPoint : dataPoints) {
			if (!dataPoint.isVisited()) {
				
				List<DataPoint> neighbors = dataPoint.regionQuery(dataPoints, eps);
				
				if (neighbors.size() < minPts) {
					dataPoint.setClusterId(Cluster.NOISE);
				}
				else {
					result.addLast(new Cluster());
					//dataPoint.reachability = Cluster.Density.Connected;
					result.getLast().add(dataPoint); 
					expandCluster(neighbors, result.getLast(), eps, minPts);
				}
			}
		}
		
		System.out.println("Number of clusters: " + result.size());
		System.out.println("Number of noise data points: " + noise().size());
		
		return result;
	}
	
	List<DataPoint> noise() {
		List<DataPoint> result = new ArrayList<>();
		for (DataPoint dataPoint : dataPoints) {
			if (dataPoint.getClusterId() == Cluster.NOISE)
				result.add(dataPoint);
		}
		return result;
	}	
	
	private void expandCluster(List<DataPoint> NeighborPts, Cluster C, double eps, int minPts ) {
		
		// A for each loop throws a ConcurrentModificationException
		// sin NeighborPts are updated with new data points
		for (int i = 0; i < NeighborPts.size(); i++) {
			DataPoint dataPoint = NeighborPts.get(i);
			if (!dataPoint.isVisited()) { 
				
				List<DataPoint> newNeighbors = dataPoint.regionQuery(dataPoints, eps);
				if (newNeighbors.size() >= minPts) {
					//dataPoint.reachability = Cluster.Density.Connected;
					NeighborPts.addAll(newNeighbors);
				}
			}
			if (dataPoint.getClusterId() < 1) {
				
				// The given data point did not have enough neighbors for it 
				// to be density-connected; therefore it is density reachable
				//if (dataPoint.reachability == Cluster.Density.None)
				//	dataPoint.reachability = Cluster.Density.Reachable;
				
				C.add(dataPoint);
			}
		}
	}
	/*
	private List<DataPoint> reqionQuery(DataPoint dataPoint, double eps) {
		
		List<DataPoint> result = new ArrayList<>();
		
		for (DataPoint dp : dataPoints) {			
			if (dataPoint.distance(dp) < eps) {
				
				// Note that the data point itself will be in the result
				result.add(dp);
			}
		}
		dataPoint.neighbors = result.size() - 1;
		return result;
	}
	
	void reset() {
		for (DataPoint DataPoint : dataPoints) {
			DataPoint.reset();
		}
	}*/	
}
