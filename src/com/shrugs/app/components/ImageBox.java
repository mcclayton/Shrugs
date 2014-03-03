package com.shrugs.app.components;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.gson.JsonObject;

public class ImageBox extends Box {

	public static HashMap<String, Image> ImageList = null;
	private Image boxImage = null;
	private String boxImageFilePath = null;

	public ImageBox(String imageFilePath, int startX, int startY, int endX,
			int endY) {
		super(startX, startY, endX, endY);

		if (ImageList == null)
			ImageList = new HashMap<String, Image>();

		this.boxImageFilePath = imageFilePath.replace('\\', '/'); //make Windows paths consistent
		
		if (ImageList.containsKey(boxImageFilePath))
			this.boxImage = ImageList.get(boxImageFilePath);
		else {
			try {
				this.boxImage = ImageIO.read(new File(boxImageFilePath));
				ImageList.put(boxImageFilePath, boxImage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.tagName = "img";
	}

	public ImageBox() {
		super();
		this.boxImageFilePath = null;
		this.boxImage = null;
	}

	public String toString() {
		String[] imagearr = boxImageFilePath.split("/");
		String name = imagearr[imagearr.length - 1];
		return "<img src=\"img/" + name + "\" style=\"position:absolute;left:"
				+ getXOffset() + "px;top:" + getYOffset() + "px;width:"
				+ width() + "px;height:" + height() + "px;" + style + "\">";
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
		obj.addProperty("img", boxImageFilePath);

		return obj;
	}

	@Override
	public void fromJsonObj(JsonObject obj) {
		super.fromJsonObj(obj);
		this.boxImageFilePath = obj.get("img").getAsString();
		try {
			this.boxImage = ImageIO.read(new File(boxImageFilePath));
		} catch (IOException e) {
			System.err.println("Image " + boxImageFilePath + " not found.");
			e.printStackTrace();
		}
	}
}
