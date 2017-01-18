package jp.co.hands.hunting.helper;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.servlet.http.Part;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import jp.co.hands.hunting.utils.HuntingUserImg;

public class FileHundler {

	public static byte[] fileConvToByte(Part target) {

		// byte型の配列を出力先とするクラス。
		// 通常、バイト出力ストリームはファイルやソケットを出力先とするが、
		// ByteArrayOutputStreamクラスはbyte[]変数、つまりメモリを出力先とする。
		ByteArrayOutputStream imageByteArray = new ByteArrayOutputStream();
		
		try {
			InputStream modelImage = target.getInputStream();
			int c;
			while ((c = modelImage.read()) != -1) {
				imageByteArray.write(c);
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			if (imageByteArray != null) {
				try {
					imageByteArray.flush();
					imageByteArray.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}

		// 書き込み先はByteArrayOutputStreamクラス内部となる。
		// この書き込まれたバイトデータをbyte型配列として取り出す場合には、
		// toByteArray()メソッドを呼び出す。
		return imageByteArray.toByteArray();
	}
	
	
	public static void writeByteToImageFile(String name, byte[] imgByte) {
		
		if(!Optional.ofNullable(name).isPresent() || !Optional.ofNullable(imgByte).isPresent()) return;
		
		File file = new File(name);
				
		try (FileOutputStream forSave = new FileOutputStream(file)){
		
			if(!file.exists()) {
				file.createNewFile();
			}	
			forSave.write(imgByte);
			forSave.flush();
			forSave.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static byte[] imageConvFromStr(String targetPath) {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		File f = new File(targetPath);
		
		try(BufferedInputStream in = new BufferedInputStream (new FileInputStream(f))) {
			int c;
			while((c = in.read()) != -1) {
				out.write(c);
			}
			out.flush();
			out.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
	
	
	public static byte[] imageConvFromWeb(String url) {
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet method = new HttpGet(url);
		try {
			HttpResponse res = client.execute(method);
			int status = res.getStatusLine().getStatusCode();
			byte[] image = EntityUtils.toByteArray(res.getEntity());
			if(status == HttpStatus.SC_OK) {
				return image;
			} else {
				throw new RuntimeException(
						String.format("Could not send message status code: %s response: %s", status, image));
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
}
