package com.shrugs.app.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class OptionsToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	private static JFrame mainFrame; // Main JFrame, used to attach a color chooser to it
	private static Color boxBackgroundColor = Color.WHITE; // Color used to remember last chosen color from color picker
	
	public OptionsToolBar(JFrame jFrame) {
		this.setFloatable(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.addButtons(this);
		this.setVisible(true);
	}
	
	public void addButtons(JToolBar jtbToolBar) {

		// Test Button
		JButton backgroundColorButton = new JButton("Paint Color");
		
		backgroundColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boxBackgroundColor = JColorChooser.showDialog(mainFrame, "Choose Location Color", (boxBackgroundColor==null)?Color.BLACK:boxBackgroundColor);
			}
		});
		jtbToolBar.add(backgroundColorButton);
		

		//jtbToolBar.addSeparator();
	}
	
	public static Color getBoxBackgroundColor() {
		return boxBackgroundColor;
	}

}