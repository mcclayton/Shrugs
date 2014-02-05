package com.shrugs.app.components;

import java.util.LinkedList;




public class DivBox extends Box {

	protected LinkedList<Box> children;
	public DivBox(int startX, int startY, int endX, int endY) {
		super(startX, startY, endX, endY);
		children = new LinkedList<Box>();
	}
	
	public LinkedList<Box> getChildren() {
		return children;
	}
	
	public String toString() {
		return "div";
	}
	
	public void addChild(Box box) {
		children.add(box);
		box.setParent(this);
	}
	
	public void removeChild(Box box) {
		children.remove(box);
		box.setParent(null);
	}

}
