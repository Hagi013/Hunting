package jp.co.hands.hunting.application.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import jp.co.hands.hunting.application.domain.HuntingRenderingBl;
import jp.co.hands.hunting.controller.BaseController;
import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.repository.impl.HuntingModelRepository;
import lombok.Getter;
import lombok.Setter;

@Named(value="modelListController")
@ManagedBean(name="modelListController")
@SessionScoped
public class ModelListController extends BaseController {

	@Inject
	private HuntingRenderingBl huntingRenderingBl;
	
	@Inject
	private HuntingModelRepository huntingModelRepository;
		
	@Getter @Setter
	private List<HuntingModel> igModelList;
		
	@PostConstruct
	public void init() {
		igModelList = huntingModelRepository.findAll();
	}
	
	/**
	 * 画像をレンダリングするメソッド(JSFより取得した画像パスから画像をStreamedContentに変換して返す)
	 * @param return 画像のパスから取得した画像データ
	*/	
	public StreamedContent getImagePath() {
		// JSFのライフサイクルの確認(Render_RESPONSEでもこのメソッドが呼ばれるが、mapに値が入っていない)
		FacesContext fc = FacesContext.getCurrentInstance();
		if(fc.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) return new DefaultStreamedContent();
		Map<String, String> map =  fc.getExternalContext().getRequestParameterMap();
		String profilePictureUrl = map.get("modelImg");

		if(!Optional.ofNullable(profilePictureUrl).isPresent() || profilePictureUrl.equals("") || profilePictureUrl.isEmpty()) return new DefaultStreamedContent();
		if(profilePictureUrl.startsWith("http")) return convertImg(huntingRenderingBl.imageConvFromWeb(profilePictureUrl));
		return convertImg(huntingRenderingBl.imagePathConverter(profilePictureUrl));
	}
	
	/**
	 * 画像をレンダリングするメソッド(DBより取得した画像データのバイナリーをStreamedContentに変換して返す)
	 * @param return ds: DBから取得した画像データ
	*/
	private StreamedContent convertImg(byte[] isImage) {
				
		FacesContext con = FacesContext.getCurrentInstance();
		if(con.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// HTMLのレンダリングフェースなのでPrimeFaces用の特殊なファイルURLを返す
			return new DefaultStreamedContent();
		}
		ByteArrayInputStream out = new ByteArrayInputStream(isImage);
		DefaultStreamedContent ds = new DefaultStreamedContent(out);
		System.out.println("ds:  " + ds);
		return ds;
		
	}

		
}
