package com.shrugs.app;

import javax.swing.JFrame;

public class Shrugs {
	private static void createMainWindow() {
		JFrame myWindow = new JFrame();
		myWindow.setSize(640,480); 
		myWindow.setTitle("Shrugs"); 
		myWindow.setResizable(true); 
		myWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myWindow.setVisible(true);
	}
	
	public static void main(String[] args) {
		createMainWindow();
	}
}