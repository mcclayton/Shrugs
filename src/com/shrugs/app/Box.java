package com.shrugs.app;



public class Box {
	private int startX, startY, endX, endY = 0;	// Coordinates of box that is currently being drawn
	
    public Box(int startX, int startY, int endX, int endY) {
    	this.startX = startX;
    	this.startY = startY;
    	this.endX = endX;
    	this.endY = endY;
    }
    
    public int getStartX() {return this.startX;}
    public int getStartY() {return this.startY;}
    public int getEndX() {return this.endX;}
    public int getEndY() {return this.endY;}
    
    public void setStartX(int startX) {this.startX = startX;}
    public void setStartY(int startY) {this.startY = startY;}
    public void setEndX(int endX) {this.endX = endX;}
    public void setEndY(int endY) {this.endY = endY;}
}