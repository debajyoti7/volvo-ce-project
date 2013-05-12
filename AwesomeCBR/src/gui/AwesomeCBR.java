package gui;

import java.awt.EventQueue;

import javax.swing.UIManager;

public class AwesomeCBR {
	// Properties.
	static {
		System.loadLibrary("amos2"); // This one has to be loaded first.
		System.loadLibrary("javaamos");
	}
	
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
