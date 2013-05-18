package core;

import cbr.Kernel;
import cbr.KernelIF;

public class CBRProject {
	private String name;
	private String url;
	private KernelIF kernel;
	
	public CBRProject(String name, String url) {
		this.name = name;
		this.url = url;
		kernel = new Kernel();
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
