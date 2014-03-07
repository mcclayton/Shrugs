package com.shrugs.app.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.shrugs.app.Export;
import com.shrugs.app.Shrugs;
import com.shrugs.app.IOManager;
import com.shrugs.app.components.BodyBox;
import com.shrugs.app.components.ImageBox;

public class MainMenuBar extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JMenu fileMenu, helpMenu;
	private JMenuItem openMenuItem, saveMenuItem, exportMenuItem, helpMenuItem, aboutMenuItem, newMenuItem;
	private String output;
	private String savepath;

	public MainMenuBar() {
		// Build the file menu.
		fileMenu = new JMenu("File");
		this.add(fileMenu);

		// Build the group of file menu's JMenuItems
		openMenuItem = new JMenuItem("Load Project");
		fileMenu.add(openMenuItem);
		saveMenuItem = new JMenuItem("Save Project");
		fileMenu.add(saveMenuItem);
		newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		fileMenu.addSeparator();
		exportMenuItem = new JMenuItem("Export");
		fileMenu.add(exportMenuItem);

		helpMenu = new JMenu("Help");
		this.add(helpMenu);
		helpMenuItem = new JMenuItem("How-To");
		helpMenu.add(helpMenuItem);
		aboutMenuItem = new JMenuItem("About Shrugs");
		helpMenu.add(aboutMenuItem);

		// Add action listeners to menu items
		openMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		exportMenuItem.addActionListener(this);
		helpMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);
		newMenuItem.addActionListener(this);
	}

	// Handle menu item clicks
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JMenuItem) { // checks to see if a menu item was
											// pressed
			JMenuItem itemClicked = (JMenuItem) source;
			if (itemClicked == openMenuItem) { // TODO: Add "Loading File" popup
												// and disable input
				try {
					JFileChooser inputFileChooser = new JFileChooser();
					inputFileChooser.setCurrentDirectory(new File(new File(".")
							.getCanonicalPath()));
					int retval = inputFileChooser.showDialog(null, "Open");

					if (retval == JFileChooser.APPROVE_OPTION) {
						output = inputFileChooser.getSelectedFile()
								.getName();
					} else if (retval == JFileChooser.CANCEL_OPTION) {
						return;
					}
					DrawableView.Load(IOManager.Load(output));

				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error during load.\n Could not load from './"+output+"'");
					e1.printStackTrace();
				}
			} else if (itemClicked==newMenuItem) { 
				//Create new project
				int result = JOptionPane.showConfirmDialog(this,"Are you sure you wish to create a new Shrugs project?\nAny unsaved data will be lost.","New Project",JOptionPane.YES_NO_CANCEL_OPTION);
	            switch(result){
	                case JOptionPane.YES_OPTION:
	                	if (ImageBox.ImageList != null) {
	                		ImageBox.ImageList.clear();
	                	}
	                	DrawableView.bodyBox = new BodyBox(0, 0, DrawableView.getViewWidth(), DrawableView.getViewHeight());
	                    return;
	                case JOptionPane.NO_OPTION:
	                    return;
	                case JOptionPane.CLOSED_OPTION:
	                    return;
	                case JOptionPane.CANCEL_OPTION:
	                    return;
	            }
			} else if (itemClicked==saveMenuItem){ //TODO: Add "Saving File" popup and disable input
				try {
					JFileChooser outputFileChooser = new JFileChooser() {
						private static final long serialVersionUID = 1L;

						@Override
						public void approveSelection() {
							File f = getSelectedFile();
							if (f.exists()) {
								int result = JOptionPane
										.showConfirmDialog(
												this,
												"The shrugs file exists, do you want to overwrite?",
												"Existing file",
												JOptionPane.YES_NO_CANCEL_OPTION);
								switch (result) {
								case JOptionPane.YES_OPTION:
									super.approveSelection();
									return;
								case JOptionPane.NO_OPTION:
									return;
								case JOptionPane.CLOSED_OPTION:
									return;
								case JOptionPane.CANCEL_OPTION:
									cancelSelection();
									return;
								}
							}
							super.approveSelection();
						}
					};

					FileNameExtensionFilter shrugsFileFilter = new FileNameExtensionFilter(
							"Shrugs Files (*.shrug)", "shrug");
					outputFileChooser.addChoosableFileFilter(shrugsFileFilter);
					outputFileChooser.setFileFilter(shrugsFileFilter);
					outputFileChooser.setCurrentDirectory(new File(
							new File(".").getCanonicalPath()));
					int retval = outputFileChooser.showDialog(null, "Save");

					if (retval == JFileChooser.APPROVE_OPTION) {
							savepath = outputFileChooser.getSelectedFile().getName();
					} else if (retval == JFileChooser.CANCEL_OPTION) {
						return;
					}
					IOManager.Save(savepath);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error during save.");
					e1.printStackTrace();
				}
			} else if (itemClicked == exportMenuItem) {
				try {
					JFileChooser exportDirectoryChooser = new JFileChooser() {
						private static final long serialVersionUID = 1L;

						@Override
						public void approveSelection() {
							File fIndexHTML = new File(getSelectedFile()
									.getAbsolutePath() + "/index.html");
							if (fIndexHTML.exists()) {
								int result = JOptionPane
										.showConfirmDialog(
												this,
												"A website named 'index.html' exists in this directory, do you want to overwrite?",
												"Existing file",
												JOptionPane.YES_NO_CANCEL_OPTION);
								switch (result) {
								case JOptionPane.YES_OPTION:
									super.approveSelection();
									return;
								case JOptionPane.NO_OPTION:
									return;
								case JOptionPane.CLOSED_OPTION:
									return;
								case JOptionPane.CANCEL_OPTION:
									cancelSelection();
									return;
								}
							}
							super.approveSelection();
						}
					};

					exportDirectoryChooser
							.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					exportDirectoryChooser.setCurrentDirectory(new File(
							new File(".").getCanonicalPath()));

					int retval = exportDirectoryChooser.showDialog(null,
							"Export");

					if (retval == JFileChooser.APPROVE_OPTION) {
						Export.export(DrawableView.bodyBox,
								".");
						String[] args = {};
						Shrugs.main(args);
					} else if (retval == JFileChooser.CANCEL_OPTION) {
						return;
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Error during export.");
					e1.printStackTrace();
				}
			} else if (itemClicked == aboutMenuItem) {
				JOptionPane
						.showMessageDialog(
								null,
								"Shrugs Helps Regular Users Generate Sites \n"
										+ "A CS 408 Project by Michael Clayton, William King, Brandan Miller, and Vipul Nataraj");
			} else if (itemClicked == helpMenuItem) {
				Icon icon = new ImageIcon("./img/clippy.png");
				JOptionPane
						.showMessageDialog(
								null,
								"To draw an element, click and drag as desired in the main grid.\n"
								+ "A selector for the type of element is located at the top of the application. \n"
								+ "-- Element Types --\n"
								+ "Div: A basic container. Can contain child elements. Color can be changed using the \"color\" button.\n"
								+ "Image: A graphic. The source can be chosen using the \"Choose Photo\" button.\n"
								+ "Text: A text area. Size and color can be configured.",
								"Help", JOptionPane.INFORMATION_MESSAGE, icon);
			}
		}
	}

}
