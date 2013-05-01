package core;

import java.util.List;

public class CBRProject {
	// Properties
	private int project_id;
	private String name;
	private List<Attribute> attributes;
	private String comments;
	private Dataset dataset;
	private DBConnector dbc;
	
	// Constructors.
	public CBRProject(String _name) {
		this.name = _name;
	}
	
	// Actions.
	public String getName() {
		return this.name;
	}
}
