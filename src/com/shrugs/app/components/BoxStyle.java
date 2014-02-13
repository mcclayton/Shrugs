package com.shrugs.app.components;

import java.util.*;
import java.util.Map.Entry;
import java.awt.Color;

//some stuff is unsafe, should add exceptions when necessary

public class BoxStyle implements Comparable<BoxStyle>{
	private Map<String, String> colors;
	private Map<String, String> attr;
	public BoxStyle(){
		attr = new HashMap<String,String>();
		colors = new HashMap<String,String>();
		colors.put("blue", "color:rgb(0,0,255)");
		colors.put("red", "color:rgb(255,0,0)");
		colors.put("green", "color:rgb(0,255,0)");
		colors.put("black", "color:rgb(0,0,0)");
		colors.put("white", "color:rgb(255,255,255)");
		colors.put("yellow", "color:rgb(255,255,0)");
		colors.put("gray", "color:rgb(192,192,192)");
		colors.put("magenta", "color:rgb(255,0,255)");
		colors.put("cyan", "color:rgb(0,255,255)");
	}
	
	public String setBoxColorPreset(String color) throws ColorNotFoundException{
		if(!colors.get(color).isEmpty()){
			return colors.get(color);
		}
		else{
			throw new ColorNotFoundException("Color is not in the presets!");
		}
	}
	
	public void setAttribute(String name, String value){
		attr.put(name, value);
	}
	
	public String getAttribute(String name) throws AttributeNotFoundException{
		String c = attr.get(name);
		if(c.isEmpty()){
			throw new AttributeNotFoundException("Attribute not found!");
		}
		else{
			return c;
		}
	}
	
	public void setBoxColor(Color c){
		this.attr.put("color", "rgb("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")");
		return;
	}
	
	public void setBoxColorViaHex(int r, int g, int b){
		this.attr.put("color", "rgb("+r+","+g+","+b+")");
	}
	
	public Set<Entry<String, String>> getColorList(){
		return colors.entrySet();
	}
	public String getColor(){
		return this.attr.get("color");
	}

	@Override
	public int compareTo(BoxStyle arg0) {
		return (this.attr.equals(arg0)) ? 1 : 0;
	}
}