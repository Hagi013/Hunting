package jp.co.hands.hunting.manage.instagram.api.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstagramUsers {
	
	private String endPointHost;
	private String endPointPath;

}
