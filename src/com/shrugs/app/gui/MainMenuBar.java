package com.shrugs.app.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainMenuBar extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JMenu fileMenu, editMenu;
	private JMenuItem openMenuItem, saveMenuItem, exportMenuItem;
	
	public MainMenuBar()
	{
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
		
		//Add action listeners to menu items
		openMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		exportMenuItem.addActionListener(this);
	}
	
	//Handle menu item clicks
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source instanceof JMenuItem){  //checks to see if a menu item was pressed
			JMenuItem itemClicked = (JMenuItem) source;
			if (itemClicked==openMenuItem){
				JOptionPane.showMessageDialog(null, "Open Menu Item Pressed.");
			} else if (itemClicked==saveMenuItem){
				JOptionPane.showMessageDialog(null, "Save Menu Item Pressed.");
			} else if (itemClicked==exportMenuItem){
				JOptionPane.showMessageDialog(null, "Export Menu Item Pressed.");
			}
		}
	}

}
