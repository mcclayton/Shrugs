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

import com.shrugs.app.components.BodyBox;
import com.shrugs.app.components.Box;
import com.shrugs.app.components.BoxStyle;


public class DrawableView extends JPanel implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	final static float dash[] = {5.0f};
	final static BasicStroke dashedStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
	final static BasicStroke solidStroke = new BasicStroke();

	private int startX, startY, endX, endY = 0;	// Coordinates of box that is currently being drawn
	private Box parent;
	boolean isDragging = false;	// Used to tell if the user is currently drawing/dragging a box
	boolean tlcord, trcord, blcord, brcord; // Used to tell if the coordinates of the object are inside another object
	boolean make; // Used to tell if the object should be made or not 

	private BodyBox bodyBox; // The main body box of the web page 

	// Lists of drawable objects
	ArrayList<Box> boxList = new ArrayList<Box>();

	public DrawableView(int width, int height) {
		bodyBox = new BodyBox(0, 0, width-1, height-1);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				startX = evt.getX();        
				startY = evt.getY();

				// Snap the initial coordinates to the body box grid if necessary
				if(bodyBox.coordinatesInsideBox(startX, startY) && bodyBox.gethighlight()) {
					startX = bodyBox.getNearestHSnap(startX);
					startY = bodyBox.getNearestVSnap(startY);
				}

				// Snap the initial coordinates to a box grid if necessary
				for(Box b : boxList) {
					if(b.coordinatesInsideBox(startX, startY) && b.gethighlight()) {
						startX = b.getNearestHSnap(startX);
						startY = b.getNearestVSnap(startY);
					}
				}

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

					// Don't allow the box to be made if width or height is 0.
					if (((endX - startX) == 0) || ((endY - startY) == 0)) {
						make = false;
					}

					//Find the closest relative parent if the coordinates exists inside any objects
					//prevent any object that break the borders of another box object from being created
					for(Box b : boxList) {
						tlcord = b.coordinatesInsideBox(Math.min(startX, endX), Math.min(startY, endY));
						trcord = b.coordinatesInsideBox(Math.min(startX, endX)+Math.abs(startX - endX), Math.min(startY, endY));
						blcord = b.coordinatesInsideBox(Math.min(startX, endX), Math.min(startY, endY)+Math.abs(startY - endY));
						brcord = b.coordinatesInsideBox(Math.min(startX, endX)+Math.abs(startX - endX), Math.min(startY, endY)+Math.abs(startY - endY));


						make = b.lineIntersectsBox(Math.min(startX, endX), Math.min(startY, endY), Math.min(startX, endX)+Math.abs(startX - endX), Math.min(startY, endY)+Math.abs(startY - endY));
						
						//Find if the object is crossing any boarders of another object
						if (!(tlcord && trcord && blcord && brcord) && !(!tlcord && !trcord && !blcord && !brcord)) {
							make = false;
						}
						//debuging System.out.println(tlcord+":"+trcord+":"+blcord+":"+brcord);

						// Set the box's, who is about to be created, parent
						if(b.coordinatesInsideBox(startX,startY)) {
							if (parent == null || (parent.getStartX() <= b.getStartX())) {
								parent = b;
							}
						}
					}

					//make the new box
					if (make) {
						Box newBox = new Box(Math.min(startX, endX), Math.min(startY, endY), Math.max(startX, endX), Math.max(startY, endY), parent);
						BoxStyle newStyle = new BoxStyle();
						newStyle.setBoxColor(OptionsToolBar.getBoxBackgroundColor());
						newBox.setStyle(newStyle);
						boxList.add(newBox);
					}

					repaint();
				}
			}

			@Override
			public void mouseClicked(MouseEvent evt) {

				if (evt.getClickCount() == 2) {
		            startX = evt.getX();        
		            startY = evt.getY();
		            
					for(Box b : boxList) {
						if(b.coordinatesInsideBox(startX, startY) && b.gethighlight()) {
							b.showAttributesMenu();
						}
					}
				}

				repaint();
			}
		});

		addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		endX = evt.getX();        
		endY = evt.getY();

		// Snap the end coordinates to the body box grid if necessary
		if(bodyBox.coordinatesInsideBox(endX, endY) && bodyBox.gethighlight()) {
			endX = bodyBox.getNearestHSnap(endX);
			endY = bodyBox.getNearestVSnap(endY);
		}

		// Snap the end coordinates to a box grid if necessary
		for(Box b : boxList) {
			if(b.coordinatesInsideBox(endX, endY) && b.gethighlight()) {
				endX = b.getNearestHSnap(endX);
				endY = b.getNearestVSnap(endY);
			}
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent mvEvt) {
		bodyBox.setHighlight(true);

		// Highlight the innermost box that the mouse is inside
		for(Box b : boxList) {
			if(b.coordinatesInsideBox(mvEvt.getX(), mvEvt.getY()))
			{
				if (b.getParent() != null) {
					if (b.getParent().gethighlight()) {
						b.getParent().setHighlight(false);        				
					}
				}
				bodyBox.setHighlight(false);
				b.setHighlight(true);
			} else {
				b.setHighlight(false);
			}
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

		// Draw the body box's grid
		if (bodyBox.gethighlight()) {
			// Draw the grid on mouseover
			g2.setColor(new Color(222, 222, 222));
			g2.setStroke(dashedStroke);
			for(Integer vSnap : bodyBox.getVSnaps()) {
				g2.drawLine(bodyBox.getStartX(), vSnap, bodyBox.getStartX()+bodyBox.width(), vSnap);
			}
			for(Integer hSnap : bodyBox.getHSnaps()) {
				g2.drawLine(hSnap, bodyBox.getStartY(), hSnap, bodyBox.getStartY()+bodyBox.height());
			}
		}

		// Draw all the box objects based on the values in the box object
		Color boxBackground;
		for(Box b : boxList) {
			// Draw Box Background
			g2.setStroke(solidStroke);
			boxBackground = b.getStyle().getBoxColorValue();
			g2.setColor(boxBackground);
			g2.fillRect(b.getStartX(), b.getStartY(), b.width(), b.height());

			if (b.gethighlight()) {
				
				// Make sure the grid is a color that won't blend in with the boxes background color
				double luminance = 0.2126*boxBackground.getRed() + 0.7152*boxBackground.getGreen() + 0.0722*boxBackground.getBlue();
				if (luminance > 128) {
					g2.setColor(Color.GRAY);
				} else {
					g2.setColor(Color.LIGHT_GRAY);
				}
				
				// Draw the grid on mouseover
				g2.setStroke(dashedStroke);
				for(Integer vSnap : b.getVSnaps()) {
					g2.drawLine(b.getStartX(), vSnap, b.getStartX()+b.width(), vSnap);
				}
				for(Integer hSnap : b.getHSnaps()) {
					g2.drawLine(hSnap, b.getStartY(), hSnap, b.getStartY()+b.height());
				}

				g2.setStroke(solidStroke);
				g2.setColor(Color.red);
			} else {
				g2.setColor(Color.black);
			}

			// Draw Box foreground
			g2.drawRect(b.getStartX(), b.getStartY(), b.width(), b.height());
		}


		// If the user is drawing a rectangle, draw it with a dashed stroke
		if (isDragging) {
			// Draw Box Background
			g2.setStroke(solidStroke);
			g2.setColor(OptionsToolBar.getBoxBackgroundColor());
			g2.fillRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(startX - endX), Math.abs(startY - endY));

			//Draw Box foreground
			g2.setColor(Color.black);
			g2.setStroke(dashedStroke);
			g2.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(startX - endX), Math.abs(startY - endY));
		}
	}
}