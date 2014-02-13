package com.shrugs.app.components;

import java.util.*;
import java.util.Map.Entry;
import java.awt.Color;

public class BoxStyle{
	private Map<String, String> colors;
	public BoxStyle(){
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
	
	public String setBoxColorViaHex(int r, int g, int b){
		Color inputColor = new Color(r,g,b);
		return inputColor.toString();
	}
	
	public Set<Entry<String, String>> getColorList(){
		return colors.entrySet();
	}
}