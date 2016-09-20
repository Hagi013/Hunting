package jp.co.hands.hunting.manage.instagram.api.users.adapter;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import jp.co.hands.hunting.manage.instagram.api.users.entity.IgModelRecievedInfo;

public class IgModelRecievedInfoAdapter implements JsonSerializer<IgModelRecievedInfo> {
	
	@Override
	public JsonElement serialize(IgModelRecievedInfo value, Type type, JsonSerializationContext context) {
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.add("data", context.serialize(value.getIgModelIngo()));
		return jsonObj;		
	}
	
}
