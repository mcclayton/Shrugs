package com.shrugs.app.components;

import java.util.LinkedList;



public class Box {
	protected int startX;	// Coordinates of box that is currently being drawn
	protected int startY;
	protected int endX;
	protected int endY = 0;
	protected Box parent;
    protected boolean highlight;
	
	public Box(int startX, int startY, int endX, int endY) {
		this(startX, startY, endX, endY, null);
	}
	
    public Box(int startX, int startY, int endX, int endY, Box parent) {
    	this.startX = startX;
    	this.startY = startY;
    	this.endX = endX;
    	this.endY = endY;
    	this.parent = parent;
    	this.highlight = false;
    }
	
	public LinkedList<Box> getChildren() {
		return null;
	}
    
    public int getStartX() {return this.startX;}
    public int getStartY() {return this.startY;}
    public int getEndX() {return this.endX;}
    public int getEndY() {return this.endY;}
    public Box getParent() {return this.parent;}
    public boolean gethighlight() {return this.highlight;}

    public void setStartX(int startX) {this.startX = startX;}
    public void setStartY(int startY) {this.startY = startY;}
    public void setEndX(int endX) {this.endX = endX;}
    public void setEndY(int endY) {this.endY = endY;}
    public void setParent(Box parent) {this.parent = parent;}
    public void setHighlight(boolean toggle) {this.highlight = toggle;} 
    
    
    public int getXOffset() {
    	return startX-parent.getStartX();
    }
    
    public int getYOffset() {
    	return startY-parent.getStartX();
    }
    
    public boolean coordinatesInsideBox(int x, int y) {
    	if(x >= startX && x <= (startX+endX) && y >= startY && y <= (startY+endY))
    		return true;
    	else
    		return false;
    }

}