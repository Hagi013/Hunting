package jp.co.hands.hunting.manage.instagram.api.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

public class HttpHelper {

	public static HttpClient HTTP_CLIENT;
	
	static {
		List<Header> headers = new ArrayList<>();
		headers.add(new BasicHeader("Content-type", "application/json; charset=UTF-8"));
		HTTP_CLIENT = HttpClientBuilder.create()
				.setDefaultRequestConfig(RequestConfig.DEFAULT)
				.setDefaultHeaders(headers).build();
	}
	
}
