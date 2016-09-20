package jp.co.hands.hunting.manage.instagram.api.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jp.co.hands.hunting.manage.instagram.api.oauth.adapter.AccessTokenAdapter;
import jp.co.hands.hunting.manage.instagram.api.oauth.adapter.IgUserInfoAdapter;
import jp.co.hands.hunting.manage.instagram.api.oauth.entity.AccessToken;
import jp.co.hands.hunting.manage.instagram.api.oauth.entity.IgUserInfoForAccessToken;
import jp.co.hands.hunting.manage.instagram.api.users.adapter.IgModelRecievedInfoAdapter;
import jp.co.hands.hunting.manage.instagram.api.users.entity.IgModelRecievedInfo;

public class GsonHelper {
	
	public static Gson GSON;
	
	static {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(AccessToken.class, new AccessTokenAdapter());
		builder.registerTypeAdapter(IgUserInfoForAccessToken.class, new IgUserInfoAdapter());
		builder.registerTypeAdapter(IgModelRecievedInfo.class, new IgModelRecievedInfoAdapter());
		GSON = builder.create();		
	}
}
