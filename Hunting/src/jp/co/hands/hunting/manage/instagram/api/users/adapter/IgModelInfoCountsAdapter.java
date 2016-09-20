package jp.co.hands.hunting.manage.instagram.api.users.adapter;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import jp.co.hands.hunting.manage.instagram.api.users.entity.IgModelInfoCounts;

import static jp.co.hands.hunting.manage.instagram.api.helper.JsonHelper.addIfPresent;

public class IgModelInfoCountsAdapter implements JsonSerializer<IgModelInfoCounts>{

	@Override
	public JsonElement serialize(IgModelInfoCounts value, Type type, JsonSerializationContext context) {
		
		JsonObject jsonObj = new JsonObject();
		addIfPresent(jsonObj, "media", value.getMedia());
		addIfPresent(jsonObj, "follows", value.getFollows());
		addIfPresent(jsonObj, "followed_by", value.getFollowedBy());
		return jsonObj;
	}
}
