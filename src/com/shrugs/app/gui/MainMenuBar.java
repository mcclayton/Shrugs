package com.shrugs.app.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	public MainMenuBar()
	{
		JMenu fileMenu, editMenu;
		JMenuItem openMenuItem, saveMenuItem, exportMenuItem;


		//Build the file menu.
		fileMenu = new JMenu("File");
		this.add(fileMenu);

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
		this.add(editMenu);
	}

}
