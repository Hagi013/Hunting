package jp.co.hands.hunting.manage.instagram.api.oauth.adapter;

import static jp.co.hands.hunting.manage.instagram.api.helper.JsonHelper.addIfPresent;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import jp.co.hands.hunting.manage.instagram.api.oauth.entity.IgUserInfoForAccessToken;

public class IgUserInfoAdapter implements JsonSerializer<IgUserInfoForAccessToken>{
	
	
	public JsonElement serialize(IgUserInfoForAccessToken value, Type type, JsonSerializationContext context ) {
		
		JsonObject jsonObj = new JsonObject();
		addIfPresent(jsonObj, "id", value.getId());
		addIfPresent(jsonObj, "username", value.getUserName());
		addIfPresent(jsonObj, "full_name", value.getFullName());
		addIfPresent(jsonObj, "profile_picture", value.getProfilePic());		
		return jsonObj;
	}
}
