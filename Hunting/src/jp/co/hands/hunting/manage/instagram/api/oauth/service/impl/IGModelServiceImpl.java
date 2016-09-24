package jp.co.hands.hunting.manage.instagram.api.oauth.service.impl;

import static jp.co.hands.hunting.manage.instagram.api.helper.GsonHelper.GSON;
import static jp.co.hands.hunting.manage.instagram.api.helper.HttpHelper.HTTP_CLIENT;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.yaml.snakeyaml.Yaml;

import jp.co.hands.hunting.manage.instagram.api.helper.YamlHelper;
import jp.co.hands.hunting.manage.instagram.api.oauth.entity.AccessToken;
import jp.co.hands.hunting.manage.instagram.api.oauth.entity.Code;
import jp.co.hands.hunting.manage.instagram.api.oauth.service.IGModelService;
import jp.co.hands.hunting.manage.instagram.api.utils.InstagramClientInfo;
import jp.co.hands.hunting.manage.instagram.api.utils.InstagramOAuthInfo;
import jp.co.hands.hunting.manage.instagram.api.utils.filename.YamlUtils;

@Stateless
public class IGModelServiceImpl implements IGModelService, Serializable {

	@Override
	public AccessToken getAccessToken(String code) {
		
		YamlHelper yamlHelper = new YamlHelper();
		try {
			InstagramClientInfo instagramClientInfo = (InstagramClientInfo)yamlHelper.getYamlInfo(InstagramClientInfo.class, YamlUtils.CLIENT_INFO_YAML_FILE_NAME);
			InstagramOAuthInfo instagramOAuthInfo = (InstagramOAuthInfo)yamlHelper.getYamlInfo(InstagramOAuthInfo.class, YamlUtils.OAUTH_YAML_FILE_NAME);
			System.out.println("instagramClientInfo:    "+ instagramClientInfo);
			System.out.println("instagramOAuthInfo.grant_type:    "+ instagramOAuthInfo.getGrantType());
			System.out.println("instagramOAuthInfo.redirectURI:    "+ instagramOAuthInfo.getRedirectUri());			
			// Post用のURLをセット
			HttpPost method = new HttpPost(instagramOAuthInfo.getEndPoints());
			List<NameValuePair> nameValuePairs = new ArrayList<>();
			nameValuePairs.add(new BasicNameValuePair("client_id", instagramClientInfo.getClientId()));
			nameValuePairs.add(new BasicNameValuePair("client_secret", instagramClientInfo.getClientSecret()));
			nameValuePairs.add(new BasicNameValuePair("grant_type", instagramOAuthInfo.getGrantType()));
			nameValuePairs.add(new BasicNameValuePair("redirect_uri", instagramOAuthInfo.getRedirectUri()));
			nameValuePairs.add(new BasicNameValuePair("code", code));

			// 値をEntityにセット
			method.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Postの実行
			HttpResponse response = HTTP_CLIENT.execute(method);
			// Post結果のStatus確認
			int status = response.getStatusLine().getStatusCode();
			// Post結果のBodyの確認
			String body = EntityUtils.toString(response.getEntity());
			// StatusCheckし、OKであればAccessTokenモデルに値をセット
			if (status == HttpStatus.SC_OK) {
				return GSON.fromJson(body, AccessToken.class);
			} else {
				throw new RuntimeException(
						String.format("Could not send message status code: %s response: %s", status, body));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
