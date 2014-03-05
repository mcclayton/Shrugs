package com.shrugs.app.components;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
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
		this.style.makeBackgroundTransparent();

		if (ImageList == null)
			ImageList = new HashMap<String, Image>();

		this.tagName = "img";

		setImage(imageFilePath);
	}

	public void setImage(String path) {
		this.boxImageFilePath = path.replace('\\', '/');

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
	}

	public ImageBox() {
		super();
		this.boxImageFilePath = null;
		this.boxImage = null;
		if (ImageList == null)
			ImageList = new HashMap<String, Image>();
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
		setImage(obj.get("img").getAsString());
	}
}
