package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import core.CBRProject;
import core.AmosProcessBuilder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class CBRProject_View extends JPanel {
	//private JTextField textField2 = new JTextField();
	private JTextField textField1 = new JTextField(20);
	//private JButton btn_query = new JButton("kNN Search");
	//private KNNSearch_JFrame frame_query = new KNNSearch_JFrame();
	private JTextArea status_textarea = new JTextArea();
	private CBRProject project;
	private AmosProcessBuilder connector;
	private boolean database_opened = false;
	private boolean database_modified = false;
	private final JButton btn_disconnect = new JButton("");
	private final JButton btn_connect = new JButton("");
	private JComponent plot = new Default_JPanel(BoxLayout.X_AXIS);
	
	public JTabbedPane tbp = new JTabbedPane();

	public CBRProject_View(final CBRProject p, final JFrame pFrame) {
		project = p;
		connector = new AmosProcessBuilder(status_textarea);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setVisible(false);

		Default_JPanel up = new Default_JPanel(BoxLayout.Y_AXIS);
		JPanel u1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) u1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		u1.setBackground(Color.WHITE);
		JLabel title = new JLabel(p.getName() + " :: " + p.getURL());
		title.setMaximumSize(new Dimension(Integer.MAX_VALUE, Settings.JTextField_height));
		title.setFont(new Font("Verdana", Font.BOLD, 14));
		u1.add(title);
		up.add(u1);

		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		sep.setMinimumSize(new Dimension(Integer.MAX_VALUE, 1));
		up.add(sep);

		/*Default_JPanel u2 = new Default_JPanel(BoxLayout.X_AXIS);
		u2.setMinimumSize(new Dimension(1, Settings.JTextField_height + Settings.border_size));
		u2.setMaximumSize(new Dimension(Integer.MAX_VALUE, Settings.JTextField_height + Settings.border_size));
		u2.setPreferredSize(new Dimension(1, Settings.JTextField_height + Settings.border_size));
		u2.add(textField2);
		textField2.setFont(Settings.font_normal);

		if(p.getKernel().getAttributeNames() != null) {
			String attributes_csv = "";
			int length = p.getKernel().getAttributeNames().length;
			String[] attributes = p.getKernel().getAttributeNames();
			for(int i = 0; i < length; i++ ) {
				attributes_csv += attributes[i] + (i < length - 1 ? "," : "");
			}

			textField2.setText(attributes_csv);
		}
		else {
			//textField2.setText("attribute1,attribute2,attribute3,attribute4,attribute5");
		}

		textField2.setMinimumSize(new Dimension(1, Settings.JTextField_height));
		textField2.setMaximumSize(new Dimension(Integer.MAX_VALUE, Settings.JTextField_height));
		u2.add(btn_query);
		btn_query.setFont(Settings.font_normal);
		btn_query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(project.getKernel().getAttributeNames() != null) {
					frame_query.setVisible(pFrame, project);
				}
				else {
					JOptionPane.showMessageDialog(pFrame, "No attributes are specified.");
				}
			}
		});
		up.add(u2);*/
		add(up);

		JComponent status_panel = new Default_JPanel(BoxLayout.Y_AXIS);
		
		tbp.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

		/* PLOT TAB */
		//tbp.addTab("Plot", pnl_query);
		final JComponent clust_tab = new Default_JPanel(BoxLayout.Y_AXIS);
			JComponent clust_toolbar = new Default_JPanel(BoxLayout.X_AXIS);
			clust_toolbar.setBorder(BorderFactory.createTitledBorder("Clustering parameters"));
				clust_toolbar.add(new JLabel("Epsilon: "));
				final JTextField epsilon_text = new JTextField("0.8");
				epsilon_text.setMaximumSize(new Dimension(100, Settings.JTextField_height));
				clust_toolbar.add(epsilon_text);
				
				clust_toolbar.add(new JLabel("   minPts: "));
				final JTextField pts_text = new JTextField("4");
				pts_text.setMaximumSize(new Dimension(100, Settings.JTextField_height));
				clust_toolbar.add(pts_text);
				
				JButton btn_clustering = new JButton("Do clustering");
				btn_clustering.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						double epsilon = Double.parseDouble(epsilon_text.getText());
						int points = Integer.parseInt(pts_text.getText());
						
						try {
							p.initKernel(epsilon, points);
							clust_tab.remove(plot);
							plot = new ClassifierPanel(p.getKernel().getClassifier());
							clust_tab.add(plot);
							clust_tab.revalidate();
							clust_tab.repaint();
						} catch (Exception e) { e.printStackTrace(); }
					}
				});
				clust_toolbar.add(btn_clustering);
			clust_tab.add(clust_toolbar);
			clust_tab.add(plot);
		tbp.addTab("Clustering", clust_tab);

		/* AMOS II TAB */
		JComponent pnl_amos = new Default_JPanel(BoxLayout.Y_AXIS);
		tbp.addTab("AmosII", pnl_amos);
		
		JComponent button_bar = new Default_JPanel(BoxLayout.X_AXIS);
		button_bar.setBackground(Color.WHITE);
		btn_connect.setToolTipText("Connect to AMOS");
		ImageIcon image = new ImageIcon("graphics/database-check-icon.png");
		btn_connect.setIcon(image);
		btn_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btn_connect.setEnabled(false);
				btn_disconnect.setEnabled(true);
				textField1.setEnabled(true);
				amosConnect();
				database_opened = true;
			}
		});
		button_bar.add(btn_connect);

		//btn_disconnect.setBorder(null);
		btn_disconnect.setToolTipText("Disconnect from AMOS");
		image = new ImageIcon("graphics/database-delete-icon.png");
		btn_disconnect.setIcon(image);
		btn_disconnect.setEnabled(false);
		btn_disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(database_opened) {
					database_opened = false;

					if(database_modified) {
						database_modified = false;
						int n = JOptionPane.showConfirmDialog(null, "Save dataset?", "Save confirmation", JOptionPane.YES_NO_OPTION);
						if(n == JOptionPane.YES_OPTION) {
							connector.execute("save '"+project.getURL()+"';");
						}
					}

					btn_connect.setEnabled(true);
					btn_disconnect.setEnabled(false);
					textField1.setText("");
					textField1.setEnabled(false);

					amosDisconnect();
				}
			}
		});
		button_bar.add(btn_disconnect);

		pnl_amos.add(button_bar);
		
		JComponent pnl_amos_1 = new Default_JPanel(BoxLayout.X_AXIS);
		button_bar.add(pnl_amos_1);
		//pnl_amos.add(pnl_amos_1);
		JLabel pnl_amos_lbl1 = new JLabel("Command: ");
		pnl_amos_1.add(pnl_amos_lbl1);
		textField1.setEnabled(false);
		textField1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) { 
					try {
						database_modified = true;
						status_textarea.append(textField1.getText()+"\n");
						connector.execute(textField1.getText());
						textField1.setText("");
					}
					catch(Exception ex) {
						CBRProject_View.this.status_textarea.setText("Exception ::" + ex.getMessage());
					}
				}
			}
		});
		pnl_amos_1.add(textField1);
		textField1.setMaximumSize(new Dimension(Integer.MAX_VALUE, Settings.JTextField_height));

		// Amos Text area
		Default_JPanel pnl_amos_2 = new Default_JPanel(BoxLayout.X_AXIS);
		JScrollPane sp = new JScrollPane();
		sp.setMinimumSize(new Dimension(1, 1));
		sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		sp.setViewportView(status_textarea);
		
		status_textarea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		status_textarea.setEditable(false);
		status_textarea.setText("");
		status_textarea.setFont(Settings.font_normal);
		pnl_amos_2.add(sp);
		pnl_amos.add(pnl_amos_2);
		
		// Add tabbedpane
		status_panel.add(tbp);
		add(status_panel);
	}

	public void amosDisconnect() {
		connector.doStop();
	}

	public void amosConnect() {
		connector.doStart(project.getURL());
	}
}