package jp.co.hands.hunting.manage.instagram.api.users.entity;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import jp.co.hands.hunting.manage.instagram.api.entity.InstagramObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IgModelInfo extends InstagramObject {

	@SerializedName("id")
	private String id;
	
	@SerializedName("username")
	private String userName;
	
	@SerializedName("full_name")
	private String fullName;
	
	@SerializedName("profile_picture")
	private String  profilePicture;
	
	@SerializedName("bio")
	private String bio;
	
	@SerializedName("website")
	private String website;
	
	@SerializedName("counts")
	private IgModelInfoCounts igModelInfoCounts;
	
}
