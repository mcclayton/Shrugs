package com.shrugs.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.shrugs.app.components.Box;
import com.shrugs.app.components.DivBox;
import com.shrugs.app.components.TextAreaBox;
import com.shrugs.app.gui.DrawableView;



public class Export {
	
	/* public static void main(String[] args) {
		try {
			BodyBox body = new BodyBox(0,0,900,1000);
			DivBox box1 = new DivBox(10,10,300,300);
			DivBox box2 = new DivBox(10,10,100,100);
			DivBox box3 = new DivBox(20,20,90,90);
			DivBox box4 = new DivBox(110,20,200,110);
			body.addChild(box1);
			box1.addChild(box2);
			box2.addChild(box3);
			box1.addChild(box4);
			export(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} */
	
	private static BufferedWriter writer;
	
	public static void export(Box box) throws IOException {
		export(box, "output");
	}
	
	//TODO: tags should be generated as <[tag] id=[id]>[content/children]</[tag]>
	public static void export(Box box, String outputDir) throws IOException {
		File path = new File(outputDir);
		path.mkdir();
		File indexFile = new File(outputDir+"/index.html");
		indexFile.createNewFile();
		writer = new BufferedWriter(new FileWriter(indexFile));
		writer.append(DrawableView.bodyBox.toString());
		writer.close();
	}

}
