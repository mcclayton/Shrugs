package com.shrugs.app.components;

import java.util.LinkedList;

public class BodyBox extends DivBox {

	public BodyBox(int startX, int startY, int endX, int endY) {
		super(startX, startY, endX, endY);
		this.highlight = true;
	}

	public int getXOffset() {
		return startX;
	}

	public int getYOffset() {
		return startY;
	}

	@Override
	public LinkedList<Integer> getVSnaps() {
		LinkedList<Integer> snaps = new LinkedList<Integer>();
		for (int i = 0; i < this.height(); i += 50)
			snaps.add(i);
		return snaps;
	}
	
	public Box youngestBoxContainingPoint(int x, int y) {
		for (Box c : children) {
			if (c.containsPoint(x, y)) {
				if (c instanceof DivBox)
					return ((DivBox) c).youngestBoxContainingPoint(x, y);
				else
					return c;
			}
		}
		return this;
	}
}
