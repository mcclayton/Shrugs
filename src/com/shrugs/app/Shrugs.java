package com.shrugs.app;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.shrugs.app.gui.DrawableView;
import com.shrugs.app.gui.MainMenuBar;
import com.shrugs.app.gui.OptionsPane;


public class Shrugs {

	public static void main(String[] args) {  
		// Build the Shrugs GUI and then set it
		SwingUtilities.invokeLater(new Runnable() 
	    {
	        public void run() 
	        {
	        	buildAndShowGUI();
	        }
	    });
	}
	
	public static void buildAndShowGUI() {
		// Initialize the main JFrame
		JFrame jFrame = new JFrame();
		jFrame.setTitle("Shrugs");
		jFrame.setSize(640, 480);
		jFrame.setBackground(Color.LIGHT_GRAY);

		// Define how the JFrame should exit
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Get the JFrame's content pane
		Container cPane = jFrame.getContentPane();
		
		// Create a split pane that contains a scrollable options pane on the left and a DrawableView on the right
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new OptionsPane(), new DrawableView());
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);
		// Add the splitPane to the content pane
		cPane.add(splitPane);

		// Add the menu bar to the JFrame
		jFrame.setJMenuBar(new MainMenuBar());
		jFrame.setVisible(true);
	}
}