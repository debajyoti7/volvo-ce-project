package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import core.CBRAttribute;

public class JFrame_query extends JFrame {
	// Properties.
	private static final long serialVersionUID = 8446333323136510070L;
	private JPanel_LTControl[] fields;
	
	// Constructors.
	public JFrame_query() {}
	
	// Actions.
	public void setVisible(JFrame parent, List<CBRAttribute> attributes, Boolean b) {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		setTitle("AwesomeCBR - New query");
		setResizable(false);
		
		//add(new JLabel(attributes.size()+""));
		fields = new JPanel_LTControl[attributes.size()];
		for(int i = 0; i < attributes.size(); i++) {
			fields[i] = new JPanel_LTControl(attributes.get(i).getName()+":", "");
			add(fields[i]);
		}
		
		JPanel bp = new JPanel();
		bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
		bp.setBorder(new EmptyBorder(5,5,5,5));
		
		JPanel spacer = new JPanel();
		spacer.setMinimumSize(new Dimension(1, Settings.JTextField_height));
		spacer.setMaximumSize(new Dimension(32767, Settings.JTextField_height));
		bp.add(spacer);
		
		JButton submit_button = new JButton("Submit");
		submit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = "";
				for(int i = 0; i < fields.length; i++) {
					str += "\"" + fields[i].getText() + "\"" + (i < fields.length - 1 ? "," : "");
				}
				JOptionPane.showMessageDialog(JFrame_query.this, "To teh kernel!! " + str);
			}
		});
		
		bp.add(submit_button);
		add(bp);
		
		pack();
		
		setLocationRelativeTo(parent);
		super.setVisible(b);
	}

}
