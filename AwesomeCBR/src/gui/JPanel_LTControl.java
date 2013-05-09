package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class JPanel_LTControl extends JPanel {
	// Attributes.
	private static final long serialVersionUID = 2550829358263381327L;
	private JTextField t;
	private JLabel l;
	private Settings s = new Settings();
	
	// Constructors.
	public JPanel_LTControl() {}
	public JPanel_LTControl(String label, String value) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		l = new JLabel(label);
		l.setFont(s.font_normal);
		l.setMinimumSize(new Dimension(s.tabs_first_col_width, s.JTextField_height));
		l.setMaximumSize(new Dimension(s.tabs_first_col_width, s.JTextField_height));
		l.setPreferredSize(new Dimension(s.tabs_first_col_width, s.JTextField_height));
		add(l);
		
		t = new JTextField(value);
		t.setMinimumSize(new Dimension(250, s.JTextField_height));
		t.setMaximumSize(new Dimension(32767, s.JTextField_height));
		t.setPreferredSize(new Dimension(250, s.JTextField_height));
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
