package com.shrugs.app.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shrugs.app.components.BodyBox;

public class BodyBoxDeserializer implements JsonDeserializer<BodyBox> {

	@Override
	public BodyBox deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		BodyBox body = new BodyBox(obj.get("x1").getAsInt(),obj.get("y1").getAsInt(),obj.get("x2").getAsInt(),obj.get("y2").getAsInt());
		//TODO: add children
		
		return body;
	}

}
