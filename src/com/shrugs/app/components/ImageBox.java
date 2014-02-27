package com.shrugs.app.components;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class ImageBox extends Box {

	@Expose
	private Image boxImage = null;
	private String boxImageFilePath = null;

	public ImageBox(String imageFilePath, int startX, int startY, int endX, int endY) {
		super(startX, startY, endX, endY);
		this.boxImageFilePath = imageFilePath;
		try {
			this.boxImage = ImageIO.read(new File(imageFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		return "div";
	}

	public Image getImage() {
		return this.boxImage;
	}
	
	public String getImageFilePath() {
		return this.boxImageFilePath;
	}
	
	@Override
	public JsonObject toJsonObj() {
		JsonObject obj = super.toJsonObj();
		obj.addProperty("img", "michael.png"); //TODO: get image name
		
		return obj;
	}
}
