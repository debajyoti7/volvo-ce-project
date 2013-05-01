package gui;

import java.awt.EventQueue;

import javax.swing.UIManager;

public class AwsomeCBR {
	// Properties.
	
	// Constructors.
	
	// Actions.
	public static void main(String[] args) {
		try {
	        // Set System Look & Feel
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Exception e) { /* handle exception */ }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame_main frame = new JFrame_main();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
