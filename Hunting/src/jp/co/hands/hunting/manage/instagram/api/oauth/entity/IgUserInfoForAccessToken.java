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
public class IgUserInfoForAccessToken implements Serializable {
	
	@SerializedName("id")
	private String id;
	
	@SerializedName("username")
	private String userName;
	
	@SerializedName("full_name")
	private String fullName;
	
	@SerializedName("profile_picture")
	private String profilePic;
		
	
}
