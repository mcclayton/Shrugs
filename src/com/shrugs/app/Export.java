package com.shrugs.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.shrugs.app.components.Box;



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
		writer.append("<!doctype html>"
					+ "<html>"
					+ "<head>"
					+ "<title>Shrugs Webpage</title>"
					+ "</head>"
					+ "<body style=\"margin:0;\">");
		DFS(box);
		writer.append("</body>"
					+ "</html>");
		writer.close();
	}
	
	private static void DFS(Box box) throws IOException {
		writer.append("<"+box.toString()+" style=\""+getStyle(box)+"\">");
		for(Box child : box.getChildren()) {
			DFS(child);
		}
		writer.append("</"+box.toString()+">");
	}
	
	private static String getStyle(Box box) {
		String style = 
				"position:absolute;"
				+ "left:"+box.getXOffset()+"px;"
				+ "top:"+box.getYOffset()+"px;"
				+ "width:"+(box.width())+"px;"
				+ "height:" +(box.height())+"px;"
				+ "margin:0;padding:0;";
		if(box.getStyle()!=null)
			style+=box.getStyle();
		return style;
	}

}
