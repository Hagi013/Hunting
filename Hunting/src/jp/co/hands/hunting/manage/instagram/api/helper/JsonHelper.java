package jp.co.hands.hunting.manage.instagram.api.helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class JsonHelper {
	
	public static void addIfPresent(JsonObject jsonObj, String property, Object value) {
		
		if (value == null) {
			return;
		}
		
		if (value instanceof String) {
			jsonObj.add(property, new JsonPrimitive((String)value));
		} else if (value instanceof Number) {
			jsonObj.add(property, new JsonPrimitive((Number)value));
		} else if (value instanceof Boolean) {
			jsonObj.add(property, new JsonPrimitive((Boolean)value));
		} else {
			throw new UnsupportedOperationException("object must be primitive wrapper object");
		}	
	}
	
	public static void addIfPresent(JsonObject jsonObj, String property, Object[] values) {
		
		if (values == null) {
			return;
		}
		JsonArray jsonArray = new JsonArray();
		if (values instanceof String[]) {
			for(String value : (String[])values) {
				jsonArray.add(value);
			}
		} else if (values instanceof Number[]) {
			for (Number value : (Number[]) values) {
				jsonArray.add(value);
			}
		} else if (values instanceof Boolean[]) {
			for (Boolean value : (Boolean[])values) {
				jsonArray.add(value);
			}
		} else {
            throw new UnsupportedOperationException("object values must be primitive wrapper array object");
		}
		jsonObj.add(property, jsonArray);
	}
}
