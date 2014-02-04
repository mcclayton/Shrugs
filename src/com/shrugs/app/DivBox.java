package com.shrugs.app;

import java.util.LinkedList;

public class DivBox extends Box {

	private LinkedList<Box> children;
	public DivBox(int startX, int startY, int endX, int endY) {
		super(startX, startY, endX, endY);
		children = new LinkedList<Box>();
	}
	
	public LinkedList<Box> getChildren() {
		return children;
	}

}
