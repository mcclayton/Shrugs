package com.shrugs.app.components;

import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class DivBox extends Box {

	@Expose
	protected LinkedList<Box> children;

	public DivBox(int startX, int startY, int endX, int endY) {
		super(startX, startY, endX, endY);
		children = new LinkedList<Box>();
	}

	public DivBox() {
		super();
		children = new LinkedList<Box>();
	}

	public LinkedList<Box> getChildren() {
		return children;
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

	public void reassociateChildren() {
		for (Box b : children)
			b.setParent(this);
	}

	@Override
	public JsonObject toJsonObj() {
		JsonObject obj = super.toJsonObj();
		JsonArray children = new JsonArray();
		for (Box b : this.children) {
			children.add(b.toJsonObj());
		}
		obj.add("children", children);

		return obj;
	}
	
	@Override
	public void fromJsonObj(JsonObject obj) {
		super.fromJsonObj(obj);
		JsonArray children = obj.get("children").getAsJsonArray();
		for(JsonElement el : children) {
			JsonObject childObj = el.getAsJsonObject();
			try {
				Class<?> type = Class.forName(childObj.get("type").getAsString());
				Box childBox = (Box) type.newInstance();
				childBox.fromJsonObj(childObj);
				this.addChild(childBox);
			} catch (Exception e) {
				System.err.println("Error parsing JSON");
				e.printStackTrace();
			}
		}
	}
	
	public String toString() {
		String str = "<div style=\"position:absolute;left:"+getXOffset()+"px;top:"+getYOffset()+"px;width:"+(height()+1)+"px;height:"+(width()+1)+"px;"+style+"\">";
		for(Box child : children)
			str+=child;
		str+="</div>";
		return str;
	}
}
