package xml;

import java.io.*;
import java.util.*;

import org.apache.commons.math3.ml.clustering.DoublePoint;

public class XmlReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		File directory = new File("C:/Users/Tobias/Desktop/AI/Rådata till Tomas");
		System.out.println(directory.exists());
		
		List<DoublePoint> dataPoints = new ArrayList<>();
		File[] subDirs = directory.listFiles();
		for (File file : subDirs) {
			try {
				read(file, dataPoints);
			} catch (Exception e) {
				System.err.println("Error reading from directory " + file.getName());
			}			
		}	
		System.out.println(dataPoints.size() + " number of feature vectors read");
		
		write(new File("data.csv"), dataPoints);
		
		List<DoublePoint> filtered = new ArrayList<>();
		for (DoublePoint doublePoint : dataPoints) {
			filtered.add(new DoublePoint(filter(doublePoint.getPoint(), 5)));
		}
		write(new File("filtered.csv"), filtered);
	}

	private static void read(File dir, List<DoublePoint> dataPoints) throws IOException {
		System.out.println(dir);
		
		File[] files = dir.listFiles();
//		if (files != null) {
			File dataFile;
			if (files[0].getName().endsWith(".xml"))
				dataFile = files[0];
			else
				dataFile = files[1];
			
			try (Scanner scanner = new Scanner(dataFile)) {
				for (int i = 0; i < 5; i++)
					scanner.nextLine();
						
				double[] values = new double[157];
				for (int i = 0; i < values.length; i++) {
					String line = scanner.nextLine();
					line = line.substring(33, 44);
					values[i] = Double.parseDouble(line);
//					System.out.print(values[i]);
				}
				dataPoints.add(new DoublePoint(values));
//			}
		}
	}
	
	private static double[] filter(double[] values, int interval) {
		
		double[] result = new double[values.length / interval];
		for (int i = 0; i < result.length; i++) {
			double sum = 0.0;
			int start = interval * i;
			for (int j = start; j < start + interval; j++) {
				sum += values[j];
			}
			result[i] = sum / interval;
		}
		return result;
	}
	
	private static void write(File file, List<DoublePoint> dataPoints) throws IOException {
		PrintWriter output = new PrintWriter(file);
		for (DoublePoint doublePoint : dataPoints) {
			output.write(trim(Arrays.toString(doublePoint.getPoint())));
			output.write("\n");
		}
		output.close();
	}
	
	private static String trim(String s) {
		return s.substring(1, s.length() - 1);
	}
}
