package gui;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Default_JPanel extends JPanel {
	public Default_JPanel(int l) {
		setLayout(new BoxLayout(this, l));
		setBorder(new EmptyBorder(Settings.border_size, Settings.border_size, Settings.border_size, Settings.border_size));
		setBackground(Color.WHITE);
	}
}
