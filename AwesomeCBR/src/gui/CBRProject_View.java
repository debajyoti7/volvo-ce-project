package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import core.CBRProject;
import core.AmosProcessBuilder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

@SuppressWarnings("serial")
public class CBRProject_View extends JPanel {
	//private JTextField textField2 = new JTextField();
	private JTextField textField1 = new JTextField(20);
	private JButton btn_query = new JButton("kNN Search");
	private KNNSearch_JFrame frame_query = new KNNSearch_JFrame();
	private JTextArea status_textarea = new JTextArea();
	private CBRProject project;
	private AmosProcessBuilder connector;
	private boolean database_opened = false;
	private boolean database_modified = false;
	private final JButton btn_disconnect = new JButton("");
	private final JButton btn_connect = new JButton("");
	private DefaultListModel<String> history_listModel = new DefaultListModel<String>();
	private JList<String> history = new JList<String>(history_listModel);
	private JComponent plot = new Default_JPanel(BoxLayout.X_AXIS);
	
	public JTabbedPane tbp = new JTabbedPane();

	public CBRProject_View(final CBRProject p, final JFrame pFrame) {
		project = p;
		connector = new AmosProcessBuilder(status_textarea);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setVisible(false);

		/* TITLE */
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
		add(up);

		
		
		JComponent status_panel = new Default_JPanel(BoxLayout.Y_AXIS);		
		tbp.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

		/* PLOT TAB */
		//tbp.addTab("Plot", pnl_query);
		final JComponent clust_tab = new Default_JPanel(BoxLayout.Y_AXIS);
			JComponent clust_toolbar = new Default_JPanel(BoxLayout.X_AXIS);
			clust_toolbar.setBorder(BorderFactory.createTitledBorder("Clustering parameters"));
				clust_toolbar.add(new JLabel("Epsilon: "));
				final JFormattedTextField epsilon_text = new JFormattedTextField();
				epsilon_text.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("0.0", new DecimalFormatSymbols(new Locale("us", "EN"))))));
				epsilon_text.setText("0.5");
				epsilon_text.setMaximumSize(new Dimension(100, Settings.JTextField_height));
				clust_toolbar.add(epsilon_text);
				
				clust_toolbar.add(new JLabel("   minPts: "));
				SpinnerModel model = new SpinnerNumberModel(3, 1, 100, 1);
				final JSpinner kSpinner = new JSpinner(model);
				kSpinner.setMaximumSize(new Dimension(100, Settings.JTextField_height));
				clust_toolbar.add(kSpinner);
				clust_toolbar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
				
				JButton btn_clustering = new JButton("Do clustering");
				btn_clustering.setFont(Settings.font_normal);
				btn_clustering.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						double epsilon = Double.parseDouble(epsilon_text.getText());
						int points = Integer.parseInt(kSpinner.getValue().toString());
						
						try {
							p.initKernel(epsilon, points);
							clust_tab.remove(plot);
							plot = new ClassifierPanel(p.getKernel().getClassifier());
							clust_tab.add(plot);
							clust_tab.revalidate();
							clust_tab.repaint();
							btn_query.setEnabled(true);
						} catch (Exception e) { e.printStackTrace(); }
					}
				});
				clust_toolbar.add(btn_clustering);
				
				btn_query.setFont(Settings.font_normal);
				btn_query.setEnabled(false);
				btn_query.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//if(project.getKernel() != null && project.getKernel().getAttributeNames() != null) {
						frame_query.setVisible(pFrame, project);
						//}
						//else {
						//JOptionPane.showMessageDialog(pFrame, "No attributes are specified.");
						//}
					}
				});
				clust_toolbar.add(btn_query);
			clust_tab.add(clust_toolbar);
			clust_tab.add(plot);
		tbp.addTab("Clustering & Query", clust_tab);

		/* AMOS II TAB */
		JComponent pnl_amos = new Default_JPanel(BoxLayout.X_AXIS);
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
							//connector.execute("save '"+project.getURL()+"';");
							connector.execute("save '"+project.getName()+".dmp';");
						}
					}

					btn_connect.setEnabled(true);
					btn_disconnect.setEnabled(false);
					textField1.setText("");
					textField1.setEnabled(false);

					amosDisconnect();
					
					history_listModel.clear();
				}
			}
		});
		button_bar.add(btn_disconnect);
		
		JComponent pnl_amos_1 = new Default_JPanel(BoxLayout.X_AXIS);
		button_bar.add(pnl_amos_1);
		
		//pnl_amos.add(pnl_amos_1);
		JLabel pnl_amos_lbl1 = new JLabel("Command: ");
		pnl_amos_1.add(pnl_amos_lbl1);
		//pnl_amos_1.setMinimumSize(new Dimension(0, 0));
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
						historyAdd(textField1.getText());
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
		
		status_textarea.setEditable(false);
		status_textarea.setText("");
		status_textarea.setFont(Settings.font_normal);
		pnl_amos_2.add(sp);
		
		JComponent tp = new Default_JPanel(BoxLayout.Y_AXIS);
		tp.setMinimumSize(new Dimension(0, 0));
		tp.add(button_bar);
		tp.add(pnl_amos_2);
		pnl_amos.add(tp);
		
		JScrollPane history_sp = new JScrollPane();
		history_sp.setMinimumSize(new Dimension(150, Integer.MAX_VALUE));
		//history_sp.setMaximumSize(new Dimension(150, Integer.MAX_VALUE));
		history_sp.setPreferredSize(new Dimension(150, 150));
		history_sp.setViewportView(history);
		history_sp.setBorder(BorderFactory.createTitledBorder("Cmd History"));
		history_sp.setBackground(Color.WHITE);
		
		history.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        @SuppressWarnings("rawtypes")
				JList list = (JList)evt.getSource();
		        if (evt.getClickCount() > 1) {
		        	if(list.getModel().getSize() > 0) {
		        		status_textarea.append(list.getSelectedValue().toString()+"\n");
		        		connector.execute(list.getSelectedValue().toString());
		        	}
		        }
		    }
		});
		
		// Almost working history.
		//pnl_amos.add(history_sp);
		
		status_panel.add(tbp);
		add(status_panel);
	}
	
	private void historyAdd(String cmd) {
		history_listModel.addElement(cmd);
	}

	public void amosDisconnect() {
		connector.doStop();
	}

	public void amosConnect() {
		connector.doStart(project.getURL());
	}
}
