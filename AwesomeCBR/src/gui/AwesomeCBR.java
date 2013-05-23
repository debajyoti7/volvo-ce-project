package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import core.CBRProject;
import core.CBRControler;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
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
	private CBRControler pm;
	private List<CBRProject_View_JPanel> ppanels = new ArrayList<CBRProject_View_JPanel>();
	private Boolean disable_listeners = false;
	//private JSplitPane splitPaneLR;
	private JScrollPane scrollPane;
	private JPanel contentPane;
	private JPanel panel_main;
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
					try {
						CBRProject p = new CBRProject(np.getProjectName(), np.getURL());
						pm.add(p);
						
						// To selecte the newly created project.
						refreshView(pm.getProjectNames().indexOf(np.getProjectName()));
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
					ppanels.get(list_projects.getSelectedIndex()).amosDisconnect();
					pm.remove(list_projects.getSelectedValue().toString());
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
		    	// TODO
		    }
		});
			
		setJMenuBar(bar);
		
		panel_main = new Default_JPanel(BoxLayout.X_AXIS);
		//panel_main.setLayout(new BorderLayout(0,0));
		
		JComponent list_panel = new JPanel();
		list_panel.setBackground(Color.WHITE);
		list_panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		list_panel.setMinimumSize(new Dimension(300, Integer.MAX_VALUE));
		
		scrollPane = new JScrollPane();
		scrollPane.setMinimumSize(new Dimension(150, 1));
		scrollPane.setMaximumSize(new Dimension(150, Integer.MAX_VALUE));
		scrollPane.setPreferredSize(new Dimension(150, Integer.MAX_VALUE));
		
		list_projects = new JList<String>();
		list_projects.addListSelectionListener(new ListSelectionListener() {
			// selection changed
			public void valueChanged(ListSelectionEvent arg0) {
				int sIndex = list_projects.getSelectedIndex();
				if(!AwesomeCBR.this.disable_listeners) {
					for(Component c : projects_area.getComponents()) {
						c.setVisible(false);
					}
					ppanels.get(sIndex).setVisible(true);
					setTitle("AwesomeCBR - "+pm.getProjects().get(sIndex).getName());
				}
			}
		});
		scrollPane.setViewportView(list_projects);
		/*list_panel.add(list_projects);
		list_panel.setMinimumSize(new Dimension(150, Integer.MAX_VALUE));
		list_panel.setMaximumSize(new Dimension(150, Integer.MAX_VALUE));*/
		//list_panel.setPreferredSize(new Dimension(150, Integer.MAX_VALUE));
		
		//projects_area.setLayout(new BoxLayout(projects_area, BoxLayout.Y_AXIS));
		
		//JComponent jc = new Default_JPanel(BoxLayout.X_AXIS);
		//jc.setBackground(Color.RED);
		//jc.add(list_panel);
		//jc.add(projects_area);
		
		panel_main.add(scrollPane);
		panel_main.add(projects_area);
		panel_main.setBackground(Color.BLUE);
		
		contentPane.add(panel_main);
		
		pm = new CBRControler();
		refreshView(0);		
	}
	
	private void refreshView(int selected_index) {
		// Refresh projects.
		
		if(AwesomeCBR.this.pm.getProjects().size() > 0) {
			disable_listeners = true;
			list_projects.setListData(AwesomeCBR.this.pm.getProjectNames().toArray(new String[AwesomeCBR.this.pm.getProjectNames().size()]));
			disable_listeners = false;
			
			for(CBRProject p : pm.getProjects()) {
				CBRProject_View_JPanel tmp = new CBRProject_View_JPanel(p, AwesomeCBR.this);
				ppanels.add(tmp);
				projects_area.add(tmp);
			}
			
			list_projects.setSelectedIndex(selected_index);
			ppanels.get(selected_index).setVisible(true);
			
			f3.setEnabled(true);
		}
		else {
			disable_listeners = true;
			list_projects.setListData(new String[] {""});
			disable_listeners = false;
			ppanels.clear();
			setTitle("AwesomeCBR");
			
			projects_area.removeAll();
			projects_area.revalidate();
			projects_area.repaint();
			
			f3.setEnabled(false);
		}
	}
}
