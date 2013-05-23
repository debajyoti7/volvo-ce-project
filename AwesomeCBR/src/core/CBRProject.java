package core;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.xml.sax.SAXException;

import xml.Utils;

import cbr.Kernel;
import cbr.KernelIF;

public class CBRProject {
	private String name;
	private String url;
	private KernelIF kernel;
	
	public CBRProject(String name, String url) throws IOException, ParserConfigurationException, SAXException {
		this.name = name;
		this.url = url;
		//kernel = new Kernel(new File(url), 0.0, 1);
	}
	
	public void initKernel(double eps, int minPts) throws IOException, ParserConfigurationException, SAXException {
		Set<DoublePoint> dataPoints = Utils.populateFromXmlFile(new File(url), new HashSet<DoublePoint>());
		kernel = new Kernel(dataPoints, eps, minPts);
	}
	
	public String getName() {
		return name;
	}
	
	public String getURL() {
		return url;
	}
	
	public KernelIF getKernel() {
		return kernel;
	}
}
