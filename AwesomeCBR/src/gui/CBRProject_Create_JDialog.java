package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import core.CBRProject;
import java.awt.event.MouseAdapter;
import java.io.File;

@SuppressWarnings("serial")
public class CBRProject_Create_JDialog extends JDialog {
	private List<String> not_available_names;
	private JTextField textField1;
	private JTextField textField2;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	private boolean form_validated;
	private String old_value;
	private JFileChooser fc = new JFileChooser();

	public CBRProject_Create_JDialog(JFrame par, List<String> not_available_names, String title, CBRProject p) {
		super(par, title, true);
		String label = "Name:";
	
		String old_value = (p == null ? "" : p.getName());
		String old_value2 = (p == null ? "" : p.getURL());
		
		setResizable(false);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		this.old_value = old_value;
		this.not_available_names = not_available_names;
		this.form_validated = false;
		
		
		Default_JPanel p1 = new Default_JPanel(BoxLayout.X_AXIS);
		
		lblNewLabel = new JLabel(label);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setMinimumSize(new Dimension(Settings.tabs_first_col_width, Settings.JTextField_height));
		lblNewLabel.setMaximumSize(new Dimension(32767, Settings.JTextField_height));
		p1.add(lblNewLabel);
		
		contentPane.add(p1);
		
		Default_JPanel p2 = new Default_JPanel(BoxLayout.X_AXIS);
		
		textField1 = new JTextField();
		textField1.setMinimumSize(new Dimension(250, Settings.JTextField_height));
		textField1.setMaximumSize(new Dimension(32767, Settings.JTextField_height));
		textField1.setPreferredSize(new Dimension(250, Settings.JTextField_height));
		textField1.setFont(Settings.font_normal);
		textField1.setText(old_value);
		
		p2.add(textField1);
		
		contentPane.add(p2);
		
		
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
		p3.setBorder(new EmptyBorder(15, 5, 5, 5));
		p3.setBackground(Color.WHITE);
		
		JLabel lblNew = new JLabel("Dataset:");
		lblNew.setHorizontalAlignment(SwingConstants.LEFT);
		lblNew.setMinimumSize(new Dimension(Settings.tabs_first_col_width, Settings.JTextField_height));
		lblNew.setMaximumSize(new Dimension(32767, Settings.JTextField_height));
		p3.add(lblNew);
		
		contentPane.add(p3);
		
		Default_JPanel p4 = new Default_JPanel(BoxLayout.X_AXIS);
		
		textField2 = new JTextField();
		textField2.setMinimumSize(new Dimension(250, Settings.JTextField_height));
		textField2.setMaximumSize(new Dimension(32767, Settings.JTextField_height));
		textField2.setPreferredSize(new Dimension(250, Settings.JTextField_height));
		textField2.setFont(Settings.font_normal);
		textField2.setText(old_value2);
		
		p4.add(textField2);
		JButton btn_browse = new JButton("");
		Icon image = new ImageIcon("graphics/browse.png");
		btn_browse.setIcon(image);
		btn_browse.setToolTipText("Browse");
		btn_browse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fc.setCurrentDirectory(new File(new File(".").getAbsolutePath()));
				int returnVal = fc.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					textField2.setText(fc.getSelectedFile().getAbsolutePath());
				}
				else {
				}
			}
		});
		p4.add(btn_browse);
		
		contentPane.add(p4);
		
		Default_JPanel pf = new Default_JPanel(BoxLayout.X_AXIS);
		Default_JPanel pf_spacer = new Default_JPanel(BoxLayout.X_AXIS);
		pf_spacer.setMinimumSize(new Dimension(Integer.MAX_VALUE, 1));
		pf_spacer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
		pf.add(pf_spacer);
		
		Default_JPanel second_right = new Default_JPanel(BoxLayout.X_AXIS);
		btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitForm();
			}
		});
		second_right.add(btnNewButton);
		pf.add(second_right);
		contentPane.add(pf);
		
		// Stretch window to contents.
		this.pack();
	}
	
	// Actions.
	private void submitForm() {
		int problems = 0;
		
		if(textField1.getText().equals("")) {
			JOptionPane.showMessageDialog(CBRProject_Create_JDialog.this, "Please give a project name.");
			problems++;
		}
		
		if(! textField1.getText().equals(CBRProject_Create_JDialog.this.old_value) && CBRProject_Create_JDialog.this.not_available_names.contains(textField1.getText())) {
			JOptionPane.showMessageDialog(CBRProject_Create_JDialog.this, "Name already exist.");
			problems++;
		}
		
		if(!new File(textField2.getText()).exists()) {
			JOptionPane.showMessageDialog(CBRProject_Create_JDialog.this, "Dataset is not valid.");
			problems++;
		}

		if(problems == 0) {
			CBRProject_Create_JDialog.this.form_validated = true;
			CBRProject_Create_JDialog.this.setVisible(false);
		}
	}
	
	public String getProjectName() {
		return this.textField1.getText();
	}

	public String getURL() {
		return this.textField2.getText();
	}
	
	public Boolean isValidated() {
		return this.form_validated;
	}

	public void setVisible(Boolean b) {
		super.setVisible(b);
	}
}
