package gui;

import java.awt.Dimension;
import java.util.List;
import javax.swing.BoxLayout;
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
public class JDialog_CRUD_Project extends JDialog {
	private List<String> not_available_names;
	private JTextField textField1;
	private JTextField textField2;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	private boolean form_validated;
	private String old_value;
	private JFileChooser fc = new JFileChooser();

	public JDialog_CRUD_Project(JFrame par, List<String> not_available_names, String title, CBRProject p) {
		super(par, title, true);
		String label = "Name:";
	
		String old_value = (p == null ? "" : p.getName());
		String old_value2 = (p == null ? "" : p.getDataset());
		
		setResizable(false);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		this.old_value = old_value;
		this.not_available_names = not_available_names;
		this.form_validated = false;
		
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		lblNewLabel = new JLabel(label);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setMinimumSize(new Dimension(Settings.tabs_first_col_width, Settings.JTextField_height));
		lblNewLabel.setMaximumSize(new Dimension(32767, Settings.JTextField_height));
		//lblNewLabel.setPreferredSize(new Dimension(Settings.tabs_first_col_width, Settings.JTextField_height));
		p1.add(lblNewLabel);
		
		contentPane.add(p1);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p2.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		textField1 = new JTextField();
		textField1.setMinimumSize(new Dimension(250, Settings.JTextField_height));
		textField1.setMaximumSize(new Dimension(32767, Settings.JTextField_height));
		textField1.setPreferredSize(new Dimension(250, Settings.JTextField_height));
		textField1.setFont(Settings.font_normal);
		textField1.setText(old_value);
		/*textField1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == KeyEvent.VK_ENTER && btnNewButton.isEnabled()) {
					submitForm();
				}
			}
		});*/
		/*textField1.getDocument().addDocumentListener(
			new DocumentListener() {
				public void changedUpdate(DocumentEvent e) { warn(); }
				public void removeUpdate(DocumentEvent e) { warn(); }
				public void insertUpdate(DocumentEvent e) { warn(); }

				public void warn() {
					if(! textField1.getText().equals(JDialog_create.this.old_value) && JDialog_create.this.not_available_names.contains(textField1.getText())) {
						//lblNewLabel_1.setVisible(true);
						lblNewLabel_1.setText("Name already used!");
						btnNewButton.setEnabled(false);
					}
					else {
						lblNewLabel_1.setText("");
						if(textField1.getText().length() == 0) {
							btnNewButton.setEnabled(false);
						}
						else {
							btnNewButton.setEnabled(true);
						}
					}
				}
			}
		);*/
		p2.add(textField1);
		
		contentPane.add(p2);
		
		
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
		p3.setBorder(new EmptyBorder(15, 5, 5, 5));
		
		JLabel lblNew = new JLabel("Dataset:");
		lblNew.setHorizontalAlignment(SwingConstants.LEFT);
		lblNew.setMinimumSize(new Dimension(Settings.tabs_first_col_width, Settings.JTextField_height));
		lblNew.setMaximumSize(new Dimension(32767, Settings.JTextField_height));
		//lblNewLabel.setPreferredSize(new Dimension(Settings.tabs_first_col_width, Settings.JTextField_height));
		p3.add(lblNew);
		
		contentPane.add(p3);
		
		JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
		p4.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		textField2 = new JTextField();
		/*textField2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//fc.setSelectedFile(new File(textField.getText()+".acbr"));
				int returnVal = fc.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//JDialog_create.this.file_path = fc.getSelectedFile().getAbsolutePath();
					//JDialog_create.this.form_validated = true;
					//JDialog_create.this.setVisible(false);
					textField2.setText(fc.getSelectedFile().getAbsolutePath());
				}
				else {
					//JDialog_create.this.form_validated = false;
				}
			}
		});*/
		textField2.setMinimumSize(new Dimension(250, Settings.JTextField_height));
		textField2.setMaximumSize(new Dimension(32767, Settings.JTextField_height));
		textField2.setPreferredSize(new Dimension(250, Settings.JTextField_height));
		textField2.setFont(Settings.font_normal);
		textField2.setText(old_value2);
		/*textField2.addMouseClickedListener(new MouseListener() {
		    public void mouseClicked(MouseEvent e) {

		    }
		}*/
		//textField2.setEditable(false);
		//textField2.setText(old_value);
		/*textField2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == KeyEvent.VK_ENTER && btnNewButton.isEnabled()) {
					submitForm();
				}
			}
		});*/
		/*textField2.getDocument().addDocumentListener(
			new DocumentListener() {
				public void changedUpdate(DocumentEvent e) { warn(); }
				public void removeUpdate(DocumentEvent e) { warn(); }
				public void insertUpdate(DocumentEvent e) { warn(); }

				public void warn() {
					// TODO
				}
			}
		);*/
		p4.add(textField2);
		JButton btn_browse = new JButton("Browse...");
		btn_browse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//fc.setSelectedFile(new File(textField.getText()+".acbr"));
				int returnVal = fc.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//JDialog_create.this.file_path = fc.getSelectedFile().getAbsolutePath();
					//JDialog_create.this.form_validated = true;
					//JDialog_create.this.setVisible(false);
					textField2.setText(fc.getSelectedFile().getAbsolutePath());
				}
				else {
					//JDialog_create.this.form_validated = false;
				}
			}
		});
		p4.add(btn_browse);
		
		contentPane.add(p4);
		
		JPanel pf = new JPanel();
		pf.setLayout(new BoxLayout(pf, BoxLayout.X_AXIS));
		
		JPanel pf_spacer = new JPanel();
		pf_spacer.setLayout(new BoxLayout(pf_spacer, BoxLayout.X_AXIS));
		pf_spacer.setBorder(new EmptyBorder(5, 5, 5, 5));
		pf_spacer.setMinimumSize(new Dimension(200, 1));
		pf_spacer.setMaximumSize(new Dimension(200, 32767));
		pf_spacer.setPreferredSize(new Dimension(200, 1));
		
		pf.add(pf_spacer);
		
		JPanel second_right = new JPanel();
		second_right.setLayout(new BoxLayout(second_right, BoxLayout.X_AXIS));
		second_right.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		btnNewButton = new JButton("OK");
		//btnNewButton.setEnabled(false);
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
		/*this.fc.setSelectedFile(new File(textField.getText()+".acbr"));
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			JDialog_create.this.file_path = fc.getSelectedFile().getAbsolutePath();
			JDialog_create.this.form_validated = true;
			JDialog_create.this.setVisible(false);
		}
		else {
			JDialog_create.this.form_validated = false;
		}*/
		
		int problems = 0;
		
		if(textField1.getText().equals("")) {
			JOptionPane.showMessageDialog(JDialog_CRUD_Project.this, "Please give a project name.");
			problems++;
		}
		
		if(! textField1.getText().equals(JDialog_CRUD_Project.this.old_value) && JDialog_CRUD_Project.this.not_available_names.contains(textField1.getText())) {
			JOptionPane.showMessageDialog(JDialog_CRUD_Project.this, "Name already exist.");
			problems++;
		}
		
		if(!new File(textField2.getText()).exists()) {
			JOptionPane.showMessageDialog(JDialog_CRUD_Project.this, "Dataset is not valid.");
			problems++;
		}
		
		//if textField2 not a file
		//if textField2 not amos
		if(problems == 0) {
			JDialog_CRUD_Project.this.form_validated = true;
			JDialog_CRUD_Project.this.setVisible(false);
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
