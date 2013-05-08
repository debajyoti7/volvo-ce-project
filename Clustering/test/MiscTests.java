import static org.junit.Assert.*;

import java.util.*;

import org.apache.commons.math3.stat.*;

import org.junit.Test;


public class MiscTests {

	@Test
	public void testMean() {
		List<DataPoint> dataPoints = new ArrayList<>();
		
		dataPoints.add(new DataPoint(new double[]{1}));
		dataPoints.add(new DataPoint(new double[]{2}));
		dataPoints.add(new DataPoint(new double[]{3}));
		dataPoints.add(new DataPoint(new double[]{4}));
		dataPoints.add(new DataPoint(new double[]{5}));
		
		double[] result = Main.mean(dataPoints);
		
		assertEquals(1, result.length);
		assertEquals(3.0, result[0], 0.00005);
		
		double[][] values = new double[dataPoints.get(0).getValues().length][dataPoints.size()];
		for (int j = 0; j < values.length; j++) {
			for (int i = 0; i < values[0].length; i++) {
				values[j][i] = dataPoints.get(i).getValues()[j];
			}
			assertEquals(result[j], StatUtils.mean(values[j]), 0.00001);
		}
		
	}

}
