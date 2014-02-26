package com.shrugs.app.components;

import java.awt.Image;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class ImageBox extends Box {

	@Expose
	private Image boxImage = null;

	public ImageBox(Image image, int startX, int startY, int endX, int endY) {
		super(startX, startY, endX, endY);
		this.boxImage = image;
	}

	public String toString() {
		return "div";
	}

	public Image getImage() {
		return this.boxImage;
	}
	
	@Override
	public JsonObject toJsonObj() {
		JsonObject obj = super.toJsonObj();
		obj.addProperty("img", "michael.png"); //TODO: get image name
		
		return obj;
	}
}
