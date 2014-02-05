package com.shrugs.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Export {
	
	public static void main(String[] args) {
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
	}
	
	private static BufferedWriter writer;
	
	public static void export(Box box) throws IOException {
		export(box, "output");
	}
	
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
				+ "width:"+(box.getEndX()-box.getStartX())+"px;"
				+ "height:" +(box.getEndY()-box.getStartY())+"px;"
				+ "background:"+randomColor()+";"
				+ "margin:0;padding:0;";
		return style;
	}
	
	private static String randomColor() {
		String out = "rgb(";
		for(int i=0;i<3;i++)
			out+=(int)Math.floor(Math.random()*256)+",";
		out = out.substring(0, out.length()-1) + ")";
		return out;
	}

}
