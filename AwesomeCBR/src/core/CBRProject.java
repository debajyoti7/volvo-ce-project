package core;

import java.util.ArrayList;
import java.util.List;

public class CBRProject {
	// Properties
	private int project_id;
	private String name;
	private String url;
	private List<Attribute> attributes;
	private String comments;
	private Dataset dataset;
	private DBConnector dbc;
	
	// Constructors.
	public CBRProject() {
		attributes = new ArrayList<Attribute>();
		//this is it!!
	}
	
	public CBRProject(String _name) {
		this();
		this.name = _name;
	}
	
	// Actions.
	public void setName(String _name) {
		this.name = _name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addAttribute(Attribute a) {
		attributes.add(a);
	}
}
