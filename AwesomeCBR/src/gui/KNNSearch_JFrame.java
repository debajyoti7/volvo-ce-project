package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import core.CBRProject;

public class KNNSearch_JFrame extends JFrame {
	private static final long serialVersionUID = 8446333323136510070L;
	private JPanel_LTControl[] fields;
	
	public KNNSearch_JFrame() {}
	
	public void setVisible(final JFrame parent, final CBRProject project) {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		setTitle("New kNN query");
		setResizable(false);
		
		//initial value, min, max, step
		SpinnerModel model = new SpinnerNumberModel(10, 3, 100, 1);
		final JSpinner kSpinner = new JSpinner(model);		
		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("Define number of neighbors (k): "));
		topPanel.add(kSpinner);
		add(topPanel);
		
		//add(new JLabel(attributes.size()+""));
		String[] attributes = new String[] {"attr1", "attr2"}; //project.getKernel().getAttributeNames();
		fields = new JPanel_LTControl[attributes.length];
		for(int i = 0; i < attributes.length; i++) {
			fields[i] = new JPanel_LTControl(attributes[i]+":");
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
				double[] values = new double[fields.length];
				for(int i = 0; i < fields.length; i++) {
					try {
						fields[i].t.commitEdit();
						values[i] = Double.parseDouble(fields[i].t.getValue().toString());
					} catch (ParseException pe) {
						JOptionPane.showMessageDialog(parent, "Couldn't parse all values :-(", "Illegal value format", JOptionPane.ERROR_MESSAGE);
						return;
					}	
				}
				try {
					//JOptionPane.showMessageDialog(KNNSearch_JFrame.this, "To teh kernel!! " + Arrays.toString(values));
					project.getKernel().kNNQuery((Integer)kSpinner.getValue(), values);
					setVisible(false);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(parent, ex.toString(), "Exception occured?!", JOptionPane.ERROR_MESSAGE);
				}				
			}
		});
		
		bp.add(submit_button);
		add(bp);
		
		pack();
		
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	@SuppressWarnings("serial")
	private class JPanel_LTControl extends JPanel {
		private JFormattedTextField t;
		
		public JPanel_LTControl(String label) {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setBorder(new EmptyBorder(5, 5, 5, 5));
			
			JLabel l = new JLabel(label);
			l.setFont(Settings.font_normal);
			add(l);
			
			t = new JFormattedTextField();//(DecimalFormat) DecimalFormat.getNumberInstance());
			t.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("0.0", new DecimalFormatSymbols(new Locale("us", "EN"))))));
			//t.setLocale(Locale.US);
			//t.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
			t.setMinimumSize(new Dimension(250, Settings.JTextField_height));
			t.setMaximumSize(new Dimension(32767, Settings.JTextField_height));
			t.setPreferredSize(new Dimension(250, Settings.JTextField_height));
			
			add(t);
		}
	}
}
