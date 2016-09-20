package jp.co.hands.hunting.manage.instagram.api.oauth.adapter;

import static jp.co.hands.hunting.manage.instagram.api.helper.JsonHelper.addIfPresent;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import jp.co.hands.hunting.manage.instagram.api.oauth.entity.AccessToken;

public class AccessTokenAdapter implements JsonSerializer<AccessToken> {
	
	public JsonElement serialize(AccessToken value, Type type, JsonSerializationContext context) {
		
		JsonObject jsonObj = new JsonObject();
		addIfPresent(jsonObj, "accessToken", value.getAccessToken());
		jsonObj.add("user", context.serialize(value.getIgUserInfoForAccessToken()));
		return jsonObj;		
	}
}
