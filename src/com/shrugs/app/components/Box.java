package com.shrugs.app.components;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class Box {
	@Expose
	protected int startX; // Coordinates of box that is currently being drawn
	@Expose
	protected int startY;
	@Expose
	protected int endX;
	@Expose
	protected int endY = 0;
	protected Box parent;
	protected boolean highlight;
	@Expose
	protected BoxStyle style;
	protected String tagName = "div";

	protected static float[] snapRatios = { 0, .15f, .25f, .33f, .50f, .67f,
			.75f, .85f, 1 };

	public Box() {
		this(0, 0, 0, 0, null);
	}

	public Box(int startX, int startY, int endX, int endY) {
		this(startX, startY, endX, endY, null);
	}

	public Box(int startX, int startY, int endX, int endY, Box parent) {
		this.startX = Math.min(startX, endX);
		this.startY = Math.min(startY, endY);
		this.endX = Math.max(startX, endX);
		this.endY = Math.max(startY, endY);
		this.parent = parent;
		this.highlight = false;
		this.style = new BoxStyle();
	}

	public LinkedList<Box> getChildren() {
		return null;
	}

	public int getStartX() {
		return this.startX;
	}

	public int getStartY() {
		return this.startY;
	}

	public int getEndX() {
		return this.endX;
	}

	public int getEndY() {
		return this.endY;
	}

	public Box getParent() {
		return this.parent;
	}

	public boolean gethighlight() {
		return this.highlight;
	}

	public BoxStyle getStyle() {
		return this.style;
	}

	public int width() {
		return this.endX - this.startX;
	}

	public int height() {
		return this.endY - this.startY;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public void setParent(Box parent) {
		this.parent = parent;
	}

	public void setHighlight(boolean toggle) {
		this.highlight = toggle;
	}

	public void setStyle(BoxStyle style) {
		this.style = style;
	}

	public int getXOffset() {
		return startX - parent.getStartX();
	}

	public int getYOffset() {
		return startY - parent.getStartY();
	}

	public LinkedList<Integer> getHSnaps() {
		LinkedList<Integer> snaps = new LinkedList<Integer>();
		int width = this.width();
		for (float f : snapRatios)
			snaps.add((int) (f * width) + this.startX);
		snaps.set(snapRatios.length - 1, snaps.get(snapRatios.length - 1) + 1);
		return snaps;
	}

	public LinkedList<Integer> getVSnaps() {
		LinkedList<Integer> snaps = new LinkedList<Integer>();
		int height = this.height();
		for (float f : snapRatios)
			snaps.add((int) (f * height) + this.startY);
		snaps.set(snapRatios.length - 1, snaps.get(snapRatios.length - 1) + 1);
		return snaps;
	}

	/*
	 * Finds the nearestHSnap to the given x coordinate.
	 * 
	 * @param xCoord The X coordinate to find the nearest hSnap to.
	 * 
	 * @return The nearest hSnap.
	 */
	public Integer getNearestHSnap(int xCoord) {
		int minDistance = Integer.MAX_VALUE;
		int closestHSnap = 0;
		int distance;

		for (Integer hSnap : this.getHSnaps()) {
			distance = Math.max(hSnap, xCoord) - Math.min(hSnap, xCoord);
			if (distance < minDistance) {
				minDistance = distance;
				closestHSnap = hSnap.intValue();
			}
		}
		return closestHSnap;
	}

	/*
	 * Finds the nearestVSnap to the given y coordinate.
	 * 
	 * @param yCoord The Y coordinate to find the nearest vSnap to.
	 * 
	 * @return The nearest vSnap.
	 */
	public Integer getNearestVSnap(int yCoord) {
		int minDistance = Integer.MAX_VALUE;
		int closestVSnap = 0;
		int distance;

		for (Integer vSnap : this.getVSnaps()) {
			distance = Math.max(vSnap, yCoord) - Math.min(vSnap, yCoord);
			if (distance < minDistance) {
				minDistance = distance;
				closestVSnap = vSnap.intValue();
			}
		}
		return closestVSnap;
	}

	public int locationRelativeToBox(int x, int y) {
		if (x >= (endX) && y <= startY)
			return 1;
		else if (x <= endX && x >= startX && y <= startY)
			return 2;
		else if (x <= startX && y <= startY)
			return 3;
		else if (x <= startX && y >= startY && y <= endY)
			return 4;
		else if (x <= startX && y >= endY)
			return 5;
		else if (x <= endX && x >= startX && y >= endY)
			return 6;
		else if (x >= endX && y >= endY)
			return 7;
		else if (x >= endX && y >= startY && y <= endY)
			return 8;
		else
			return 0;
	}

	public void showAttributesMenu() {
		// TODO: This method needs to display a dialogue of attributes.
		JOptionPane.showMessageDialog(null, this.toString()
				+ "\nTODO: This will be an attributes setting dialogue.");
	}

	public boolean containsPoint(int x, int y) {
		return !(this.startX > x || this.endX < x || this.startY > y || this.endY < y);
	}

	/**
	 * Checks if the box overlaps with another box
	 * 
	 * @param b
	 *            the box to check against
	 * @return true if the boxes overlap; false otherwise
	 */
	public boolean collidesWith(Box b) {
		return !(this.startX >= b.endX || this.endX <= b.startX
				|| this.startY >= b.endY || this.endY <= b.startY);
	}

	public JsonObject toJsonObj() {
		JsonObject obj = new JsonObject();
		obj.addProperty("type", getClass().getName());
		obj.addProperty("x1", startX);
		obj.addProperty("x2", endX);
		obj.addProperty("y1", startY);
		obj.addProperty("y2", endY);
		obj.add("style", style.toJsonObj());
		return obj;
	}
	
	public void fromJsonObj(JsonObject obj) {
		this.startX = obj.get("x1").getAsInt();
		this.endX = obj.get("x2").getAsInt();
		this.startY = obj.get("y1").getAsInt();
		this.endY = obj.get("y2").getAsInt();
		this.style = new BoxStyle();
		this.style.fromJsonObj(obj.get("style").getAsJsonObject());
	}

}