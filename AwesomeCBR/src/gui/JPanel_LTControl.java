package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class JPanel_LTControl extends JPanel {
	private JTextField t;
	private JLabel l;
	
	public JPanel_LTControl() {}
	public JPanel_LTControl(String label, String value) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		l = new JLabel(label);
		l.setFont(Settings.font_normal);
		l.setMinimumSize(new Dimension(Settings.tabs_first_col_width, Settings.JTextField_height));
		l.setMaximumSize(new Dimension(Settings.tabs_first_col_width, Settings.JTextField_height));
		l.setPreferredSize(new Dimension(Settings.tabs_first_col_width, Settings.JTextField_height));
		add(l);
		
		t = new JTextField(value);
		t.setMinimumSize(new Dimension(250, Settings.JTextField_height));
		t.setMaximumSize(new Dimension(32767, Settings.JTextField_height));
		t.setPreferredSize(new Dimension(250, Settings.JTextField_height));
		t.setText(value);
		add(t);
	}
	
	// Actions.
	public void setText(String text) {
		t.setText(text);
	}
	public String getText() {
		return t.getText();
	}
	
	
}
