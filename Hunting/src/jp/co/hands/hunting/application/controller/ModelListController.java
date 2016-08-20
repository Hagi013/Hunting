package jp.co.hands.hunting.application.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import jp.co.hands.hunting.application.helper.JsfManagedObjectFetcher;
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
	private HuntingModelRepository huntingModelRepository;
	
	@Getter @Setter
	private List<HuntingModel> igModelList;
	
	
	@PostConstruct
	public void init() {
		igModelList = huntingModelRepository.findAll();	
	}
	
	/**
	 * 画像をレンダリングするメソッド(DBより取得した画像データのバイナリーをStreamedContentに変換して返す)
	 * @param return ds: DBから取得した画像データ
	*/
	public StreamedContent getConvertPic() {
		
		HuntingModel targetModel = HuntingModel.builder().build();
		
		FacesContext con = FacesContext.getCurrentInstance();
		if(con.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// HTMLのレンダリングフェースなのでPrimeFaces用の特殊なファイルURLを返す
			return new DefaultStreamedContent();
		} else {
			targetModel = huntingModelRepository.findByKey(JsfManagedObjectFetcher.getString("igModelId"));
		}
		ByteArrayInputStream out = new ByteArrayInputStream(targetModel.getProfilePicture());
		DefaultStreamedContent ds = new DefaultStreamedContent(out);
		return ds;
		
	}
	
}
