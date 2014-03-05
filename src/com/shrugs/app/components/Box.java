package com.shrugs.app.components;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.shrugs.app.gui.OptionsToolBar;

public class Box {
	protected int startX;
	protected int startY;
	protected int endX;
	protected int endY = 0;
	protected Box parent;
	protected boolean highlight;
	protected BoxStyle style;
	protected String tagName = "div";
	protected String boxName;

	public Box() {
		this(0, 0, 0, 0, null);
	}

	public Box(int startX, int startY, int endX, int endY) {
		this(startX, startY, endX, endY, null);
	}

	public Box(int startX, int startY, int endX, int endY, Box parent) {
		this.startX = Math.min(startX, endX);
		this.startY = Math.min(startY, endY);
		this.endX = Math.max(startX, endX);
		this.endY = Math.max(startY, endY);
		this.parent = parent;
		this.highlight = false;
		this.style = new BoxStyle();
		this.boxName = this.tagName.toString() + " Box {" + this.startX + "," + this.startY + "}"; 
	}

	public LinkedList<Box> getChildren() {
		return null;
	}

	public int getStartX() {
		return this.startX;
	}

	public int getStartY() {
		return this.startY;
	}

	public int getEndX() {
		return this.endX;
	}

	public int getEndY() {
		return this.endY;
	}
	
	public String getBoxName() {
		return this.boxName;
	}

	public Box getParent() {
		return this.parent;
	}

	public boolean gethighlight() {
		return this.highlight;
	}

	public BoxStyle getStyle() {
		return this.style;
	}

	public int width() {
		return this.endX - this.startX;
	}

	public int height() {
		return this.endY - this.startY;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}
	
	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}

	public void setParent(Box parent) {
		this.parent = parent;
	}

	public void setHighlight(boolean toggle) {
		this.highlight = toggle;
	}

	public void setStyle(BoxStyle style) {
		this.style = style;
	}

	public int getXOffset() {
		return startX - parent.getStartX();
	}

	public int getYOffset() {
		return startY - parent.getStartY();
	}

    /**
     * Builds a string based on existing parametes of the box object
     * The String differs depending on the type of box it is
     *
     * @param
     *             
     * @return
     *             A string contating all the information of the box
     */
	public String boxDetailsString() {
		String details;		
		
		details = 	"Name: " + this.boxName + "\n" +
				"Type: " + this.tagName + "\n" +
				"Top Left Box Point: " + "{" + this.startX + "," + this.startY + "}\n";
		
		if (this.tagName=="div")
			details += "Color: " + this.style.getBoxColor();
		else if (this.tagName=="txt") {
			TextAreaBox txt = (TextAreaBox) this;
			details += "Text: " + txt.getText();
		}else if (this.tagName=="img"){
			ImageBox img = (ImageBox) this;
			details += "Image: " + img.getImageFilePath();
		}
		
		return details;		
	}
	
	public LinkedList<Integer> getHSnaps() {
		LinkedList<Integer> snaps = new LinkedList<Integer>();
		int width = this.width();
		int limit = 50;
		float f;
		for (f = 1f; f * width > limit && f > .1f; f *= .5f)
			;
		for (float i = 0; i < 1; i += f)
			snaps.add(this.startX + (int) (i * width));
		snaps.add(this.startX + width + 1);
		return snaps;
	}

	public LinkedList<Integer> getVSnaps() {
		LinkedList<Integer> snaps = new LinkedList<Integer>();
		int height = this.height();
		int limit = 50;
		float f;
		for (f = 1f; f * height > limit && f > .1f; f *= .5f)
			;
		for (float i = 0; i < 1; i += f)
			snaps.add(this.startY + (int) (i * height));
		snaps.add(this.startY + height + 1);
		return snaps;
	}

	/**
	 * Finds the nearestHSnap to the given x coordinate.
	 * 
	 * @param xCoord The X coordinate to find the nearest hSnap to.
	 * 
	 * @return The nearest hSnap.
	 */
	public Integer getNearestHSnap(int xCoord) {
		int minDistance = Integer.MAX_VALUE;
		int closestHSnap = 0;
		int distance;

		for (Integer hSnap : this.getHSnaps()) {
			distance = Math.max(hSnap, xCoord) - Math.min(hSnap, xCoord);
			if (distance < minDistance) {
				minDistance = distance;
				closestHSnap = hSnap.intValue();
			}
		}
		return closestHSnap;
	}

	/**
	 * Finds the nearestVSnap to the given y coordinate.
	 * 
	 * @param yCoord The Y coordinate to find the nearest vSnap to.
	 * 
	 * @return The nearest vSnap.
	 */
	public Integer getNearestVSnap(int yCoord) {
		int minDistance = Integer.MAX_VALUE;
		int closestVSnap = 0;
		int distance;

		for (Integer vSnap : this.getVSnaps()) {
			distance = Math.max(vSnap, yCoord) - Math.min(vSnap, yCoord);
			if (distance < minDistance) {
				minDistance = distance;
				closestVSnap = vSnap.intValue();
			}
		}
		return closestVSnap;
	}

    /**
     * This method indicates where in corilation to the box a coordinate is
     *   2
     * 3 _ 1
     * 4|_|8
     * 5 6 7
     *
     * @param
     *             x: the x coordinate of the point to check
     *            y: the y coordinate of the point to check
     * @return
     *             Returns an int to indicate relative location based on the diagram above
     */
	public int locationRelativeToBox(int x, int y) {
		if (x >= (endX) && y <= startY)
			return 1;
		else if (x <= endX && x >= startX && y <= startY)
			return 2;
		else if (x <= startX && y <= startY)
			return 3;
		else if (x <= startX && y >= startY && y <= endY)
			return 4;
		else if (x <= startX && y >= endY)
			return 5;
		else if (x <= endX && x >= startX && y >= endY)
			return 6;
		else if (x >= endX && y >= endY)
			return 7;
		else if (x >= endX && y >= startY && y <= endY)
			return 8;
		else
			return 0;
	}

    /**
     * When a box is double clicked, will show a diologe box for details of the box
     * Depending on the type of box the diologe will differ
     *
     * @param
     *        
     * @return
     */
	public void showAttributesMenu() {
		Object[] options = {"Delete","Recolor", "Rename" ,"Cancel"};
		String details = this.boxDetailsString();
							 
		if (this.tagName=="txt")
			options[1] = "Different Text";
		else if (this.tagName=="img")
			options[1] = "Different Image";	
		
		int n = JOptionPane.showOptionDialog(null,
			details,					
			this.boxName,				//pane label
			JOptionPane.YES_NO_OPTION,		//default dialoge for buttons
			JOptionPane.QUESTION_MESSAGE,	//type of box object
			null,     						//do not use a custom Icon
			options,  						//the titles of buttons
			options[0]); 					//default button title
		
		switch (n) {
			case 0:	//Delete
					((DivBox) this.parent).removeChild(this);
					break;
			case 1:	//Change Content of box
					changeBoxContent();
					break;
			case 2:	//Rename
					String newName = (String)JOptionPane.showInputDialog(
					                    null,
					                    "New Box Name",
					                    "New Box Name Dialog ",
					                    JOptionPane.PLAIN_MESSAGE,
					                    null,
					                    null,
					                    "Type a new name here");
					this.setBoxName(newName);
					break;
			case 3: //Cancel
					break;
		}
	}

	
	public void changeBoxContent() {
		if (this.tagName=="div") {
			//Change the color
			Color newColor = null;
			newColor = JColorChooser.showDialog(null, "Choose Location Color", Color.white);
			this.style.setBoxColor(newColor);
		} else if (this.tagName=="txt") {
			//Change Text
			String newText = (String)JOptionPane.showInputDialog(
                    null,
                    "Enter the new text you would like to be displayed below",
                    "New Text Dialog ",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "Type new Text here");
			if (newText != null) {
				TextAreaBox txt = (TextAreaBox) this;
				txt.setText(newText);			
			}
		} else if (this.tagName=="img"){
			// Change Box Content
			JFileChooser imageFileChooser = new JFileChooser();
			imageFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
			try {
				//Set the chooser directory to be the current directory
				imageFileChooser.setCurrentDirectory(new File(new File(".").getCanonicalPath()));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			int returnVal = imageFileChooser.showDialog(null, "Open");

			if (returnVal == JFileChooser.APPROVE_OPTION) 
			{
				ImageBox img = (ImageBox) this;
				img.setImage(imageFileChooser.getSelectedFile().getAbsolutePath());
			}				
		}		
	}
	
	public boolean containsPoint(int x, int y) {
		return !(this.startX > x || this.endX < x || this.startY > y || this.endY < y);
	}

	/**
	 * Checks if the box overlaps with another box
	 * 
	 * @param b
	 *            the box to check against
	 * @return true if the boxes overlap; false otherwise
	 */
	public boolean collidesWith(Box b) {
		return !(this.startX >= b.endX || this.endX <= b.startX
				|| this.startY >= b.endY || this.endY <= b.startY);
	}

	public JsonObject toJsonObj() {
		JsonObject obj = new JsonObject();
		obj.addProperty("type", getClass().getName());
		obj.addProperty("x1", startX);
		obj.addProperty("x2", endX);
		obj.addProperty("y1", startY);
		obj.addProperty("y2", endY);
		obj.add("style", style.toJsonObj());
		return obj;
	}

	public void fromJsonObj(JsonObject obj) {
		this.startX = obj.get("x1").getAsInt();
		this.endX = obj.get("x2").getAsInt();
		this.startY = obj.get("y1").getAsInt();
		this.endY = obj.get("y2").getAsInt();
		this.style = new BoxStyle();
		this.style.fromJsonObj(obj.get("style").getAsJsonObject());
	}
	
	public String toString() {
		return "<span></span>";
	}	

}