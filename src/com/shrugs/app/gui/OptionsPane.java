package com.shrugs.app.gui;

import java.awt.Color;
import java.awt.Label;
import java.awt.Panel;

public class OptionsPane extends Panel {
	private static final long serialVersionUID = 1L;

	public OptionsPane()
	{
		this.setBackground(Color.LIGHT_GRAY);
		this.add(new Label("OPTIONS PANE"));
		for (int i=0; i<10; i++) {
			this.add(new Label("Test Label "+i));
		}
	}

}
