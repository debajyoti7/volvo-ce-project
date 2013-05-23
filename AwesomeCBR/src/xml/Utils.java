package xml;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.xml.sax.SAXException;

/**
 * Consists solely of static utility methods.
 */
public class Utils {

	// Suppress default constructor
	private Utils() {}
	
	/**
	 * Populates data from an XML file.
	 * @param dataFile points out the data
	 * @param dataPoints to be populated by the read data
	 * @return the populated dataPoints parameter
	 */
	public static Set<DoublePoint> populateFromXmlFile(File dataFile, Set<DoublePoint> dataPoints) throws ParserConfigurationException, SAXException, IOException{		
		System.out.println("Reading data from " + dataFile.getCanonicalPath());
		XmlData xmlDataSet = new XmlData(dataFile);
		
		double[] dB1 = xmlDataSet.getSamples().get(0).getYValues();
		double[] dB2 = xmlDataSet.getSamples().get(1).getYValues();
		
		for (int i = 0; i < dB1.length; i++) {
			dataPoints.add(new DoublePoint(new double[]{dB1[i], dB2[i]}));
		}
		return dataPoints;
	}
}
