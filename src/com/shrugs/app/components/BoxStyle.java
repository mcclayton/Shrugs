package com.shrugs.app.components;

import java.awt.Color;
import java.util.HashMap;

public class BoxStyle implements Comparable<BoxStyle> {
	private HashMap<String, String> style;
	
	public BoxStyle() {
		style = new HashMap<String, String>();
	}
	
	public String attr(String name) {
		return style.get(name);
	}
	
	public void attr(String name, String value) {
		style.put(name, value);
	}
	
	public String color() {
		return style.get("color");
	}
	
	public void color(Color c) {
		style.put("color", "rgb("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")");
	}
	
	public void color(int r, int g, int b) {
		style.put("color", "rgb("+r+","+g+","+b+")");
	}

	@Override
	public int compareTo(BoxStyle o) {
		return (this.style.equals(o.style)) ? 1 : 0;
	}
	
}