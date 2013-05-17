package cbr;

import java.util.Iterator;

import mtree.DistanceFunction;
import mtree.utils.Pair;

import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

public class Utils {

	private Utils() {}
/*	
	public static final DistanceMeasure EUCLIDEAN = new EuclideanDistance(); 
	
	public static final DistanceFunction<DoublePoint> DISTANCE = new DistanceFunction<DoublePoint>() {
		
		@Override
		public double calculate(DoublePoint data1, DoublePoint data2) {
			return EUCLIDEAN.compute(data1.getPoint(), data2.getPoint());
		}
	};
	
	private static int compareTo(DoublePoint d1, DoublePoint d2) {
		for(int i = 0; i < d1.getPoint().length; i++) {
			double v1 = d1.getPoint()[i];
			double v2 = d2.getPoint()[i];
			if(v1 > v2) {
				return +1;
			}
			if(v1 < v2) {
				return -1;
			}
		}		
		return 0;
	}
	
	static Pair<DoublePoint> minMax(Iterable<DoublePoint> items) {
		Iterator<DoublePoint> iterator = items.iterator();
		if(!iterator.hasNext()) {
			return null;
		}
		
		DoublePoint min = iterator.next();
		DoublePoint max = min;
		
		while(iterator.hasNext()) {
			DoublePoint item = iterator.next();
			
			//double d1 = euclideanDistance.compute(item.getPoint(), min.getPoint());
			
			if(compareTo(item, min) < 0) {
				min = item;
			}
			if(compareTo(item, max) > 0) {
				max = item;
			}
		}
		
		return new Pair<DoublePoint>(min, max);
	}
	*/
}