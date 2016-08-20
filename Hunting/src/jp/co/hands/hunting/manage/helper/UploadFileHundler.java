package jp.co.hands.hunting.manage.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

public class UploadFileHundler {

	public static byte[] fileHundle(Part target) {

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

		System.out.println(imageByteArray.toByteArray().toString());

		// 書き込み先はByteArrayOutputStreamクラス内部となる。
		// この書き込まれたバイトデータをbyte型配列として取り出す場合には、
		// toByteArray()メソッドを呼び出す。
		return imageByteArray.toByteArray();

	}
}
