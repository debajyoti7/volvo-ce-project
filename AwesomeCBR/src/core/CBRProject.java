package core;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
	
	public void initKernel(double arg0, int arg1) throws IOException, ParserConfigurationException, SAXException {
		kernel = new Kernel(new File(url), arg0, arg1);
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
