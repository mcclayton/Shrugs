package com.shrugs.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrugs.app.adapters.BodyBoxAdapter;
import com.shrugs.app.components.BodyBox;
import com.shrugs.app.gui.DrawableView;

public class IOManager {

	public static void Save(String path) throws IOException {
		File file = new File("data.shrug");
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		Gson gson = new GsonBuilder().registerTypeAdapter(BodyBox.class, new BodyBoxAdapter()).setPrettyPrinting().create();
		String json = gson.toJson(DrawableView.bodyBox);
		bw.write(json);
		bw.close();
	}
	
	public static BodyBox Load(String path) throws IOException {
		File file = new File("data.shrug");
		if(!file.exists())
			throw new IOException();
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		Gson gson = new GsonBuilder().registerTypeAdapter(BodyBox.class, new BodyBoxAdapter()).setPrettyPrinting().create();
		BodyBox b = gson.fromJson(br, BodyBox.class);
		return b;
	}

}
