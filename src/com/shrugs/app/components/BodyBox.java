package com.shrugs.app.components;




public class BodyBox extends DivBox {

	public BodyBox(int startX, int startY, int endX, int endY) {
		super(startX, startY, endX, endY);
	}
	
	public int getXOffset() {
		return startX;
	}
	
	public int getYOffset() {
		return startY;
	}

}
