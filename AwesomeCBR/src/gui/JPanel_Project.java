package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import core.CBRAttribute;
import core.CBRProject;
import core.AmosProcessBuilder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class JPanel_Project extends JPanel {
	//private JSplitPane up_down = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JTextField textField2 = new JTextField();
	private JTextField textField1 = new JTextField();
	private JButton btn_query = new JButton("kNN Search");
	private JFrame_query frame_query = new JFrame_query();
	private JTextArea status_textarea = new JTextArea();
	private CBRProject project;
	private AmosProcessBuilder connector;

	public JPanel_Project(CBRProject p, final JFrame pFrame) {
		project = p;

		connector = new AmosProcessBuilder(status_textarea);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.BLUE);
		setVisible(false);

		//add(up_down);

		JPanel_Default up = new JPanel_Default(BoxLayout.Y_AXIS);
		//up_down.setLeftComponent(up);
		add(up);

		//up.setMinimumSize(new Dimension(1, 1));
		//up.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
		//up.setPreferredSize(new Dimension(Integer.MAX_VALUE, 1));
		
		JPanel_Default u1 = new JPanel_Default(BoxLayout.Y_AXIS);
		//u1.setBorder(new EmptyBorder(5,5,25,10));
		//u1.setMinimumSize(new Dimension(1, Settings.JTextField_height + 10));
		//u1.setMaximumSize(new Dimension(Integer.MAX_VALUE, Settings.JTextField_height + 10));
		//u1.setPreferredSize(new Dimension(Integer.MAX_VALUE, Settings.JTextField_height + 10));
		JLabel title = new JLabel(p.getName() + " :: " + p.getDataset());
		//title.setMaximumSize(new Dimension(Integer.MAX_VALUE, Settings.JTextField_height));
		title.setFont(new Font("Verdana", Font.BOLD, 14));
		u1.add(title);
		
		up.add(u1);
		up.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		//up.setMaximumSize(new Dimension(Integer.MAX_VALUE, Settings.JTextField_height));
		//up.setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		
		
		
		JPanel_Default u2 = new JPanel_Default(BoxLayout.X_AXIS);
		u2.setMinimumSize(new Dimension(1, Settings.JTextField_height + Settings.border_size));
		u2.setMaximumSize(new Dimension(Integer.MAX_VALUE, Settings.JTextField_height + Settings.border_size));
		u2.setPreferredSize(new Dimension(1, Settings.JTextField_height + Settings.border_size));
		u2.add(textField2);
		textField2.setFont(Settings.font_normal);
		textField2.setText("attribute1,attribute2,attribute3,attribute4,attribute5");
		textField2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) { 
					btn_query.doClick();
				}
			}
		});
		u2.add(btn_query);
		btn_query.setFont(Settings.font_normal);
		btn_query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField2.getText().equals("")) {

					// TODO Implement the interface here and get the attributes from there.
					// TODO If no attributes are available from the interface read from file.
					String[] attr_tokens = textField2.getText().split(",");
					project.setAttributes(new ArrayList<CBRAttribute>());

					for(int i = 0; i < attr_tokens.length; i++) {
						project.addAttribute(new CBRAttribute(attr_tokens[i]));
					}

					frame_query.setVisible(pFrame, project.getAttributes(), true);
					// TODO Save attributes to file.
				}
				else {
					JOptionPane.showMessageDialog(pFrame, "No attributes are specified.");
				}
			}
		});
		up.add(u2);

		JPanel_Default status_panel = new JPanel_Default(BoxLayout.Y_AXIS);
		//up_down.setRightComponent(status_panel);
		add(status_panel);

		JPanel_Default button_bar = new JPanel_Default(BoxLayout.LINE_AXIS);
		ImageIcon image;
		JButton btn_connect = new JButton("");
		btn_connect.setToolTipText("Connect to AMOS");
		image = new ImageIcon("graphics/database-check-icon.png");
		btn_connect.setIcon(image);
		btn_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				amosConnect();
				// TODO set a flag that its connected.
			}
		});
		//btn_connect.setMinimumSize(new Dimension(0, Settings.JTextField_height));
		button_bar.add(btn_connect);

		JButton btn_disconnect = new JButton("");
		btn_disconnect.setToolTipText("Disconnect from AMOS");
		image = new ImageIcon("graphics/database-delete-icon.png");
		btn_disconnect.setIcon(image);
		btn_disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				amosDisconnect();
				// TODO Read the same flag and if its set save dataset :: save "file.osql";
				// TODO Also on close program do the same.
			}
		});
		button_bar.add(btn_disconnect);

		JPanel spacer = new JPanel();
		spacer.setBackground(Color.WHITE);
		button_bar.add(spacer);
		status_panel.add(button_bar);

		JTabbedPane tbp = new JTabbedPane();
		tbp.setMinimumSize(new Dimension(1, 1));
		tbp.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		status_panel.add(tbp);

		JPanel_Default pnl_amos = new JPanel_Default(BoxLayout.Y_AXIS);
		tbp.addTab("AmosII", pnl_amos);

		JPanel_Default pnl_amos_1 = new JPanel_Default(BoxLayout.X_AXIS);
		JLabel pnl_amos_lbl1 = new JLabel("Command: ");
		pnl_amos_1.add(pnl_amos_lbl1);
		textField1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) { 
					try {
						status_textarea.append(textField1.getText()+"\n");
						connector.execute(textField1.getText());
						textField1.setText("");
						//ac.execute(textField1.getText());
						// TODO.
					}
					catch(Exception ex) {
						JPanel_Project.this.status_textarea.setText("Exception ::" + ex.getMessage());
					}
				}
			}
		});
		//textField1.setMinimumSize(new Dimension(0, Settings.JTextField_height));
		pnl_amos_1.add(textField1);
		pnl_amos_1.setMinimumSize(new Dimension(1, Settings.JTextField_height + Settings.border_size));
		pnl_amos_1.setMaximumSize(new Dimension(Integer.MAX_VALUE, Settings.JTextField_height + Settings.border_size));
		pnl_amos.add(pnl_amos_1);

		JPanel_Default pnl_amos_2 = new JPanel_Default(BoxLayout.X_AXIS);
		JScrollPane sp = new JScrollPane();
		sp.setMinimumSize(new Dimension(1, 1));
		sp.setMaximumSize(new Dimension(32767, 32767));

		sp.setViewportView(status_textarea);
		status_textarea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		status_textarea.setEditable(false);
		status_textarea.setText("");
		status_textarea.setFont(Settings.font_normal);
		pnl_amos_2.add(sp);
		pnl_amos.add(pnl_amos_2);
	}

	public void amosDisconnect() {
		connector.doStop();
	}

	public void amosConnect() {
		connector.doStart(project.getDataset());
	}
}
