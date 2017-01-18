package jp.co.hands.hunting.application.domain;

import java.io.IOException;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import jp.co.hands.hunting.helper.FileHundler;
import jp.co.hands.hunting.helper.YamlHelper;
import jp.co.hands.hunting.utils.HuntingUserImg;
import jp.co.hands.hunting.utils.filename.YamlUtils;


@Stateless
public class HuntingRenderingBl {

	public byte[] imagePathConverter(String profilePictureUrl) {
		System.out.println("profilePictureUrl:  "+profilePictureUrl);
		if(!Optional.ofNullable(profilePictureUrl).isPresent()) return null;
		if(profilePictureUrl.startsWith("http")) return FileHundler.imageConvFromStr(profilePictureUrl);
		
		YamlHelper<HuntingUserImg> yh = new YamlHelper<HuntingUserImg>();
		try {
			String orgPath = yh.getYamlInfo(HuntingUserImg.class, YamlUtils.HUNTING_MODEL_IMG_PATH).getImgPath();
			return FileHundler.imageConvFromStr(orgPath + profilePictureUrl);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
	
	public byte[] imageConvFromWeb(String url) {
		
		if(!Optional.ofNullable(url).isPresent()) return null;
		HttpGet method = new HttpGet(url);
		HttpClient http = HttpClientBuilder.create().build();
		try {
			HttpResponse res = http.execute(method);
			int status = res.getStatusLine().getStatusCode();
			byte[] image = EntityUtils.toByteArray(res.getEntity());
			if(status == HttpStatus.SC_OK) {
				return image;
			} else {
				throw new RuntimeException(
						String.format("Could not send message status code: %s response: %s", status, image));
			}
		} catch (ClientProtocolException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return null;		
	}
	
}
