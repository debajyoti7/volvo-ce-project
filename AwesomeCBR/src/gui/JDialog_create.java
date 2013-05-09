package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
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
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JDialog_create extends JDialog {
	// Properties.
	private static final long serialVersionUID = -4325429234968345424L;
	private List<String> not_available_names;
	private JTextField textField;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private boolean form_validated;
	private String file_path;
	private String old_value;
	
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
		
		
		JPanel first = new JPanel();
		first.setLayout(new BoxLayout(first, BoxLayout.X_AXIS));
		first.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		lblNewLabel = new JLabel(label);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setMinimumSize(new Dimension(s.tabs_first_col_width, s.JTextField_height));
		lblNewLabel.setMaximumSize(new Dimension(32767, s.JTextField_height));
		//lblNewLabel.setPreferredSize(new Dimension(s.tabs_first_col_width, s.JTextField_height));
		first.add(lblNewLabel);
		
		JPanel first2 = new JPanel();
		first2.setLayout(new BoxLayout(first2, BoxLayout.X_AXIS));
		first2.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		textField = new JTextField();
		textField.setMinimumSize(new Dimension(250, s.JTextField_height));
		textField.setMaximumSize(new Dimension(32767, s.JTextField_height));
		textField.setPreferredSize(new Dimension(250, s.JTextField_height));
		textField.setFont(s.font_normal);
		textField.setText(old_value);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == KeyEvent.VK_ENTER && btnNewButton.isEnabled()) {
					submitForm();
				}
			}
		});
		textField.getDocument().addDocumentListener(
			new DocumentListener() {
				public void changedUpdate(DocumentEvent e) { warn(); }
				public void removeUpdate(DocumentEvent e) { warn(); }
				public void insertUpdate(DocumentEvent e) { warn(); }

				public void warn() {
					if(! textField.getText().equals(JDialog_create.this.old_value) && JDialog_create.this.not_available_names.contains(textField.getText())) {
						//lblNewLabel_1.setVisible(true);
						lblNewLabel_1.setText("Name already used!");
						btnNewButton.setEnabled(false);
					}
					else {
						lblNewLabel_1.setText("");
						if(textField.getText().length() == 0) {
							btnNewButton.setEnabled(false);
						}
						else {
							btnNewButton.setEnabled(true);
						}
					}
				}
			}
		);
		first2.add(textField);
		contentPane.add(first);
		contentPane.add(first2);
		
		JPanel second = new JPanel();
		second.setLayout(new BoxLayout(second, BoxLayout.X_AXIS));
		
		JPanel second_left = new JPanel();
		second_left.setLayout(new BoxLayout(second_left, BoxLayout.X_AXIS));
		second_left.setBorder(new EmptyBorder(5, 5, 5, 5));
		second_left.setMinimumSize(new Dimension(300, 0));
		second_left.setMaximumSize(new Dimension(300, 32767));
		second_left.setPreferredSize(new Dimension(300, 0));
		
		lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblNewLabel_1.setMinimumSize(new Dimension(0, s.JTextField_height));
		lblNewLabel_1.setMaximumSize(new Dimension(32767, s.JTextField_height));
		lblNewLabel_1.setPreferredSize(new Dimension(200, s.JTextField_height));
		second_left.add(lblNewLabel_1);
		second.add(second_left);
		
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
		second.add(second_right);
		contentPane.add(second);
		
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
