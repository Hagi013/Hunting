package jp.co.hands.hunting.manage.instagram.api.users.adapter;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import jp.co.hands.hunting.manage.instagram.api.users.entity.IgModelInfo;

import static jp.co.hands.hunting.manage.instagram.api.helper.JsonHelper.addIfPresent;


public class IgModelInfoAdapter implements JsonSerializer<IgModelInfo>{

	@Override
	public JsonElement serialize(IgModelInfo value, Type type, JsonSerializationContext context) {
		
		JsonObject jsonObj = new JsonObject();		
		addIfPresent(jsonObj, "id", value.getId());
		addIfPresent(jsonObj, "username", value.getUserName());
		addIfPresent(jsonObj, "full_name", value.getFullName());
		addIfPresent(jsonObj, "profile_picture", value.getProfilePicture());
		addIfPresent(jsonObj, "bio", value.getBio());
		addIfPresent(jsonObj, "website", value.getWebsite());
		jsonObj.add("counts", context.serialize(value.getIgModelInfoCounts()));
		return jsonObj;	
	}
}
