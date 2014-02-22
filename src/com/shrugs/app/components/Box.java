package com.shrugs.app.components;

import java.util.LinkedList;

public class Box {
	protected int startX;	// Coordinates of box that is currently being drawn
	protected int startY;
	protected int endX;
	protected int endY = 0;
	protected Box parent;
    protected boolean highlight;
    protected BoxStyle style;
    
    protected static float[] snapRatios = { 0, .25f, .33f, .50f, .67f, .75f, 1 };
    
	
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
    	this.style = new BoxStyle();
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
    public BoxStyle getStyle() {return this.style;}
    public int width() {return this.endX - this.startX;}
    public int height() {return this.endY - this.startY;}
    public void setStartX(int startX) {this.startX = startX;}
    public void setStartY(int startY) {this.startY = startY;}
    public void setEndX(int endX) {this.endX = endX;}
    public void setEndY(int endY) {this.endY = endY;}
    public void setParent(Box parent) {this.parent = parent;}
    public void setHighlight(boolean toggle) {this.highlight = toggle;} 
    public void setStyle(BoxStyle style) {this.style = style;}
    
    
    public int getXOffset() {
    	return startX-parent.getStartX();
    }
    
    public int getYOffset() {
    	return startY-parent.getStartX();
    }
    
    public LinkedList<Integer> getHSnaps() {
    	LinkedList<Integer> snaps = new LinkedList<Integer>();
    	int width = this.width();
    	for(float f : snapRatios)
    		snaps.add((int)(f*width)+this.startX);
    	return snaps;
    }
    
    public LinkedList<Integer> getVSnaps() {
    	LinkedList<Integer> snaps = new LinkedList<Integer>();
    	int height = this.height();
    	for(float f : snapRatios)
    		snaps.add((int)(f*height)+this.startY);
    	return snaps;
    }
    
    /* Finds the nearestHSnap to the given x coordinate.
     * 
     * @param  xCoord  The X coordinate to find the nearest hSnap to.
     * @return         The nearest hSnap.
     */
    public Integer getNearestHSnap(int xCoord) {
		int minDistance = Integer.MAX_VALUE;
		int closestHSnap = 0;
    	int distance;
    	
    	for(Integer hSnap : this.getHSnaps()) {
			distance = Math.max(hSnap, xCoord) - Math.min(hSnap, xCoord);
			if (distance < minDistance) {
				minDistance = distance;
				closestHSnap = hSnap.intValue();
			}
		}
    	return closestHSnap;
    }
    
    /* Finds the nearestVSnap to the given y coordinate.
     * 
     * @param  yCoord  The Y coordinate to find the nearest vSnap to.
     * @return         The nearest vSnap.
     */
    public Integer getNearestVSnap(int yCoord) {
		int minDistance = Integer.MAX_VALUE;
		int closestVSnap = 0;
    	int distance;
    	
    	for(Integer vSnap : this.getVSnaps()) {
			distance = Math.max(vSnap, yCoord) - Math.min(vSnap, yCoord);
			if (distance < minDistance) {
				minDistance = distance;
				closestVSnap = vSnap.intValue();
			}
		}
    	return closestVSnap;
    }
    
    public boolean coordinatesInsideBox(int x, int y) {
    	if(x >= startX && x <= (startX+this.width()) && y >= startY && y <= (startY+this.height()))
    		return true;
    	else
    		return false;
    }
    
    public boolean lineIntersectsBox(int x1, int y1, int x2, int y2){
    	//(x1, y1) (x2, y2)
    	int rl1, rl2;	//coordinate loaction relative to the box; 0 inside, 1 top right, 2 top middle, 3 top left,
    					//4 left middle, 5 bottom left, 6 bottom middle, 7 bottom middle, 8 middle right
    					//rl1 = relative location 1
    	rl1 = locationRelativeToBox(x1, y1);
    	rl2 = locationRelativeToBox(x2, y2);
    	// debugging System.out.println("(" + rl1 + ":" + rl2 + ")");
    	if (   (rl1==1 && rl2==5 || rl1==5 && rl2==1)
    		|| (rl1==2 && rl2==6 || rl1==6 && rl2==2)
    		|| (rl1==3 && rl2==7 || rl1==7 && rl2==3)
    		|| (rl1==4 && rl2==8 || rl1==4 && rl2==8))
    		return true;
    	else
    		return false;
    }

    public int locationRelativeToBox(int x, int y) {    	
    	if (x >= (startX + endX) && y <= startY)
    		return 1;
    	else if (x <= (startX + endX) && x >= startX && y <= startY)
    		return 2;
    	else if (x <= startX && y <= startY)
    		return 3;
    	else if (x <= startX && y >= startY && y <= (startY+endY))
    		return 4;
    	else if (x <= startX && y >= (startY+endY))
    		return 5;
    	else if (x <= (startX + endX) && x >= startX && y >= (startY+endY))
    		return 6;
    	else if (x >= (startX + endX) && y >= (startY+endY))
    		return 7;
    	else if (x >= (startX + endX) && y >= startY && y <= (startY+endY))
    		return 8;
    	else
    		return 0;
    }

}