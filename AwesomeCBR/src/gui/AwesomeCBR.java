package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import core.CBRProject;
import core.CBRController;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import java.awt.Dimension;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *	-	JFormattedTextField instead of JTextField for the kNN-search values.
 * 		Ensures a valid double value
 * 	-	setLocationRelativeTo(null);	// Centers on screen
 * 	-	The kNN search panel now get a reference to the kernel so it can perform
 * 		a real search
 * 	-	A JSpinner to define the k in the kNN-search. VITAL :-) 
 *  -	Calculate probability for a data point with the 
 *  	Naive Bayes classifier BEFORE performing the kNN request!
 *  	Use the caseProbability method in NaiveBayesClassifier
 */
@SuppressWarnings("serial")
public class AwesomeCBR extends JFrame {
	private CBRController controller;
	private List<CBRProject_View> project_view_lst = new ArrayList<CBRProject_View>();
	private Boolean disable_listeners = false;
	//private JSplitPane splitPaneLR;
	private JScrollPane scrollPane;
	private JPanel projects_area = new Default_JPanel(BoxLayout.Y_AXIS);
	private JList<String> list_projects;	
	private JMenuItem f1, f3;
	
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
					frame.setLocale(Locale.US);
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
		setLocationRelativeTo(null);	// Centers on screen
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		
		/* Menu bar */
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("Project");
		file.setMnemonic('P');
		f1 = new JMenuItem("New...");
		f1.setMnemonic('N');
		f1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
		    	CBRProject_Create_JDialog np = new CBRProject_Create_JDialog(AwesomeCBR.this, AwesomeCBR.this.controller.getProjectNames(), "Awsome CBR - Add new project", null);
				np.setLocationRelativeTo(AwesomeCBR.this);
				np.setVisible(true);
				
				if(np.isValidated()) {
					try {
						CBRProject p = new CBRProject(np.getProjectName(), np.getURL());
						controller.add(p);
						
						// To selecte the newly created project.
						refreshView(controller.getProjectNames().indexOf(np.getProjectName()));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(
								AwesomeCBR.this, 
								"Unexpected error occured: \n" + e.toString(),
								"Unexpected error",
								JOptionPane.ERROR_MESSAGE);
					}
					
				}
		    }
		});
		file.add(f1);
		
		f3 = new JMenuItem("Delete...");
		f3.setMnemonic('D');
		f3.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
		    	int n = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.YES_OPTION) {
					project_view_lst.get(list_projects.getSelectedIndex()).amosDisconnect();
					controller.remove(list_projects.getSelectedValue().toString());
					refreshView(0);
				}
		    }
		});
		file.add(f3);
		bar.add(file);
		
		JMenu help = new JMenu("Help");
		bar.add(help);
		
		help.setMnemonic('H');
		JMenuItem h1 = new JMenuItem("About");
		help.add(h1);
		h1.setMnemonic('A');
		h1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
		    	JOptionPane.showMessageDialog(null,"AwesomeCBR v1\n2013, Malardalen University");
		    }
		});
			
		setJMenuBar(bar);
		
		/* Main area panel */		
		JComponent panel_main = new Default_JPanel(BoxLayout.X_AXIS);
		
		projects_area.setMinimumSize(new Dimension(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(150, Integer.MAX_VALUE));
		list_projects = new JList<String>();
		list_projects.setFont(Settings.font_normal);
		list_projects.addListSelectionListener(new ListSelectionListener() {
			// selection changed
			public void valueChanged(ListSelectionEvent arg0) {
				if(!AwesomeCBR.this.disable_listeners && controller.getProjects().size() != 0) {
					for(Component c : projects_area.getComponents()) {
						c.setVisible(false);
					}
					project_view_lst.get(list_projects.getSelectedIndex()).setVisible(true);
				}
			}
		});
		scrollPane.setViewportView(list_projects);
	
		//projects_area.setLayout(new BoxLayout(projects_area, BoxLayout.Y_AXIS));
		
		JSplitPane splitPaneLR = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, projects_area);
		splitPaneLR.setResizeWeight(0.1);
		
		/* Console */
		JComponent console = new Default_JPanel(BoxLayout.Y_AXIS);
		JScrollPane scrollpane3 = new JScrollPane();
		JTextPane consolePane = new JTextPane();

		MessageConsole mc = new MessageConsole(consolePane);
		mc.redirectErr(Color.RED, System.err);
		mc.redirectOut(Color.BLACK, System.out);
		
		scrollpane3.setViewportView(consolePane);
		console.add(scrollpane3);
		
		panel_main.add(splitPaneLR);
		
		JSplitPane splitPaneUD = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel_main, console);
		splitPaneUD.setResizeWeight(0.8);
		contentPane.add(splitPaneUD);
		
		controller = new CBRController();
		refreshView(0);		
	}
	
	private void refreshView(int selected_index) {
		// Refresh projects.
		
		if(AwesomeCBR.this.controller.getProjects().size() > 0) {
			disable_listeners = true;
			list_projects.setListData(AwesomeCBR.this.controller.getProjectNames().toArray(new String[AwesomeCBR.this.controller.getProjectNames().size()]));
			disable_listeners = false;
			
			project_view_lst.clear();
			projects_area.removeAll();
			for(CBRProject p : controller.getProjects()) {
				CBRProject_View tmp = new CBRProject_View(p, AwesomeCBR.this);
				project_view_lst.add(tmp);
				projects_area.add(tmp);
			}
			
			list_projects.setSelectedIndex(selected_index);
			project_view_lst.get(selected_index).setVisible(true);
			
			f3.setEnabled(true);
		}
		else {
			disable_listeners = true;
			list_projects.setListData(new String[] {""});
			disable_listeners = false;
			project_view_lst.clear();
			setTitle("AwesomeCBR");
			
			projects_area.removeAll();
			projects_area.revalidate();
			projects_area.repaint();
			
			f3.setEnabled(false);
		}
	}
}
