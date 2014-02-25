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
    	snaps.set(snaps.size()-1,snaps.get(snaps.size()-1)+1);
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
	
	public LinkedList<Box> flatten() {
		LinkedList<Box> lst = new LinkedList<Box>();
		LinkedList<Box> queue = new LinkedList<Box>();
		lst.add(this);
		queue.addAll(this.getChildren());
		while(!queue.isEmpty()) {
			Box b = queue.removeFirst();
			if(b instanceof DivBox)
				queue.addAll(((DivBox)b).getChildren());
			lst.add(b);
		}
		return lst;
	}
	
	public void reassociateAllChildren() {
		this.reassociateChildren();
		
		LinkedList<Box> queue = new LinkedList<Box>();
		queue.addAll(this.children);
		while(!queue.isEmpty()) {
			Box b = queue.removeFirst();
			if(!(b instanceof DivBox))
				continue;
			DivBox db = (DivBox) b;
			db.reassociateChildren();
			queue.addAll(db.getChildren());
		}
	}
}
