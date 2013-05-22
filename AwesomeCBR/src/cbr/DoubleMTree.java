package cbr;

import java.util.Iterator;
import java.util.Set;
import mtree.*;
import mtree.utils.Pair;

/**
 * TODO make appropriate updates to create the index structure by the probability values
 */
public class DoubleMTree extends MTree<Double> {		
	
	private static final DistanceFunction<Double> DISTANCE = new DistanceFunction<Double>() {
		
		@Override
		public double calculate(Double data1, Double data2) {
			return Math.abs(data1.doubleValue() - data2.doubleValue());
		}
	};
	
	private static final PromotionFunction<Double> DETERMINISTIC = new PromotionFunction<Double>() {
		@Override
		public Pair<Double> process(Set<Double> dataSet, DistanceFunction<? super Double> distanceFunction) {
			return minMax(dataSet);		
		}		
	};
	
	private static Pair<Double> minMax(Iterable<Double> items) {
		Iterator<Double> iterator = items.iterator();
		if(!iterator.hasNext()) {
			return null;
		}
		
		Double min = iterator.next();
		Double max = min;
		
		while(iterator.hasNext()) {
			Double item = iterator.next();
			
			if(item < min)
				min = item;
			else if (item > max)
				max = item;			
		}
		
		return new Pair<Double>(min, max);
	}
	
	public DoubleMTree() {
		super(DISTANCE, new ComposedSplitFunction<Double>(DETERMINISTIC, new PartitionFunctions.BalancedPartition<Double>()));		
	}
}