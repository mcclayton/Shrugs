package com.shrugs.app.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class OptionsToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	// Main JFrame, used to attach a color chooser to it
	private static JFrame mainFrame;
	// Color used to remember last chosen color from color picker
	private static Color newColor = null;
	
	public OptionsToolBar(JFrame jFrame) {
		this.setFloatable(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.addButtons(this);
		this.setVisible(true);
	}
	
	public void addButtons(JToolBar jtbToolBar) {

		// Test Button
		JButton testButton = new JButton("Test");
		
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Test Button Pressed");
			}
		});
		jtbToolBar.add(testButton);
		
		// Test Button
		JButton testButton2 = new JButton("Color Chooser");
		
		testButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newColor = JColorChooser.showDialog(mainFrame, "Choose Location Color", (newColor==null)?Color.BLACK:newColor);
			}
		});
		jtbToolBar.add(testButton2);
		

		jtbToolBar.addSeparator();
		
		// Test Text Field
		JTextField testTextField = new JTextField("Enter Text Here");
		testTextField.setEditable(true);
		testTextField.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Text Field Event");
			}
		});
		jtbToolBar.add(testTextField);
	}

}