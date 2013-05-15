package core;

import java.util.ArrayList;
import java.util.List;

public class CBRProject {
	private String name;
	private String dataset;
	private List<CBRAttribute> attributes;
	
	public CBRProject() {}
	
	public CBRProject(String _name, String _dataset_url) {
		this.name = _name;
		this.dataset = _dataset_url;
	}

	public void setName(String _name) {
		this.name = _name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setDataset(String _dataset) {
		this.dataset = _dataset;
	}
	
	public String getDataset() {
		return this.dataset;
	}
	
	public void addAttribute(CBRAttribute a) {
		attributes.add(a);
	}
	
	public List<CBRAttribute> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(ArrayList<CBRAttribute> arrayList) {
		attributes = arrayList;
	}
}
