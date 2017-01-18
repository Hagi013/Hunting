package jp.co.hands.hunting.manage.helper;

import java.io.IOException;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FetchImgFromIG {
	
	public String executeFetch(String url) throws IOException {
		url = url.substring(0, url.indexOf('?'));
		System.out.println("url;   "+url);
		Document document = Jsoup.connect(url).data()
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
				.timeout(10000).followRedirects(true).get();
		System.out.println("aaa;   "+document);
		Elements elements = document.select("._jjzlb._icyx7");
		System.out.println(elements.size());
		String imgSrc = elements.get(elements.size() - 1).attr("src");
		System.out.println(imgSrc);
		return imgSrc;
		
	}
	
}
