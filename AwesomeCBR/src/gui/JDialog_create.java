package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.io.File;

public class JDialog_create extends JDialog {
	// Properties.
	private static final long serialVersionUID = -4325429234968345424L;
	private List<String> not_available_names;
	private JTextField textField1;
	private JTextField textField2;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private boolean form_validated;
	private String file_path;
	private String old_value;
	private JFileChooser fc = new JFileChooser();
	
	private Settings s = new Settings();

	// Constructors.
	public JDialog_create(JFrame par, List<String> not_available_names, String label, String old_value, String title) {
		super(par, title, true);
		
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
		lblNewLabel.setMinimumSize(new Dimension(s.tabs_first_col_width, s.JTextField_height));
		lblNewLabel.setMaximumSize(new Dimension(32767, s.JTextField_height));
		//lblNewLabel.setPreferredSize(new Dimension(s.tabs_first_col_width, s.JTextField_height));
		p1.add(lblNewLabel);
		
		contentPane.add(p1);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p2.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		textField1 = new JTextField();
		textField1.setMinimumSize(new Dimension(250, s.JTextField_height));
		textField1.setMaximumSize(new Dimension(32767, s.JTextField_height));
		textField1.setPreferredSize(new Dimension(250, s.JTextField_height));
		textField1.setFont(s.font_normal);
		textField1.setText(old_value);
		textField1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == KeyEvent.VK_ENTER && btnNewButton.isEnabled()) {
					submitForm();
				}
			}
		});
		textField1.getDocument().addDocumentListener(
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
		);
		p2.add(textField1);
		
		contentPane.add(p2);
		
		
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
		p3.setBorder(new EmptyBorder(15, 5, 5, 5));
		
		JLabel lblNew = new JLabel("Dataset:");
		lblNew.setHorizontalAlignment(SwingConstants.LEFT);
		lblNew.setMinimumSize(new Dimension(s.tabs_first_col_width, s.JTextField_height));
		lblNew.setMaximumSize(new Dimension(32767, s.JTextField_height));
		//lblNewLabel.setPreferredSize(new Dimension(s.tabs_first_col_width, s.JTextField_height));
		p3.add(lblNew);
		
		contentPane.add(p3);
		
		
		
		
		JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
		p4.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		textField2 = new JTextField();
		textField2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//fc.setSelectedFile(new File(textField.getText()+".acbr"));
				int returnVal = fc.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//JDialog_create.this.file_path = fc.getSelectedFile().getAbsolutePath();
					//JDialog_create.this.form_validated = true;
					//JDialog_create.this.setVisible(false);
				}
				else {
					//JDialog_create.this.form_validated = false;
				}
			}
		});
		textField2.setMinimumSize(new Dimension(250, s.JTextField_height));
		textField2.setMaximumSize(new Dimension(32767, s.JTextField_height));
		textField2.setPreferredSize(new Dimension(250, s.JTextField_height));
		textField2.setFont(s.font_normal);
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
		});
		textField2.getDocument().addDocumentListener(
			new DocumentListener() {
				public void changedUpdate(DocumentEvent e) { warn(); }
				public void removeUpdate(DocumentEvent e) { warn(); }
				public void insertUpdate(DocumentEvent e) { warn(); }

				public void warn() {
					if(! textField2.getText().equals(JDialog_create.this.old_value) && JDialog_create.this.not_available_names.contains(textField2.getText())) {
						//lblNewLabel_1.setVisible(true);
						lblNewLabel_1.setText("Name already used!");
						btnNewButton.setEnabled(false);
					}
					else {
						lblNewLabel_1.setText("");
						if(textField2.getText().length() == 0) {
							btnNewButton.setEnabled(false);
						}
						else {
							btnNewButton.setEnabled(true);
						}
					}
				}
			}
		);*/
		p4.add(textField2);
		
		contentPane.add(p4);
		
		
		
		
		JPanel pf = new JPanel();
		pf.setLayout(new BoxLayout(pf, BoxLayout.X_AXIS));
		
		JPanel pf_spacer = new JPanel();
		pf_spacer.setLayout(new BoxLayout(pf_spacer, BoxLayout.X_AXIS));
		pf_spacer.setBorder(new EmptyBorder(5, 5, 5, 5));
		pf_spacer.setMinimumSize(new Dimension(300, 0));
		pf_spacer.setMaximumSize(new Dimension(300, 32767));
		pf_spacer.setPreferredSize(new Dimension(300, 0));
		
		lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblNewLabel_1.setMinimumSize(new Dimension(0, s.JTextField_height));
		lblNewLabel_1.setMaximumSize(new Dimension(32767, s.JTextField_height));
		lblNewLabel_1.setPreferredSize(new Dimension(200, s.JTextField_height));
		pf_spacer.add(lblNewLabel_1);
		pf.add(pf_spacer);
		
		JPanel second_right = new JPanel();
		second_right.setLayout(new BoxLayout(second_right, BoxLayout.X_AXIS));
		second_right.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		btnNewButton = new JButton("OK");
		btnNewButton.setEnabled(false);
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
		
		JDialog_create.this.form_validated = true;
		JDialog_create.this.setVisible(false);
	}
	
	public String getProjectName() {
		return this.textField1.getText();
	}

	public String getUrl() {
		return this.file_path;
	}
	
	public Boolean isValidated() {
		return this.form_validated;
	}

	public void setVisible(Boolean b) {
		super.setVisible(b);
	}
}
