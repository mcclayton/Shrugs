package com.shrugs.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.shrugs.app.gui.DrawableView;
import com.shrugs.app.gui.MainMenuBar;
import com.shrugs.app.gui.OptionsToolBar;


public class Shrugs {
	private static final int WINDOW_WIDTH = 612;
	private static final int WINDOW_HEIGHT = 792;
	
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
		jFrame.setResizable(false);
		jFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		jFrame.setBackground(Color.WHITE);

		// Define how the JFrame should exit
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Get the JFrame's content pane
		Container cPane = jFrame.getContentPane();
		// Set the content pane's layout
		cPane.setLayout(new BorderLayout());
		
		// Add the components to the content pane
		cPane.add(new DrawableView(), BorderLayout.CENTER);
		cPane.add(new OptionsToolBar(jFrame), BorderLayout.NORTH);

		// Add the menu bar to the JFrame
		jFrame.setJMenuBar(new MainMenuBar());
		jFrame.setVisible(true);
	}
}