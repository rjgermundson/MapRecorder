package Locator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InputDialogBox extends JDialog {
	private static final long serialVersionUID = 1L;
	private PrintWriter output;
	private double currX = 0.0;
	private double currY = 0.0;
	private String abbrev = "";
	private String full = "";

	public InputDialogBox(String title) {
		setTitle(title);
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints con = new GridBagConstraints();
		
		JLabel abbrevText = new JLabel("Abbreviated Name:");
		con.fill = GridBagConstraints.HORIZONTAL;
		con.gridy = 0;
		con.ipadx = 10;
		add(abbrevText, con);
		
		
		JLabel fullText = new JLabel("Full Name:");
		con.gridy = 2;
		add(fullText, con);
		
		
		JTextField abbrevField = new JTextField();
		con.gridy = 1;
		add(abbrevField, con);
		
		JTextField fullField = new JTextField();
		fullField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String abbrevName = abbrevField.getText();
					String fullName = fullField.getText();
					if (!abbrevName.matches(" *") && !fullName.matches(" *")) {
						abbrev = abbrevName;
						full = fullName;
						writeToOutput();
						abbrevField.setText(abbrev);
						fullField.setText(full);
					}
				}
			}
		});
		con.gridy = 3;
		add(fullField, con);
		
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String abbrevName = abbrevField.getText();
				String fullName = fullField.getText();
				if (!abbrevName.matches(" *") && !fullName.matches(" *")) {
					abbrev = abbrevName;
					full = fullName;
					writeToOutput();
					abbrevField.setText(abbrev);
					fullField.setText(full);
				}
			}
			
		});
		con.gridy = 4;
		add(submitButton, con);
		pack();
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	}
	
	/**
	 * Sets where the inputs from the dialog box will be written to
	 * 
	 * @requires output != null
	 * @param output The stream to be written to
	 */
	public void setWriter(PrintWriter output) {
		this.output = output;
	}
	
	/**
	 * Sets the xCoord and yCoord of the given input
	 * @param xCoord The x coordinate of the input
	 * @param yCoord The y coordinate of the input
	 */
	public void setCoordinate(double xCoord, double yCoord) {
		currX = xCoord;
		currY = yCoord;
	}
	
	/**
	 * Writes the abbreviated name, full name and coordinates in a tab
	 * separated line in the output file
	 */
	private void writeToOutput() {
		output.println(abbrev + "\t" + full + "\t" + currX + "\t" + currY);
		this.abbrev = "";
		this.full = "";
		this.currX = 0.0;
		this.currY = 0.0;
		output.flush();
		this.dispose();
	}
}
