package com.shrugs.app.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.shrugs.app.Export;
import com.shrugs.app.IOManager;

public class MainMenuBar extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JMenu fileMenu, helpMenu;
	private JMenuItem openMenuItem, saveMenuItem, exportMenuItem, helpMenuItem, aboutMenuItem;
	private String output;
	private String savepath;
	
	public MainMenuBar()
	{
		//Build the file menu.
		fileMenu = new JMenu("File");
		this.add(fileMenu);

		//Build the group of file menu's JMenuItems
		openMenuItem = new JMenuItem("Load Project");
		fileMenu.add(openMenuItem);
		saveMenuItem = new JMenuItem("Save Project");
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		exportMenuItem = new JMenuItem("Export");
		fileMenu.add(exportMenuItem);
		
		helpMenu = new JMenu("Help");
		this.add(helpMenu);
		helpMenuItem = new JMenuItem("How-To");
		helpMenu.add(helpMenuItem);
		aboutMenuItem = new JMenuItem("About Shrugs");
		helpMenu.add(aboutMenuItem);
		
		
		//Add action listeners to menu items
		openMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		exportMenuItem.addActionListener(this);
		helpMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);
	}
	
	//Handle menu item clicks
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();

		if (source instanceof JMenuItem){  //checks to see if a menu item was pressed
			JMenuItem itemClicked = (JMenuItem) source;
			if (itemClicked==openMenuItem){ //TODO: Add "Loading File" popup and disable input
				try {
					JFileChooser inputFileChooser = new JFileChooser();
					inputFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Shrugs Files", "shrug"));
					inputFileChooser.setCurrentDirectory(new File(new File(".").getCanonicalPath()));
					int retval = inputFileChooser.showDialog(null, "open");
					if(retval == inputFileChooser.APPROVE_OPTION){
						output = inputFileChooser.getSelectedFile().getAbsolutePath();
					}
					else if(retval == inputFileChooser.CANCEL_OPTION){
						return;
					}
					DrawableView.Load(IOManager.Load(output));
					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error during load.");
					e1.printStackTrace();
				}
			} else if (itemClicked==saveMenuItem){ //TODO: Add "Saving File" popup and disable input
				try {
					JFileChooser outputFileChooser = new JFileChooser();
					outputFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Shrugs Files", "shrug"));
					outputFileChooser.setCurrentDirectory(new File(new File(".").getCanonicalPath()));
					int retval = outputFileChooser.showDialog(null, "open");
					if(retval == outputFileChooser.APPROVE_OPTION){
						savepath = outputFileChooser.getSelectedFile().getAbsolutePath();
					}
					else if(retval == outputFileChooser.CANCEL_OPTION){
						return;
					}
					IOManager.Save(savepath);
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Error during save.");
					e1.printStackTrace();
				}
			} else if (itemClicked==exportMenuItem){
				try {
					Export.export(DrawableView.bodyBox);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error during export.");
					e1.printStackTrace();
				}
			}
			else if(itemClicked == aboutMenuItem){
					JOptionPane.showMessageDialog(null, "Shrugs: Shrugs Helps Regular Users Generate Sites \n A CS 408 Project by Michael Clayton, William King, Brandan Miller, and Vipul Nataraj");
			}
			else if(itemClicked == helpMenuItem){
					JOptionPane.showMessageDialog(null, "To draw an element, click and drag as desired in the main grid.\nA selector for the type of element is located at the top of the application. \nColors are available from the 'Paint' menu.");
			}
		}
	}

}
