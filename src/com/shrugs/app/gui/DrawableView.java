package com.shrugs.app.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.shrugs.app.components.Box;


public class DrawableView extends JPanel implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	final static float dash[] = {5.0f};
    final static BasicStroke dashedStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
    final static BasicStroke solidStroke = new BasicStroke();
	
	private int startX, startY, endX, endY = 0;	// Coordinates of box that is currently being drawn
	private Box parent, child;
	boolean isDragging = false;	// Used to tell if the user is currently drawing/dragging a box
	boolean cord1, cord2; // Used to tell if the coordinates of the object are inside another object
	boolean make; // Used to tell if the object should be made or not 

    
	// Lists of drawable objects
	ArrayList<Box> boxList = new ArrayList<Box>();
	
    public DrawableView() {
        addMouseListener(new MouseAdapter() {
            @Override
                public void mousePressed(MouseEvent evt) {
                startX = evt.getX();        
                startY = evt.getY();
                endX = startX;
                endY = startY;
                isDragging = true;	
                repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent evt) {
            	if (isDragging) {
            		isDragging = false;
            		make = true;
            		parent = null;
            		child = null;

            		//Find the closest relative parent if the coordinates exists inside any objects
                    //prevent any object that break the borders of another box object from being created
                    for(Box b : boxList) {
                    	cord1 = b.coordinatesInsideBox(startX, startY);
                    	cord2 = b.coordinatesInsideBox(Math.min(startX, endX)+Math.abs(startX - endX), Math.min(startY, endY)+Math.abs(startY - endY));
                    	
                    	//Find if the object is crossing any boarders of another object
                    	if ((!cord1&&cord2)||(cord1&&!cord2))
                    		make = false;
                    	
                    	//
                    	if(b.coordinatesInsideBox(startX,startY))
                    		if (parent == null || (parent.getStartX() <= b.getStartX()))
                    			parent = b;
                    }

                    //make the new box
                    if (make)
                    	boxList.add(new Box(Math.min(startX, endX), Math.min(startY, endY), Math.abs(startX - endX), Math.abs(startY - endY), parent));

            		repaint();
            	}
            }
        });
        
        addMouseMotionListener(this);
    }

	@Override
	public void mouseDragged(MouseEvent evt) {
        endX = evt.getX();        
        endY = evt.getY();
        repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// highlight all relative boxes
        for(Box b : boxList) {
        	if(b.coordinatesInsideBox(arg0.getX(), arg0.getY()))
        	{
        		b.setHighlight(true);
        		//Debugging text, will be removed 
        		if (b.getParent() == null)
        			System.out.println("box # " + b.getStartX() + " :: no parent");
        		else
        			System.out.println("box # " + b.getStartX() + " :: parent box # " + b.getParent().getStartX());
        	}
        	else
        		b.setHighlight(false);
        }
        repaint();
	}
	
	
	
	/*
	 * Everything that needs to be drawn on a repaint() call goes here.
	 */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = ((Graphics2D) g);
        
        // If the user is drawing a rectangle, draw it with a dashed stroke
        if (isDragging) {
        	g2.setStroke(dashedStroke);
        	g2.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(startX - endX), Math.abs(startY - endY));
        }
        
        // Draw all the box objects based on the values in the box object
        for(Box b : boxList) {
        	g2.setStroke(solidStroke);
        	if (b.gethighlight())
        		g2.setColor(Color.red);
        	else
        		g2.setColor(Color.black);
        	g2.drawRect(b.getStartX(), b.getStartY(), b.getEndX(), b.getEndY());
        }
    }
}