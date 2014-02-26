package com.shrugs.app.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.shrugs.app.components.BodyBox;

public class BodyBoxAdapter implements JsonSerializer<BodyBox>, JsonDeserializer<BodyBox> {

	@Override
	public JsonElement serialize(BodyBox src, Type typeOfSrc,
			JsonSerializationContext context) {
		return src.toJsonObj();
	}
	
	@Override
	public BodyBox deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		BodyBox body = new BodyBox();
		try {
			body.fromJsonObj(obj);
		} catch (Exception e) {
			System.err.println("Error loading file.");
			e.printStackTrace();
		}

		return body;
	}

	
}
