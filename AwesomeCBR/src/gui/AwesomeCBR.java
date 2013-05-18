package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import cbr.Kernel;
import cbr.KernelIF;
import core.CBRProject;
import core.CBRControler;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.UIManager;
import java.awt.Dimension;
import java.io.File;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class AwesomeCBR extends JFrame {	
	private CBRControler pm;
	private List<CBRProject_View_JPanel> ppanels = new ArrayList<CBRProject_View_JPanel>();
	private Boolean disable_listeners = false;
	private JSplitPane splitPaneLR;
	private JScrollPane scrollPane;
	private JPanel contentPane;
	private JPanel panel_main;
	private JPanel projects_area = new JPanel();
	private JList<String> list_projects;	
	private JMenuItem f1, /*f2,*/ f3;
	
	// Actions.
	public static void main(String[] args) {
		try {
			// Set System Look & Feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) { /* handle exception */ }

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AwesomeCBR frame = new AwesomeCBR();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AwesomeCBR() {
		// Main window settings.
		setTitle("AwesomeCBR");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("Project");
		file.setMnemonic('P');
		f1 = new JMenuItem("New...");
		f1.setMnemonic('N');
		f1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
		    	CBRProject_Create_JDialog np = new CBRProject_Create_JDialog(AwesomeCBR.this, AwesomeCBR.this.pm.getProjectNames(), "Awsome CBR - Add new project", null);
				np.setLocationRelativeTo(AwesomeCBR.this);
				np.setVisible(true);
				
				if(np.isValidated()) {
					//Kernel kernel = new Kernel(new File(np.getURL()));
					//KernelIF kernel = new Kernel(new File(np.getURL()));
					//String[] attribute_names = kernel.getAttributeNames();
					CBRProject p = new CBRProject(np.getProjectName(), np.getURL());
					pm.add(p);
					
					// To selecte the newly created project.
					refreshView(pm.getProjectNames().indexOf(np.getProjectName()));
				}
		    }
		});
		file.add(f1);
		
		/*f2 = new JMenuItem("Edit...");
		f2.setMnemonic('E');
		f2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
		    	String old_name = list_projects.getSelectedValue().toString();
				int old_index = list_projects.getSelectedIndex();
				CBRProject p = pm.getProjects().get(old_index);
				JDialog_Project np = new JDialog_Project(JFrame_main.this, JFrame_main.this.pm.getProjectNames(), "AwesomeCBR - Edit project name", p);
				np.setLocationRelativeTo(JFrame_main.this);
				np.setVisible(true);

				if(np.isValidated()) {					
					p.setName(np.getProjectName());
					p.setDataset(np.getURL());
					pm.updateProject(old_name, p);
					refreshView(pm.getProjectNames().indexOf(p.getName()));
				}

		    }
		});
		file.add(f2);*/
		
		f3 = new JMenuItem("Delete...");
		f3.setMnemonic('D');
		f3.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
		    	pm.remove(list_projects.getSelectedValue().toString());
		    	
				refreshView(0);				
				// TODO clean project_area after last item is deleted
		    }
		});
		file.add(f3);
		bar.add(file);
			
		setJMenuBar(bar);
		
		/*** Main area panel ***/
		
		panel_main = new JPanel();
		panel_main.setLayout(new BorderLayout(0,0));
		
		splitPaneLR = new JSplitPane();
		
		/*** Left side of Splitpane ***/
		
		scrollPane = new JScrollPane();
		scrollPane.setMinimumSize(new Dimension(150, 1));
		scrollPane.setMaximumSize(new Dimension(150, 32767));
		scrollPane.setPreferredSize(new Dimension(150, 32767));
		
		list_projects = new JList<String>();
		list_projects.setFont(Settings.font_normal);
		list_projects.addListSelectionListener(new ListSelectionListener() {
			// selection changed
			public void valueChanged(ListSelectionEvent arg0) {
				if(!AwesomeCBR.this.disable_listeners) {
					for(Component c : projects_area.getComponents()) {
						c.setVisible(false);
					}
					ppanels.get(list_projects.getSelectedIndex()).setVisible(true);
				}
			}
		});
		scrollPane.setViewportView(list_projects);
		
		splitPaneLR.setLeftComponent(scrollPane);
		splitPaneLR.setRightComponent(projects_area);
		projects_area.setLayout(new BoxLayout(projects_area, BoxLayout.Y_AXIS));
		
		panel_main.add(splitPaneLR);
		contentPane.add(panel_main);
		
		pm = new CBRControler();
		refreshView(0);		
	}
	
	private void refreshView(int selected_index) {
		// Refresh projects.
		disable_listeners = true;
		list_projects.setListData(AwesomeCBR.this.pm.getProjectNames().toArray(new String[AwesomeCBR.this.pm.getProjectNames().size()]));
		
		// Create Panels
		for(CBRProject_View_JPanel p : ppanels) {
			p.amosDisconnect();
		}
		ppanels.clear();
		
		
		for(CBRProject p : pm.getProjects()) {
			CBRProject_View_JPanel tmp = new CBRProject_View_JPanel(p, AwesomeCBR.this);
			
			tmp.tbp.addTab("Plot", new ClassifierPanel(p.getKernel().getClassifier()));
			tmp.tbp.setSelectedIndex(1);
			
			ppanels.add(tmp);
			projects_area.add(tmp);
		}
		
		disable_listeners = false;		
		
		if(AwesomeCBR.this.pm.getProjects().size() > 0) {
			list_projects.setSelectedIndex(selected_index);
			ppanels.get(selected_index).setVisible(true);
			//f2.setEnabled(true);
			f3.setEnabled(true);
		}
		else {
			//f2.setEnabled(false);
			f3.setEnabled(false);
		}
	}
}
