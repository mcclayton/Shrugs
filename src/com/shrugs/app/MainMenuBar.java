package com.shrugs.app;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenuBar {

	/*
	 * Call this static method to build and return the main JMenuBar
	 */
	public static JMenuBar getMenuBar()
	{
		JMenuBar menuBar;
		JMenu fileMenu, editMenu;
		JMenuItem openMenuItem, saveMenuItem, exportMenuItem;


		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the file menu.
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		//Build the group of file menu's JMenuItems
		openMenuItem = new JMenuItem("Open Project");
		fileMenu.add(openMenuItem);
		saveMenuItem = new JMenuItem("Save Project");
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		exportMenuItem = new JMenuItem("Export");
		fileMenu.add(exportMenuItem);
		
		//Build the edit menu.
		editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		return menuBar;
	}

}
