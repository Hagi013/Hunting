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
public class IgModelInfoCounts extends InstagramObject {
	
	@SerializedName("media")
	private int media;
	
	@SerializedName("follows")
	private int follows;
	
	@SerializedName("followed_by")
	private int followedBy;

}
