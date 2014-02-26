package com.shrugs.app.components;

import java.awt.Image;
import java.util.LinkedList;

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
}
