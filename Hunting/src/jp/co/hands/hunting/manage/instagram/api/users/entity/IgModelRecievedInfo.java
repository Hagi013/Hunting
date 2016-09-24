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
public class IgModelRecievedInfo extends InstagramObject {
	
	@SerializedName("data")
	private IgModelInfo[] igModelInfos;

}
