package com.shrugs.app;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawableView extends JPanel implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	final static float dash[] = {5.0f};
    final static BasicStroke dashedStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
    final static BasicStroke solidStroke = new BasicStroke();
	
	private int startX, startY, endX, endY = 0;	// Coordinates of box that is currently being drawn
	boolean isDragging = false;	// Used to tell if the user is currently drawing/dragging a box
    
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
            		boxList.add(new Box(startX, startY, endX, endY)); // Add a new box to the boxList
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
		// TODO Auto-generated method stub
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
        	g2.drawRect(startX, startY, endX-startX, endY-startY);
        }
        
        // Draw all the box objects
        for(Box b : boxList) {
        	g2.setStroke(solidStroke);
        	g2.drawRect(b.getStartX(), b.getStartY(), b.getEndX()-b.getStartX(), b.getEndY()-b.getStartY());
        }
    }
}