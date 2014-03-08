package com.shrugs.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import com.shrugs.app.components.Box;
import com.shrugs.app.components.ImageBox;
import com.shrugs.app.gui.DrawableView;

public class Export {

	private static BufferedWriter writer;

	public static void export(Box box) throws IOException {
		export(box, "output");
	}

	public static void export(Box box, String outputDir) throws IOException {
		File path = new File(outputDir);
		path.mkdir();
		File indexFile = new File(outputDir + "/index.html");
		indexFile.createNewFile();
		writer = new BufferedWriter(new FileWriter(indexFile));
		writer.append(DrawableView.bodyBox.toString());
		writer.close();
		for (String imgpath : ImageBox.ImageList.keySet()) {
			String[] patharr = imgpath.split("/");
			String name = patharr[patharr.length - 1];
			System.out.println("Saving file: " + outputDir + "/img/" + name);
			if (!(new File(imgpath).toPath().equals(new File(outputDir
					+ "/img/" + name).toPath())))
				FileUtils.copyFile(new File(imgpath), new File(outputDir
						+ "/img/" + name));
		}
		JOptionPane.showMessageDialog(null, "Export to:\n'" + outputDir
				+ "'\nSuccessful.");
	}
}
