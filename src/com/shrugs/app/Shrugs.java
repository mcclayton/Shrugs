package com.shrugs.app;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class Shrugs {
    
    public static void main(String[] args) {  
        // Initialize and display a JFrame that contains a DrawableView
    	JFrame jFrame = new JFrame();
        jFrame.setTitle("Shrugs");
        jFrame.setSize(640, 480);
        jFrame.setBackground(Color.LIGHT_GRAY);
        
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
                public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        Container cPane = jFrame.getContentPane();
        cPane.add(new DrawableView());	// Add the drawable view, canvas, to the content pane
        
        jFrame.setJMenuBar(MainMenuBar.getMenuBar());	// Add the menu bar to the JFrame
        jFrame.setVisible(true);
    }
}