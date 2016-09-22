package jp.co.hands.hunting.manage.instagram.api.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import jp.co.hands.hunting.manage.instagram.api.helper.HttpHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class Request {

	private List<NameValuePair> nameValuePairs;

	private Verbs verb;

	private URI uri;

	public Request(Verbs verb, URI uri) {
		this.verb = verb;
		this.uri = uri;
	}

	public HttpResponse doSend() throws ClientProtocolException, IOException {

			if (verb == Verbs.GET) {
				HttpGet method = new HttpGet(uri);
				return send(method);
			} 
			if (verb == Verbs.POST && Optional.ofNullable(nameValuePairs).isPresent()) {
				HttpPost method = new HttpPost(uri);
				return send(method);
			}
			throw new RuntimeException(
					String.format("Could not send this verb: %s uri: %s nameVluePairs: %s", verb, uri, nameValuePairs));
			
	}

	private HttpResponse send(HttpGet method) throws ClientProtocolException, IOException {
		return HttpHelper.HTTP_CLIENT.execute(method);
	}

	private HttpResponse send(HttpPost method) throws ClientProtocolException, IOException {
		method.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		return HttpHelper.HTTP_CLIENT.execute(method);
	}

}
