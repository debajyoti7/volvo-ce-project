package core;



import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class ProjectManager {
	// Properties.
	private List<CBRProject> projects;
	private String workspace;
	
	// Constructors.
	public ProjectManager() {
		this.projects = new ArrayList<CBRProject>();
		
		this.workspace = System.getProperty("user.home")+File.separator+".awesomecbr";
		File f = new File(this.workspace);
		if(!f.exists()) {
			f.mkdir();
		}
		else if(!f.isDirectory()) {
			// ERROR. System workspace is a file instead of folder.
		}
	}
	
	public ProjectManager(List<CBRProject> _projects) {
		this.projects = _projects;
	}
	
	// Actions.
	public int createProject(String name) {
		CBRProject p = new CBRProject(name);
		this.projects.add(p);
		
		writeProject2Disk(p);
		
		return 0;
	}
	
	public int deleteProject(String pName) {
		CBRProject p;
		
		
		
		
		
		for(int i = 0; i < this.projects.size(); i++) {
			p = this.projects.get(i);
			
			
			
			
			if(p.getName().equals(pName)) {
				
				
				/*try{
					// Create file 
					FileWriter fstream = new FileWriter(System.getProperty("user.home")+File.separator+"debug.txt");
					BufferedWriter out = new BufferedWriter(fstream);
					out.write("match");
					//Close the output stream
					out.close();
				}catch (Exception ex){//Catch exception if any
					System.err.println("Error: " + ex.getMessage());
				}*/
				
				
				
				try {
					new File(this.workspace+File.separator+p.getName()+".acbr").delete();
					this.projects.remove(i);
				}
				catch(Exception e) {
					
					return -1;
				}
			}
		}
		
		return 0;
	}
	
	public int updateProject(String projectName) {
		
		//writeProject2Disk(CBRProject p)
		/* TODO */
		
		return 0;
	}
	
	private int writeProject2Disk(CBRProject p) {
		File f = new File(this.workspace+File.separator+p.getName()+".acbr"); 

		// File already exist.
		if(f.exists()) { return -1; }

		// File couldn't be created.
		try {
			//f.createNewFile();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(f, p);
		}
		catch(Exception e) {
			return -1;
		}
		
		return 0;
	}
	
	public int loadProjects() {
		/* TODO */
		
		ObjectMapper mapper = new ObjectMapper();
		CBRProject p;
		
		File f = new File(this.workspace);
		File[] files = f.listFiles();
		
		for(int i = 0; i < files.length; i++) {
			try {				
				
				p = mapper.readValue(files[i], CBRProject.class);
				this.projects.add(p);
			}
			catch(Exception e) {
				// Oh noes!
			}
		}
		
		return 0;
	}
	
	public List<CBRProject> getProjects() {
		return this.projects;
	}
	
	public List<String> getProjectNames() {
		int pcount = this.projects.size();
		List<String> names = new ArrayList<String>();
		for(int i = 0; i < pcount; i++) {
			names.add(this.projects.get(i).getName());
		}
		
		return names;
	}
}
