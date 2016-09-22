package jp.co.hands.hunting.manage.instagram.api.users.service.Impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import jp.co.hands.hunting.manage.instagram.api.helper.YamlHelper;
import jp.co.hands.hunting.manage.instagram.api.http.Request;
import jp.co.hands.hunting.manage.instagram.api.http.Verbs;
import jp.co.hands.hunting.manage.instagram.api.oauth.entity.AccessToken;
import jp.co.hands.hunting.manage.instagram.api.users.entity.IgModelInfo;
import jp.co.hands.hunting.manage.instagram.api.users.entity.IgModelRecievedInfo;
import jp.co.hands.hunting.manage.instagram.api.users.service.IgModelService;
import jp.co.hands.hunting.manage.instagram.api.utils.InstagramUsers;
import jp.co.hands.hunting.manage.instagram.api.utils.filename.YamlUtils;

import static jp.co.hands.hunting.manage.instagram.api.helper.GsonHelper.GSON;
import static jp.co.hands.hunting.manage.instagram.api.helper.HttpHelper.HTTP_CLIENT;;


@SessionScoped
public class IgModelServiceImpl implements IgModelService {
	
	@Inject
	private AccessToken accessToken;
	
	private InstagramUsers instagramUsers;

	public IgModelServiceImpl() throws IOException {
		YamlHelper yamlHelper = new YamlHelper();
		instagramUsers = (InstagramUsers) yamlHelper.getYamlInfo(InstagramUsers.class, YamlUtils.USERS_YAML_FILE_NAME);
	}
	
	
	@Override
	public IgModelInfo recieveIgModel(String id) {
		
		try {
			URIBuilder uriBuilder = buildUri(accessToken.getAccessToken());
			uriBuilder.setPath(addSerachId(new StringBuilder(instagramUsers.getEndPointPath()), id).toString());
			HttpResponse response = Request.builder().verb(Verbs.GET).uri(uriBuilder.build()).build().doSend(); 
			int status = response.getStatusLine().getStatusCode();
			String body = EntityUtils.toString(response.getEntity());
			if(status == HttpStatus.SC_OK) {
				IgModelRecievedInfo igModelRecievedInfo = GSON.fromJson(body, IgModelRecievedInfo.class);
				return igModelRecievedInfo.getIgModelInfo();
			} else {
				throw new RuntimeException(
						String.format("Could not send message status code: %s response: %s", status, body));
			}						
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}	
		return null;				
	}
		
	private URIBuilder buildUri(String accessToken) throws IOException {

		URIBuilder uriBuilder = new URIBuilder(); 
		String endPointHost = instagramUsers.getEndPointHost();
		uriBuilder.setScheme("https").setHost(endPointHost).setParameter("access_token", accessToken);
		return uriBuilder;
	}
	
	private StringBuilder addSerachId(StringBuilder targetUri, String id) {
		return targetUri.append(id);
	}
}
