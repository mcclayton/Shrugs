package com.shrugs.app.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class OptionsToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	private static JFrame mainFrame; // Main JFrame, used to attach a color chooser to it
	private static Color boxBackgroundColor = Color.WHITE; // Color used to remember last chosen color from color picker
	private static final String[] BOX_TYPES = {
		"div",		//0
		"text",		//1
		"image"		//2
	};
	private static String boxMode = BOX_TYPES[0];	// Used to tell what box mode the user is in.
	
	public OptionsToolBar(JFrame jFrame) {
		this.setFloatable(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.addButtons(this);
		this.setVisible(true);
	}
	
	public void addButtons(final JToolBar jtbToolBar) {

		// Box background selector
		final JButton backgroundColorButton = new JButton("Paint Color");
		
		backgroundColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = null;
				newColor = JColorChooser.showDialog(mainFrame, "Choose Location Color", (boxBackgroundColor==null)?Color.BLACK:boxBackgroundColor);
				if (newColor != null) {
					boxBackgroundColor = newColor;
				}
			}
		});
		jtbToolBar.add(backgroundColorButton);
				
		// Box type selector
		final String[] boxTypes = { "Div", "Text", "Image"}; 
        //Create the combo box, select the item at index 0 by default
        final JComboBox boxTypeCombo = new JComboBox(boxTypes);
        boxTypeCombo.setSelectedIndex(0);
        boxTypeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JComboBox cb = (JComboBox) e.getSource();
                String boxType = (String) cb.getSelectedItem();
                
                if (boxType.equals("Div")) {
                	// Set the box mode to div
                	boxMode = BOX_TYPES[0];
                	
                	// Update the toolbar components
                	jtbToolBar.removeAll();
                	jtbToolBar.add(backgroundColorButton);
                	jtbToolBar.add(boxTypeCombo);
                    jtbToolBar.repaint();
                } else if (boxType.equals("Text")) {
                	// Set the box mode to text
                	boxMode = BOX_TYPES[1];
                	
                	// Update the toolbar components
                	jtbToolBar.removeAll();
                	jtbToolBar.add(backgroundColorButton);
                	jtbToolBar.add(new JTextField("Enter text..."));
                	jtbToolBar.add(boxTypeCombo);
                    jtbToolBar.repaint();
                } else if (boxType.equals("Image")) {
                	// Set the box mode to image
                	boxMode = BOX_TYPES[2];
                	
                	// Update the toolbar components
                	jtbToolBar.removeAll();
                	jtbToolBar.add(new JButton("Upload"));
                	jtbToolBar.add(boxTypeCombo);
                    jtbToolBar.repaint();
                } 
            }
        });
        jtbToolBar.add(boxTypeCombo);
		

	}
	
	public static Color getBoxBackgroundColor() {
		return boxBackgroundColor;
	}
	
	public static String getBoxMode() {
		return boxMode;
	}

}