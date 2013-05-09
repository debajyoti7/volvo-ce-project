package core;

public class Attribute {
	// Properties.
	private String name;
	private String type;
	
	// Constructors.
	public Attribute() {
		name = "";
		type = "";
	}
	
	public Attribute(String name, String type) {
		//this.attribute_id = attribute_id;
		this.name = name;
		this.type = type;
	}
	
	// Actions.
	public String getName() {
		return name;
	}
}
