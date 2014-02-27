package com.shrugs.app.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.shrugs.app.components.BodyBox;
import com.shrugs.app.components.Box;
import com.shrugs.app.components.DivBox;
import com.shrugs.app.components.ImageBox;
import com.shrugs.app.components.TextAreaBox;

public class DrawableView extends JPanel implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	final static float dash[] = { 5.0f };
	final static BasicStroke dashedStroke = new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
	final static BasicStroke solidStroke = new BasicStroke();

	private int startX, startY, endX, endY = 0; // Coordinates of box that is
	// currently being drawn
	boolean isDragging = false; // Used to tell if the user is currently
	// drawing/dragging a box
	boolean tlcord, trcord, blcord, brcord; // Used to tell if the coordinates
	// of the object are inside another
	// object
	boolean make; // Used to tell if the object should be made or not

	public static BodyBox bodyBox; // The main body box of the web page
	private Box targetBox; // The box to draw new boxes into (set on
	// mousePressed)

	public DrawableView(int width, int height) {
		bodyBox = new BodyBox(0, 0, width - 1, height - 1);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				startX = evt.getX();
				startY = evt.getY();

				targetBox = bodyBox.youngestBoxContainingPoint(startX, startY);

				startX = targetBox.getNearestHSnap(startX);
				startY = targetBox.getNearestVSnap(startY);

				endX = startX;
				endY = startY;
				isDragging = true;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent evt) {
				if (!isDragging) { // REQ1: Mouse must have been dragged
					repaint();
					return;
				}

				if (!(targetBox instanceof DivBox)) { // REQ2: target must be
					// DivBox
					isDragging = false;
					repaint();
					return;
				}

				isDragging = false;
				make = true;

				endX = targetBox.getNearestHSnap(evt.getX());
				endY = targetBox.getNearestVSnap(evt.getY());

				if ((endX == startX) || (endY == startY)) { // REQ3: Box must
					// not be 0x0
					repaint();
					return;
				}

				// swap endX/startX if necessary
				if (endX < startX) {
					int swap = endX;
					endX = startX;
					startX = swap;
				}

				// swap endY/startY if necessary
				if (endY < startY) {
					int swap = endY;
					endY = startY;
					startY = swap;
				}

				// Reduce endX/endY to prevent collision errors
				endX--;
				endY--;

				if (bodyBox.youngestBoxContainingPoint(endX, endY) != targetBox) { // REQ4:
					// Both
					// primary
					// corners
					// must
					// be
					// in
					// the
					// same
					// box
					repaint();
					return;
				}

				// Allow box type to be chosen using toolbox
				if (OptionsToolBar.getBoxMode().equals("Div")) {
					DivBox newBox = new DivBox(startX, startY, endX, endY);

					if (((DivBox) targetBox).childrenCollideWith(newBox)) { // REQ5:
						// new
						// box
						// must
						// not
						// overlap
						// with
						// pre-existing
						// children
						repaint();
						return;
					}
					newBox.getStyle().setBoxColor(
							OptionsToolBar.getBoxBackgroundColor());
					((DivBox) targetBox).addChild(newBox);
				} else if (OptionsToolBar.getBoxMode().equals("Image")) {
					ImageBox newBox;
					newBox = new ImageBox(OptionsToolBar.getBoxImagePath(), startX, startY, endX, endY);

					if (((DivBox) targetBox).childrenCollideWith(newBox)) { // REQ5:
						// new
						// box
						// must
						// not
						// overlap
						// with
						// pre-existing
						// children
						repaint();
						return;
					}
					((DivBox) targetBox).addChild(newBox);
				} else if (OptionsToolBar.getBoxMode().equals("Text")) {
					TextAreaBox newBox;
					newBox = new TextAreaBox(OptionsToolBar.getBoxText(), startX, startY, endX, endY);

					if (((DivBox) targetBox).childrenCollideWith(newBox)) { // REQ5:
						// new
						// box
						// must
						// not
						// overlap
						// with
						// pre-existing
						// children
						repaint();
						return;
					}
					((DivBox) targetBox).addChild(newBox);
				}
			}

			@Override
			public void mouseClicked(MouseEvent evt) {

				if (evt.getClickCount() == 2) {
					startX = evt.getX();
					startY = evt.getY();

					bodyBox.youngestBoxContainingPoint(startX, startY)
					.showAttributesMenu();
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
		if (bodyBox.containsPoint(endX, endY) && bodyBox.gethighlight()) {
			endX = bodyBox.getNearestHSnap(endX);
			endY = bodyBox.getNearestVSnap(endY);
		}

		// Snap the end coordinates to a box grid if necessary
		for (Box b : bodyBox.flatten()) {
			if (b.containsPoint(endX, endY) && b.gethighlight()) {
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
		for (Box b : bodyBox.flatten()) {
			if (b.containsPoint(mvEvt.getX(), mvEvt.getY())) {
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
			for (Integer vSnap : bodyBox.getVSnaps()) {
				g2.drawLine(bodyBox.getStartX(), vSnap, bodyBox.getStartX()
						+ bodyBox.width(), vSnap);
			}
			for (Integer hSnap : bodyBox.getHSnaps()) {
				g2.drawLine(hSnap, bodyBox.getStartY(), hSnap,
						bodyBox.getStartY() + bodyBox.height());
			}
		}

		// Draw all the box objects based on the values in the box object
		Color boxBackground;
		for (Box b : bodyBox.flatten()) {

			if (b instanceof ImageBox) {	// Case 1: Box is ImageBox
				// Draw the image
				g2.drawImage(((ImageBox) b).getImage(), b.getStartX(), b.getStartY(), b.width(), b.height(), null);
			} else if (b instanceof TextAreaBox) {	// Case 2: Box is TextAreaBox
				g2.setColor(Color.black);
				g2.setStroke(solidStroke);
				// Draw Box foreground (border)
				g2.drawRect(b.getStartX(), b.getStartY(), b.width(), b.height());
				
				// Draw the text area
				((TextAreaBox) b).drawString(g2, ((TextAreaBox) b).getText(), b.getStartX(), b.getStartY(), b.width(), b.height());							
			} else if (b instanceof DivBox) {	// Case 3: Box is DivBox
				// Draw Box Background
				g2.setStroke(solidStroke);
				boxBackground = b.getStyle().getBoxColorValue();
				g2.setColor(boxBackground);
				g2.fillRect(b.getStartX(), b.getStartY(), b.width(), b.height());

				if (b.gethighlight()) {

					// Make sure the grid is a color that won't blend in with the
					// boxes background color
					double luminance = 0.2126 * boxBackground.getRed() + 0.7152
							* boxBackground.getGreen() + 0.0722
							* boxBackground.getBlue();
					if (luminance > 128) {
						g2.setColor(Color.GRAY);
					} else {
						g2.setColor(Color.LIGHT_GRAY);
					}

					// Draw the grid on mouseover
					g2.setStroke(dashedStroke);
					for (Integer vSnap : b.getVSnaps()) {
						g2.drawLine(b.getStartX(), vSnap,
								b.getStartX() + b.width(), vSnap);
					}
					for (Integer hSnap : b.getHSnaps()) {
						g2.drawLine(hSnap, b.getStartY(), hSnap,
								b.getStartY() + b.height());
					}

					g2.setStroke(solidStroke);
					g2.setColor(Color.red);
				} else {
					g2.setColor(Color.black);
				}

				// Draw Box foreground
				g2.drawRect(b.getStartX(), b.getStartY(), b.width(), b.height());
			}
		}

		// If the user is drawing a rectangle, draw it with a dashed stroke
		if (isDragging) {

			g2.setStroke(solidStroke);
			if (OptionsToolBar.getBoxMode().equals("Image")) {
				// Draw the image
				Image image;
				try {
					image = ImageIO.read(new File(OptionsToolBar.getBoxImagePath()));
					g2.drawImage(image, Math.min(startX, endX), Math.min(startY, endY),
							Math.abs(startX - endX), Math.abs(startY - endY), null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (OptionsToolBar.getBoxMode().equals("Text")) {
				g2.setStroke(solidStroke);
				g2.setColor(Color.black);
				TextAreaBox.drawString(g2, OptionsToolBar.getBoxText(), Math.min(startX, endX), Math.min(startY, endY),
						Math.abs(startX - endX), Math.abs(startY - endY));
			} else if (OptionsToolBar.getBoxMode().equals("Div")) {
				// Drawn Box Background
				g2.setStroke(solidStroke);
				g2.setColor(OptionsToolBar.getBoxBackgroundColor());
				g2.fillRect(Math.min(startX, endX), Math.min(startY, endY),
						Math.abs(startX - endX), Math.abs(startY - endY));
			}

			// Draw Box foreground
			g2.setColor(Color.black);
			g2.setStroke(dashedStroke);
			g2.drawRect(Math.min(startX, endX), Math.min(startY, endY),
					Math.abs(startX - endX), Math.abs(startY - endY));
		}
	}

	public static void Load(BodyBox b) {
		bodyBox = b;
		//TODO repaint
	}
}