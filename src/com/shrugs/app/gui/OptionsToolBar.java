package com.shrugs.app.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OptionsToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	private static JFrame mainFrame; // Main JFrame, used to attach a color chooser to it
	private static Color boxBackgroundColor = Color.WHITE; // Color used to remember last chosen color from color picker
	private static Color textColor = Color.BLACK; // Color used to remember last chosen color from color picker
	private static final String DEFAULT_IMAGE_PATH = "./img/placeholder.png";
	private static String imagePath = DEFAULT_IMAGE_PATH; // Path of image to be drawn
	private static final String[] BOX_TYPES = {
		"Div",		//0
		"Text",		//1
		"Image"		//2
	};
	private static final String[] FONT_SIZES = {
		"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72"
	};
	private static String boxMode = BOX_TYPES[0];	// Used to tell what box mode the user is in.
	private static final JTextField textField = new JTextField("Enter text...");
	final static JComboBox fontSizesCombo = new JComboBox(FONT_SIZES);

	public OptionsToolBar(JFrame jFrame) {
		this.setFloatable(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.addButtons(this);
		this.setVisible(true);
	}

	public void addButtons(final JToolBar jtbToolBar) {
		// Set the default font size to 12
		fontSizesCombo.setSelectedIndex(4);
		
		// Image selector
		final JButton imageButton = new JButton("Choose Image");

		imageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser imageFileChooser = new JFileChooser();
				imageFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));

				try {
					//Set the chooser directory to be the current directory
					imageFileChooser.setCurrentDirectory(new File(new File(".").getCanonicalPath()));
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				int returnVal = imageFileChooser.showDialog(null, "Open");

				if (returnVal == JFileChooser.APPROVE_OPTION) 
				{
					imagePath = imageFileChooser.getSelectedFile().getAbsolutePath();
				}
			}
		});
		

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
		
		// Box text color selector
		final JButton textColorButton = new JButton("Font Color");

		textColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = null;
				newColor = JColorChooser.showDialog(mainFrame, "Choose Location Color", (textColor==null)?Color.BLACK:textColor);
				if (newColor != null) {
					textColor = newColor;
				}
			}
		});
		jtbToolBar.add(backgroundColorButton);
		
		//Create the combo box, select the item at index 0 by default
		final JComboBox boxTypeCombo = new JComboBox(BOX_TYPES);
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
					jtbToolBar.add(textColorButton);
					jtbToolBar.add(fontSizesCombo);
					jtbToolBar.add(textField);
					jtbToolBar.add(boxTypeCombo);
					jtbToolBar.repaint();
				} else if (boxType.equals("Image")) {
					// Set the box mode to image
					boxMode = BOX_TYPES[2];

					// Update the toolbar components
					jtbToolBar.removeAll();
					jtbToolBar.add(imageButton);
					jtbToolBar.add(boxTypeCombo);
					jtbToolBar.repaint();
				} 
			}
		});
		jtbToolBar.add(boxTypeCombo);


	}

	public static String getBoxImagePath() {
		if (imagePath == null) {
			return DEFAULT_IMAGE_PATH;
		}
		return imagePath;
	}
	
	public static String getBoxText() {
		return textField.getText();
	}
	
	public static int getBoxTextSize() {
		return Integer.parseInt((String)fontSizesCombo.getSelectedItem());
	}
	
	public static Color getBoxTextColor() {
		return textColor;
	}

	public static Color getBoxBackgroundColor() {
		return boxBackgroundColor;
	}

	public static String getBoxMode() {
		return boxMode;
	}

}