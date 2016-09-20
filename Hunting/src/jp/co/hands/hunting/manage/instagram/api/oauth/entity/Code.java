package jp.co.hands.hunting.manage.instagram.api.oauth.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Code implements Serializable {
	
	private String code;
	
}
