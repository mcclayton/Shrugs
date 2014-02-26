package com.shrugs.app.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.shrugs.app.components.BodyBox;

public class BodyBoxSerializer implements JsonSerializer<BodyBox> {

	@Override
	public JsonElement serialize(BodyBox src, Type typeOfSrc,
			JsonSerializationContext context) {
		return src.toJsonObj();
	}

	
}
