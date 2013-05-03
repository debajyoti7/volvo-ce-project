package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JSplitPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import core.ProjectManager;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class JFrame_main extends JFrame {
	// Properties.
	private static final long serialVersionUID = 6456671255024822905L;
	
	private ProjectManager pm;
	
	private JPanel contentPane;
	private JList pList; 
	private JTextField textField;
	private JButton btnp_1;
	private JPanel tab1;
	private JPanel tab2;
	private JPanel tab3;
	private JTabbedPane tabbedPane;
	
	private Boolean disable_listeners = false;

	// Constructors.
	public JFrame_main() {
		this.pm = new ProjectManager();
		
		
		setTitle("AwesomeCBR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton btnp = new JButton("+P");
		
		panel_2.add(btnp);
		btnp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnp_1 = new JButton("-P");
		btnp_1.setEnabled(false);
		
		panel_2.add(btnp_1);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.10);
		panel_3.add(splitPane);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		pList = new JList();
		scrollPane.setViewportView(pList);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tab1 = new JPanel();
		tabbedPane.addTab("Project", null, tab1, "Does nothing");
		tab1.setLayout(new BoxLayout(tab1, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tab1.add(panel);
		panel.setMaximumSize(new Dimension(30000, 30));
		
		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setPreferredSize(new Dimension(54, 14));
		lblNewLabel.setMinimumSize(new Dimension(54, 14));
		lblNewLabel.setMaximumSize(new Dimension(54, 14));
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JPanel panel_1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tab1.add(panel_1);
		panel_1.setMaximumSize(new Dimension(30000, 30000));
		
		JLabel lblComments = new JLabel("Comments:");
		panel_1.add(lblComments);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textArea.setPreferredSize(new Dimension(300, 400));
		textArea.setMinimumSize(new Dimension(200, 200));
		panel_1.add(textArea);
		
		
		textField.getDocument().addDocumentListener(
			new DocumentListener() {
				public void changedUpdate(DocumentEvent e) { warn(); }
				public void removeUpdate(DocumentEvent e) { warn(); }
				public void insertUpdate(DocumentEvent e) { warn(); }
				public void warn() {
					if(!disable_listeners) {
						if(JFrame_main.this.pm.getProjectNames().contains(textField.getText())) {
							textField.setBackground(new Color(255, 0, 0));
						}
						else {
							if(textField.getText().length() == 0) {
								textField.setBackground(new Color(255, 0, 0));
								//btnNewButton.setEnabled(false);
								//JDialog_create.this.setTitle(textField.getText());
							}
							else {
								textField.setBackground(new Color(255,255, 255));
								//btnNewButton.setEnabled(true);
								
								//pList.getModel().
							}

							//lblNewLabel_1.setVisible(false);
						}
					}
				}
			}
		);
		
		tab2 = new JPanel();
		tabbedPane.addTab("Model", null, tab2, "Does nothing");
		tab2.setLayout(null);
		
		JLabel lblNoAttributes = new JLabel("No attributes...");
		lblNoAttributes.setBounds(10, 11, 208, 14);
		tab2.add(lblNoAttributes);
		
		JButton btnNewButton = new JButton("Add attribute");
		btnNewButton.setBounds(20, 36, 127, 23);
		tab2.add(btnNewButton);
		tab3 = new JPanel();
		tabbedPane.addTab("Data", null, tab3, "Does nothing");
		
		scrollPane_1.setViewportView(tabbedPane);
		
		pList.addListSelectionListener(new ListSelectionListener() {
			// selection changed
			public void valueChanged(ListSelectionEvent arg0) {
				if(!JFrame_main.this.disable_listeners) {
					disable_listeners = true;
					JFrame_main.this.textField.setText(JFrame_main.this.pList.getSelectedValue().toString());
					disable_listeners = false;
				}
			}
		});
		
		btnp_1.addActionListener(new ActionListener() {
			// Remove Project button
			public void actionPerformed(ActionEvent arg0) {
				JFrame_main.this.pm.deleteProject(pList.getSelectedValue().toString());
				
				reloadProjectList();
			}
		});
		
		btnp.addActionListener(new ActionListener() {
			// Add new project.
			public void actionPerformed(ActionEvent arg0) {
				
				JDialog_create np = new JDialog_create(JFrame_main.this, JFrame_main.this.pm.getProjectNames());
				
				np.setVisible(true);
				
				if(np.isValidated()) {
					JFrame_main.this.pm.createProject(np.getProjectName());
					
					reloadProjectList();
				}
				
			}
		});
		
		/* Load first item */
		this.pm.loadProjects();
		if(JFrame_main.this.pm.getProjects().size() > 0) {
			JFrame_main.this.pList.setListData(JFrame_main.this.pm.getProjectNames().toArray(new String[JFrame_main.this.pm.getProjectNames().size()]));
			JFrame_main.this.pList.setSelectedIndex(0);
			JFrame_main.this.btnp_1.setEnabled(true);
		}
		/**/
		
	}
	
	private void reloadProjectList() {
		// Refresh projects.
		disable_listeners = true;
		pList.setListData(JFrame_main.this.pm.getProjectNames().toArray(new String[JFrame_main.this.pm.getProjectNames().size()]));
		disable_listeners = false;
		if(JFrame_main.this.pm.getProjects().size() > 0) {
			pList.setSelectedIndex(0);
			btnp_1.setEnabled(true);
			
			tabbedPane.setEnabledAt(0, true);
			tabbedPane.setEnabledAt(1, true);
			tabbedPane.setEnabledAt(2, true);
		}
		else {
			btnp_1.setEnabled(false);
			
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setEnabledAt(1, false);
			tabbedPane.setEnabledAt(2, false);
			
			textField.setText("");
		}
	}
}
