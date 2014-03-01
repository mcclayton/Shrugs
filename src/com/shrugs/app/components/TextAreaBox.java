package com.shrugs.app.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class TextAreaBox extends Box {

	@Expose
	private String text = "Enter text...";
	private Color textColor = Color.black; //TODO: move this to style (attr "color")
	private int textSize = 12;

	public TextAreaBox(String text, Color textColor, int textSize, int startX,
			int startY, int endX, int endY) {
		super(startX, startY, endX, endY);
		this.text = text;
		this.textColor = textColor;
		this.textSize = textSize;
		this.style.makeBackgroundTransparent();
	}

	public TextAreaBox() {
		this("", Color.black, 12, 0, 0, 0, 0);
	}

	public String toString() {
		String str = "<div style=\"position:absolute;left:" + getXOffset()
				+ "px;top:" + getYOffset() + "px;width:" + width()
				+ "px;height:" + height() + "px;" + style + "\">";
		str += text;
		str += "</div>";
		return str;
	}

	public String getText() {
		if (text == null) {
			return "Enter text...";
		} else {
			return this.text;
		}
	}

	/*
	 * This is a special draw method to draw a string within given rectangular
	 * bounds. Handles line wrapping.
	 */
	public static void drawString(Graphics2D g2, String s, int x, int y,
			int width, int height) {
		// FontMetrics gives us information about the width,
		// height, etc. of the current Graphics object's Font.
		FontMetrics fm = g2.getFontMetrics();

		int lineHeight = fm.getHeight();

		int curX = x;
		int curY = y + lineHeight;

		String[] words = s.split(" ");

		for (String word : words) {
			// Find out the width of the word.
			int wordWidth = fm.stringWidth(word + " ");

			// If text exceeds the width, then move to next line.
			if (curX + wordWidth >= x + width) {
				// If text exceeds the height, then draw ellipsis and return.
				if (curY + lineHeight >= y + height) {
					g2.drawString("...", curX, curY);
					return;
				}
				curY += lineHeight;
				curX = x;
			}

			g2.drawString(word, curX, curY);

			// Move over to the right for next word.
			curX += wordWidth;
		}
	}

	public Color getTextColor() {
		return textColor;
	}

	public int getTextSize() {
		return textSize;
	}

	@Override
	public JsonObject toJsonObj() {
		JsonObject obj = super.toJsonObj();
		obj.addProperty("text", getText());

		return obj;
	}

	@Override
	public void fromJsonObj(JsonObject obj) {
		super.fromJsonObj(obj);
		this.text = obj.get("text").getAsString();
	}
}
