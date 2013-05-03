package gui;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JDialog_create extends JDialog {
	// Properties.
	private static final long serialVersionUID = -4325429234968345424L;
	private final JPanel contentPanel = new JPanel();
	private List<String> not_available_names;
	private JTextField textField;
	private JButton btnNewButton;
	private JLabel lblNewLabel_1;
	private boolean form_validated;
	private String file_path;
	private JFileChooser fc;

	// Constructors.
	public JDialog_create(JFrame par, List<String> not_available_names) {
		super(par, "Awsome CBR - Add new project", true);
		setResizable(false);
		this.not_available_names = not_available_names;
		this.form_validated = false;

		setBounds(100, 100, 298, 105);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(10, 13, 31, 14);
		contentPanel.add(lblNewLabel);


		fc = new JFileChooser();
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == KeyEvent.VK_ENTER && btnNewButton.isEnabled()) {
					submitForm();
				}
			}
		});
		textField.setBounds(51, 10, 227, 20);

		btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitForm();
			}
		});
		btnNewButton.setBounds(231, 41, 47, 23);
		btnNewButton.setEnabled(false);
		contentPanel.add(btnNewButton);

		textField.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						//textField.setBackground(new Color(255, 0, 0));
						warn();
					}
					public void removeUpdate(DocumentEvent e) {
						//textField.setBackground(new Color(0, 255, 0));
						warn();
					}
					public void insertUpdate(DocumentEvent e) {
						//textField.setBackground(new Color(0, 0, 255));
						warn();
					}

					public void warn() {
						//JDialog_create.this.setTitle(textField.getText().length() + "");
						if(JDialog_create.this.not_available_names.contains(textField.getText())) {
							//textField.setBackground(new Color(255, 0, 0));
							lblNewLabel_1.setVisible(true);
							btnNewButton.setEnabled(false);
						}
						else {
							if(textField.getText().length() == 0) {
								btnNewButton.setEnabled(false);
								//JDialog_create.this.setTitle(textField.getText());
							}
							else {
								//textField.setBackground(new Color(255,255, 255));
								btnNewButton.setEnabled(true);
							}

							lblNewLabel_1.setVisible(false);
						}
					}
				}
				);

		contentPanel.add(textField);

		lblNewLabel_1 = new JLabel("Name already used!");
		lblNewLabel_1.setVisible(false);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(123, 45, 96, 14);
		contentPanel.add(lblNewLabel_1);


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
		return this.textField.getText();
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
