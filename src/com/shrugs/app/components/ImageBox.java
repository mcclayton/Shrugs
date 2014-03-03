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
		this.tagName = "img";
	}
	
	public ImageBox() {
		super();
		this.boxImageFilePath = null;
		this.boxImage = null;
	}

	public String toString() {
		return "<img src=\""+boxImageFilePath+"\" style=\"position:absolute;left:"+getXOffset()+"px;top:"+getYOffset()+"px;width:"+width()+"px;height:"+height()+"px;"+style+"\">";
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
		obj.addProperty("img", boxImageFilePath); //TODO: get image name
		
		return obj;
	}
	
	@Override
	public void fromJsonObj(JsonObject obj) {
		super.fromJsonObj(obj);
		this.boxImageFilePath = obj.get("img").getAsString();
		try {
			this.boxImage = ImageIO.read(new File(boxImageFilePath));
		} catch (IOException e) {
			System.err.println("Image " + boxImageFilePath +" not found.");
			e.printStackTrace();
		}
	}
}
