package core;

import java.util.ArrayList;
import java.util.List;

public class ProjectManager {
	// Properties.
	private List<CBRProject> projects;
	
	// Constructors.
	public ProjectManager() {
		this.projects = new ArrayList<CBRProject>();
	}
	
	public ProjectManager(List<CBRProject> _projects) {
		this.projects = _projects;
	}
	
	// Actions.
	public int createProject(String _name) {
		CBRProject tmp = new CBRProject(_name);
		this.projects.add(tmp);
		return 0;
	}
	
	public int deleteProject() {
		/* TODO */
		return 0;
	}
	
	public int loadProjects() {
		/* TODO */
		return 0;
	}
	
	public List<CBRProject> getProjects() {
		return this.projects;
	}
}
