package jp.co.hands.hunting.manage.instagram.api.oauth.entity;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessToken implements Serializable {
	
	@SerializedName("access_token")
	private String accessToken;
	
	@SerializedName("user")
	private IgUserInfoForAccessToken igUserInfoForAccessToken;

}
