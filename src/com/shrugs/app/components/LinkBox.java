package com.shrugs.app.components;

import java.awt.Color;

import com.google.gson.JsonObject;

public class LinkBox extends TextAreaBox {

	private String link = "#";

	public LinkBox(String text, String link, Color textColor, int textSize,
			int startX, int startY, int endX, int endY) {
		super(text, textColor, textSize, startX, startY, endX, endY);
		this.link = link;
	}

	public LinkBox() {
		this("", "#", Color.BLACK, 12, 0, 0, 0, 0);
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public JsonObject toJsonObj() {
		JsonObject obj = super.toJsonObj();
		obj.addProperty("link", link);
		return obj;
	}

	public void fromJsonObj(JsonObject obj) {
		super.fromJsonObj(obj);
		setLink(obj.get("link").getAsString());
	}

	public String toString() {
		String str = "<a href=\"" + link
				+ "\" style=\"display:block;position:absolute;left:"
				+ getXOffset() + "px;top:" + getYOffset() + "px;width:"
				+ (width() + 1) + "px;height:" + (height() + 1)
				+ "px;color:rgba(" + textColor.getRed() + ","
				+ textColor.getGreen() + "," + textColor.getBlue() + ","
				+ textColor.getAlpha() + ");font-family:sans-serif;font-size:"
				+ textSize + "px;" + style + "\">";
		str += text;
		str += "</a>";
		return str;
	}
}
