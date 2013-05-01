package gui;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import javax.swing.JSplitPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



import core.ProjectManager;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JList;

import javax.swing.JTabbedPane;

public class JFrame_main extends JFrame {
	// Properties.
	private static final long serialVersionUID = 6456671255024822905L;
	
	private ProjectManager pm;
	
	private JPanel contentPane;

	// Constructors.
	public JFrame_main() {
		this.pm = new ProjectManager();
		
		this.pm.createProject("Project1");
		this.pm.createProject("Project2");
		
		setTitle("AwsomeCBR");
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
		btnp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new JFrame_project_create().setVisible(true);
			}
		});
		
		panel_2.add(btnp);
		btnp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnp_1 = new JButton("-P");
		panel_2.add(btnp_1);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.10);
		panel_3.add(splitPane);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		int pcount = this.pm.getProjects().size();
		String[] pNames = new String[pcount];
		for(int i = 0; i < pcount; i++) {
			pNames[i] = this.pm.getProjects().get(i).getName();
		}
		
		JList list = new JList(pNames);
		scrollPane.setViewportView(list);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Project", null, panel, "Does nothing");
		panel.setLayout(new GridLayout(0, 1));
		
		JPanel r1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel l1 = new JLabel("Name:");
		JTextField f1 = new JTextField();
		
		JPanel r2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel l2 = new JLabel("Name:");
		JTextField f2 = new JTextField();
		
		r1.add(l1);
		r1.add(f1);
		
		r2.add(l2);
		r2.add(f2);
		
		panel.add(r1);
		panel.add(r2);
		
		
		tabbedPane.addTab("Model", null, new JPanel(), "Does nothing");
		tabbedPane.addTab("Data", null, new JPanel(), "Does nothing");
		
		scrollPane_1.setViewportView(tabbedPane);
	}
}
