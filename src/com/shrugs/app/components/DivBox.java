package com.shrugs.app.components;

import java.util.LinkedList;

public class DivBox extends Box {

	protected LinkedList<Box> children;

	public DivBox(int startX, int startY, int endX, int endY) {
		super(startX, startY, endX, endY);
		children = new LinkedList<Box>();
	}

	public LinkedList<Box> getChildren() {
		return children;
	}

	public String toString() {
		return "div";
	}

	public void addChild(Box box) {
		children.add(box);
		box.setParent(this);
	}

	public void removeChild(Box box) {
		children.remove(box);
		box.setParent(null);
	}

	/**
	 * Finds the Box which contains the specified point. Should only be called
	 * from BodyBox for safety.
	 * 
	 * @param x
	 *            the x-coordinate of the point to check
	 * @param y
	 *            the y-coordinate of the point to check
	 * @return the smallest box that contains the specified point
	 */
	protected Box youngestBoxContainingPoint(int x, int y) {
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

	/**
	 * Checks if a specified box overlaps with any children of this box.<br>
	 * <br>
	 * Usage:
	 * <ul>
	 * <li>Set <b>int</b>s <i>x1, y1</i> on mousedown using mouse coordinates.
	 * <li>Set <b>Box</b> <i>targetBox</i> using
	 * <i>youngestBoxContainingPoint(x1, y1)</i> on top level <b>BodyBox</b>.
	 * Grab grid snaps, etc.
	 * <li>Set <b>int</b>s <i>x2, y2</i> on mouseup using mouse cordinates.
	 * Ensure that <i>youngestBoxContainingPoint(x2, y2) == TargetBox</i>.
	 * <li>Create <b>Box</b> <i>newBox</i> on mouseup using <i>x1, y1, x2,
	 * y2</i>.
	 * <li>Verify that <i>targetBox.childrenCollideWith(newBox) == false</i>.
	 * <li>Do <i>targetBox.addChild(newBox)</i>.
	 * </ul>
	 * 
	 * @param box
	 *            The box to check against
	 * @return false (desirable) if the specified box does not overlap with any
	 *         children; true (undesirable) otherwise.
	 */
	public boolean childrenCollideWith(Box box) {
		for (Box c : children) {
			if (c.collidesWith(box))
				return true;
		}
		return false;
	}
}
