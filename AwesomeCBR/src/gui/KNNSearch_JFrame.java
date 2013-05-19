package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class KNNSearch_JFrame extends JFrame {
	private static final long serialVersionUID = 8446333323136510070L;
	private JPanel_LTControl[] fields;
	
	public KNNSearch_JFrame() {}
	
	public void setVisible(JFrame parent, String[] attributes, Boolean b) {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		setTitle("AwesomeCBR - New query");
		setResizable(false);
		
		//add(new JLabel(attributes.size()+""));
		fields = new JPanel_LTControl[attributes.length];
		for(int i = 0; i < attributes.length; i++) {
			fields[i] = new JPanel_LTControl(attributes[i]+":", "");
			add(fields[i]);
		}
		
		JPanel bp = new JPanel();
		bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
		bp.setBorder(new EmptyBorder(5,5,5,5));
		
		JPanel spacer = new JPanel();
		spacer.setMinimumSize(new Dimension(1, Settings.JTextField_height));
		spacer.setMaximumSize(new Dimension(Integer.MAX_VALUE, Settings.JTextField_height));
		bp.add(spacer);
		
		JButton submit_button = new JButton("Submit");
		submit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "";
				for(int i = 0; i < fields.length; i++) {
					str += "\"" + fields[i].getText() + "\"" + (i < fields.length - 1 ? "," : "");
				}
				JOptionPane.showMessageDialog(KNNSearch_JFrame.this, "To teh kernel!! " + str);
			}
		});
		
		bp.add(submit_button);
		add(bp);
		
		pack();
		
		setLocationRelativeTo(parent);
		super.setVisible(b);
	}
	
	@SuppressWarnings("serial")
	private class JPanel_LTControl extends JPanel {
		private JTextField t;
		private JLabel l;
		
		public JPanel_LTControl(String label, String value) {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setBorder(new EmptyBorder(5, 5, 5, 5));
			
			l = new JLabel(label);
			l.setFont(Settings.font_normal);
			//l.setMinimumSize(new Dimension(Settings.tabs_first_col_width, Settings.JTextField_height));
			//l.setMaximumSize(new Dimension(Settings.tabs_first_col_width, Settings.JTextField_height));
			//l.setPreferredSize(new Dimension(Settings.tabs_first_col_width, Settings.JTextField_height));
			add(l);
			
			t = new JTextField(value);
			t.setMinimumSize(new Dimension(250, Settings.JTextField_height));
			t.setMaximumSize(new Dimension(32767, Settings.JTextField_height));
			t.setPreferredSize(new Dimension(250, Settings.JTextField_height));
			t.setText(value);
			add(t);
		}
		
		/*public void setText(String text) {
			t.setText(text);
		}*/
		
		public String getText() {
			return t.getText();
		}
	}
}
