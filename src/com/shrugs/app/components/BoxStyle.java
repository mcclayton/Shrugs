package com.shrugs.app.components;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;


//some stuff is unsafe, should add exceptions when necessary

public class BoxStyle implements Comparable<BoxStyle>{
	
	public static void main(String[] args) {
		BoxStyle style = new BoxStyle();
		style.setBoxColor(Color.cyan);
		style.setTextColor(Color.white);
		style.setAttribute("border", "1px solid #000");
		System.out.println(style.toString());
	}
	
	private Map<String, String> attr;
	public BoxStyle(){
		attr = new HashMap<String,String>();
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
	
	public void setTextColor(Color c){
		this.attr.put("color", "rgb("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")");
		return;
	}
	
	public void setTextColor(int r, int g, int b){
		this.attr.put("color", "rgb("+r+","+g+","+b+")");
	}

	public String getTextColor(){
		return this.attr.get("color");
	}
	
	public void setBoxColor(Color c){
		this.attr.put("background-color", "rgb("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")");
		return;
	}
	
	public void setBoxColor(int r, int g, int b){
		this.attr.put("background-color", "rgb("+r+","+g+","+b+")");
	}

	public String getBoxColor(){
		return this.attr.get("background-color");
	}

	@Override
	public int compareTo(BoxStyle o) {
		return (this.attr.equals(o.attr)) ? 1 : 0;
	}
	
	public String toString() {
		String out = "";
		for( String key : attr.keySet()) {
			out+=key+":"+attr.get(key)+";";
		}
		return out;
	}
	
}