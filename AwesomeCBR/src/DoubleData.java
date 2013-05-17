import java.util.Arrays;

import mtree.DistanceFunctions.EuclideanCoordinate;

public class DoubleData implements EuclideanCoordinate {
		
	private final double[] values;
	private final int hashCode;
	
	DoubleData(double... values) {
		this.values = values;		
		this.hashCode = Arrays.hashCode(values);
	}
	
	@Override
	public int dimensions() {
		return values.length;
	}

	@Override
	public double get(int index) {
		return values[index];
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DoubleData) {
			DoubleData that = (DoubleData) obj;
			return Arrays.equals(values, that.values);
		}
		return false;
	}
	/*
	@Override
	public int compareTo(Data that) {
		int dimensions = Math.min(this.dimensions(), that.dimensions());
		for(int i = 0; i < dimensions; i++) {
			double v1 = this.values[i];
			double v2 = that.values[i];
			if(v1 > v2) {
				return +1;
			}
			if(v1 < v2) {
				return -1;
			}
		}
		
		if(this.dimensions() > dimensions) {
			return +1;
		}
		
		if(that.dimensions() > dimensions) {
			return -1;
		}
		
		return 0;
	}
	*/
}
