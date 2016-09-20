package jp.co.hands.hunting.manage.instagram.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.yaml.snakeyaml.Yaml;

import jp.co.hands.hunting.manage.instagram.api.utils.filename.YamlUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstagramOAuthInfo implements Serializable {

	private String endPoints;	
	private String grantType;
	private String redirectUri;
	
}
