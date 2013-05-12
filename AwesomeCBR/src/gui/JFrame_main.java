package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JSplitPane;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.ScrollPane;

import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import core.AmosDemo;
import core.Attribute;
import core.CBRProject;
import core.ProjectManager;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;

public class JFrame_main extends JFrame {
	// Properties.
	private static final long serialVersionUID = 6456671255024822905L;
	
	private static Settings s = new Settings();
	
	private ProjectManager pm;
	
	private JTabbedPane tabbedPane;
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	//private JScrollPane scrollPane_1;
	
	private JPanel contentPane;
	private JPanel pTop;
	private JPanel pBottom;
	private JPanel tab1;
	private JPanel tab2;
	private JPanel tab3;
	
	private JButton btn_project_create;
	private JButton btn_project_delete;
	
	private JList<String> list_projects;
	private JTextField textField1;
	private JTextArea textArea1;
	
	JTextField textField2;
	JTextArea status_textarea;
	
	private List<Attribute> attributes = new ArrayList<Attribute>();
	
	private JFrame_query frame_query = new JFrame_query();

	private Boolean disable_listeners = false;

	// Constructors.
	public JFrame_main() {
		// Main window settings.
		setTitle("AwesomeCBR");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		JMenu file = new JMenu("File");
		file.setMnemonic('F');
		
		JMenuItem f1 = new JMenuItem("New");
		f1.setMnemonic('N');
		file.add(f1);
		
		JMenuItem f2 = new JMenuItem("Import...");
		f2.setMnemonic('I');
		file.add(f2);
			
		JMenu tools = new JMenu("Tools");
		tools.setMnemonic('T');
		JMenuItem t1 = new JMenuItem("Server Manager...");
		t1.setMnemonic('M');
		tools.add(t1);
		
		//JMenuItem item2 = new JMenuItem("Open");
		/*item2.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					// Open connection config
					/* TODO *//*
				}
			}
		);*/
		
		/*exitItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					System.out.println("Exit is pressed");
				}
			}
		);*/
		
		
		JMenuBar bar = new JMenuBar();
		bar.add(file);
		bar.add(tools);
		setJMenuBar(bar);
		
		
		// Top panel and P+, P- buttons.
		pTop = new JPanel();
		pTop.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		btn_project_create = new JButton("P+");
		btn_project_create.setFont(s.font_normal);
		btn_project_create.addActionListener(new ActionListener() {
			// Add new project.
			public void actionPerformed(ActionEvent arg0) {
				JDialog_create np = new JDialog_create(JFrame_main.this, JFrame_main.this.pm.getProjectNames(), "Name:", "", "Awsome CBR - Add new project");
				np.setLocationRelativeTo(JFrame_main.this);
				np.setVisible(true);
				
				if(np.isValidated()) {
					pm.createProject(np.getProjectName());
					reloadProjectList(pm.getProjectNames().indexOf(np.getProjectName()));
				}
				
			}
		});
		pTop.add(btn_project_create);
		
		btn_project_delete = new JButton("P-");
		btn_project_delete.setFont(s.font_normal);
		btn_project_delete.setEnabled(false);
		btn_project_delete.addActionListener(new ActionListener() {
			// Remove Project button
			public void actionPerformed(ActionEvent arg0) {
				pm.deleteProject(list_projects.getSelectedValue().toString());
				reloadProjectList(0);
			}
		});
		pTop.add(btn_project_delete);
		
		contentPane.add(pTop);
		
		// Main area panel.
		pBottom = new JPanel();
		pBottom.setLayout(new BorderLayout(0,0));
		
		splitPane = new JSplitPane();
		
		// Left side of Splitpane.
		
		scrollPane = new JScrollPane();
		scrollPane.setMinimumSize(new Dimension(150, 1));
		scrollPane.setMaximumSize(new Dimension(150, 32767));
		scrollPane.setPreferredSize(new Dimension(150, 32767));
		
		list_projects = new JList<String>();
		list_projects.setFont(s.font_normal);
		//list_projects.setPreferredSize(new Dimension(100, 0));
		//list_projects.setMinimumSize(new Dimension(100, 0));
		//list_projects.setMaximumSize(new Dimension(100, 0));
		list_projects.addListSelectionListener(new ListSelectionListener() {
			// selection changed
			public void valueChanged(ListSelectionEvent arg0) {
				if(!JFrame_main.this.disable_listeners) {
					disable_listeners = true;
					textField1.setText(list_projects.getSelectedValue().toString());
					disable_listeners = false;
				}
			}
		});
		scrollPane.setViewportView(list_projects);
		
		splitPane.setLeftComponent(scrollPane);
		
		// Right side of Splitpane.
		//skata.setBorder(new EmptyBorder(5, 5, 5, 5));
		//scrollPane_1 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		
		tab1 = new JPanel();
		tab1.setBackground(Color.WHITE);
		tab1.setLayout(new BoxLayout(tab1, BoxLayout.Y_AXIS));
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		panel1.setBorder(new EmptyBorder(5, 5, 5, 5));

		JLabel JLabel_1_1 = new JLabel("Name:");
		JLabel_1_1.setMinimumSize(new Dimension(s.tabs_first_col_width, 1));
		JLabel_1_1.setPreferredSize(new Dimension(s.tabs_first_col_width, 1));
		JLabel_1_1.setFont(s.font_normal);
		panel1.add(JLabel_1_1);
		panel1.setBackground(Color.WHITE);

		textField1 = new JTextField();
		textField1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		textField1.setBackground(Color.WHITE);
		textField1.setEditable(false);
		textField1.setMinimumSize(new Dimension(1, s.JTextField_height));
		textField1.setMaximumSize(new Dimension(32767, s.JTextField_height));
		textField1.setPreferredSize(new Dimension(1, s.JTextField_height));
		textField1.setFont(s.font_normal);
		textField1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(pm.getProjects().size() > 0) {
					String old_name = list_projects.getSelectedValue().toString();
					int old_index = list_projects.getSelectedIndex();
					JDialog_create np = new JDialog_create(JFrame_main.this, JFrame_main.this.pm.getProjectNames(), "Name:", old_name, "AwesomeCBR - Edit project name");
					np.setLocationRelativeTo(JFrame_main.this);
					np.setVisible(true);

					if(np.isValidated()) {
						CBRProject p = pm.getProjects().get(old_index);
						p.setName(np.getProjectName());
						pm.updateProject(old_name, p);
						reloadProjectList(pm.getProjectNames().indexOf(p.getName()));
					}
				}
			}
		});
		panel1.add(textField1);
		tab1.add(panel1);
		
		JPanel JPanel_1_2 = new JPanel();
		JPanel_1_2.setLayout(new BoxLayout(JPanel_1_2, BoxLayout.X_AXIS));
		JPanel_1_2.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel_1_2.setBackground(Color.WHITE);

		/*JLabel JLabel_1_2 = new JLabel("Description:");
		JLabel_1_2.setMinimumSize(new Dimension(s.tabs_first_col_width, 14));
		JLabel_1_2.setPreferredSize(new Dimension(s.tabs_first_col_width, 14));
		JPanel_1_2.add(JLabel_1_2);

		textArea1 = new JTextArea();
		textArea1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		textArea1.setBackground(Color.WHITE);
		//textArea1.setBorder(new SolidBorder())
		textArea1.setEditable(false);
		textArea1.setMaximumSize(new Dimension(32767, 150));
		JPanel_1_2.add(textArea1);*/
		
		tab1.add(JPanel_1_2);

		//tabbedPane.addTab("Settings", null, tab1, "Settings");
		//tabbedPane.setFont(s.font_normal);
		
		tab2 = new JPanel();
		tab2.setBackground(Color.WHITE);
		tab2.setLayout(new BoxLayout(tab2, BoxLayout.Y_AXIS));
		
		tabbedPane.addTab("Dataset", null, tab2, "Dataset");
		tabbedPane.addTab("Settings", null, tab1, "Settings");
		
		JPanel panel21 = new JPanel();
		panel21.setLayout(new BoxLayout(panel21, BoxLayout.X_AXIS));
		panel21.setBorder(new EmptyBorder(5, 5, 5, 5));

		JLabel JLabel_2_1 = new JLabel("Attributes:");
		JLabel_2_1.setFont(s.font_normal);
		JLabel_2_1.setMinimumSize(new Dimension(s.tabs_first_col_width, 1));
		JLabel_2_1.setPreferredSize(new Dimension(s.tabs_first_col_width, 1));
		panel21.add(JLabel_2_1);
		
		textField2 = new JTextField();
		//textField2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//textField2.setBackground(Color.WHITE);
		//textField2.setEditable(false);
		textField2.setMinimumSize(new Dimension(1, s.JTextField_height));
		textField2.setMaximumSize(new Dimension(32767, s.JTextField_height));
		textField2.setPreferredSize(new Dimension(1, s.JTextField_height));
		textField2.setText("attribute1,attribute2,attribute3,attribute4,attribute5");
		panel21.add(textField2);
		panel21.setBackground(Color.WHITE);
		tab2.add(panel21);
		
		
		JPanel pq = new JPanel();
		pq.setLayout(new BoxLayout(pq, BoxLayout.X_AXIS));
		pq.setBorder(new EmptyBorder(5, 5, 5, 5));
		pq.setBackground(Color.WHITE);
		
		JButton queryButton = new JButton();
		queryButton.setText("Query");
		queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*if(!textField2.getText().equals("")) {
					String[] attr_tokens = textField2.getText().split(",");
					attributes.clear();
					for(int i = 0; i < attr_tokens.length; i++) {
						attributes.add(new Attribute(attr_tokens[i], ""));
					}
					
					frame_query.setVisible(JFrame_main.this, attributes, true);

				}
				else {
					JOptionPane.showMessageDialog(JFrame_main.this, "I am afraid there are no attributes specified...");
				}*/
				
				try {
					new AmosDemo(JFrame_main.this.status_textarea);
				}
				catch(Exception e) {
					
				}
			}
		});

		
		JButton queryButton2 = new JButton();
		queryButton2.setText("Query 2");
		queryButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		pq.add(queryButton);
		pq.add(queryButton2);
		
		tab2.add(pq);
		tab2.add(new JPanel());
		
		/*tab3 = new JPanel();
		tab3.setBackground(Color.WHITE);
		tab3.setLayout(new BoxLayout(tab3, BoxLayout.Y_AXIS));
		tabbedPane.addTab("Dataset", null, tab3, "Dataset");*/
		
		//splitPane.setRightComponent(scrollPane_1);
		splitPane.setRightComponent(tabbedPane);
		pBottom.add(splitPane);
		contentPane.add(pBottom);
		
		JPanel status_panel = new JPanel();
		status_panel.setLayout(new BoxLayout(status_panel, BoxLayout.Y_AXIS));
		status_panel.setBorder(new EmptyBorder(5,5,5,5));
		status_panel.setMinimumSize(new Dimension(32767, 250));
		status_panel.setMaximumSize(new Dimension(32767, 250));
		status_panel.setPreferredSize(new Dimension(32767, 250));
		
		JScrollPane sp = new JScrollPane();
		sp.setMinimumSize(new Dimension(1, 1));
		sp.setMaximumSize(new Dimension(32767, 32767));
		sp.setPreferredSize(new Dimension(32767, 32767));
		
		status_textarea = new JTextArea();
		status_textarea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		//status.setBackground(Color.BLACK);
		status_textarea.setForeground(Color.GRAY);
		status_textarea.setEditable(false);
		status_textarea.setText("");
		status_textarea.setFont(s.font_normal);
		
		sp.setViewportView(status_textarea);
		
		JPanel status_panel_label = new JPanel();
		status_panel_label.setLayout(new FlowLayout());
		
		JLabel slabel = new JLabel(":: Output ::");
		slabel.setFont(s.font_normal);
		status_panel_label.add(slabel);
		status_panel.add(status_panel_label);
		status_panel.add(sp);
		
		contentPane.add(status_panel);
				
		// After
		pm = new ProjectManager();
		pm.loadProjects();
		reloadProjectList(0);		
	}
	
	private void reloadProjectList(int selected_index) {
		// Refresh projects.
		disable_listeners = true;
		list_projects.setListData(JFrame_main.this.pm.getProjectNames().toArray(new String[JFrame_main.this.pm.getProjectNames().size()]));
		disable_listeners = false;
		if(JFrame_main.this.pm.getProjects().size() > 0) {
			list_projects.setSelectedIndex(selected_index);
			btn_project_delete.setEnabled(true);
			//tabbedPane.setEnabledAt(0, true);
			//tabbedPane.setEnabledAt(1, true);
			//tabbedPane.setEnabledAt(2, true);
		}
		else {
			btn_project_delete.setEnabled(false);
			//tabbedPane.setEnabledAt(0, false);
			//tabbedPane.setEnabledAt(1, false);
			//tabbedPane.setEnabledAt(2, false);
			textField1.setText("");
		}
	}
}
