package com.shrugs.app.components;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;


//some stuff is unsafe, should add exceptions when necessary

public class BoxStyle implements Comparable<BoxStyle>{
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
	
	public void setBoxColor(Color c){
		this.attr.put("color", "rgb("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")");
		return;
	}
	
	public void setBoxColorViaHex(int r, int g, int b){
		this.attr.put("color", "rgb("+r+","+g+","+b+")");
	}

	public String getColor(){
		return this.attr.get("color");
	}

	@Override
	public int compareTo(BoxStyle o) {
		return (this.attr.equals(o.attr)) ? 1 : 0;
	}

}