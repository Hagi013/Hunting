package jp.co.hands.hunting.helper.fetcher;

import java.io.ByteArrayInputStream;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public final class FetchPictureHelper {

	/**
	 * 画像をレンダリングするメソッド(DBより取得した画像データのバイナリーをStreamedContentに変換して返す)
	 * 
	 * @param return
	 *            ds: DBから取得した画像データ
	 */
	public static StreamedContent getConvertPic(byte[] byteImage) {

		FacesContext con = FacesContext.getCurrentInstance();
		if (con.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// HTMLのレンダリングフェースなのでPrimeFaces用の特殊なファイルURLを返す
			return new DefaultStreamedContent();
		} 
		ByteArrayInputStream out = new ByteArrayInputStream(byteImage);
		DefaultStreamedContent ds = new DefaultStreamedContent(out);
		return ds;

	}


	
}
