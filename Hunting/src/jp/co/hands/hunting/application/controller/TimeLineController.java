package jp.co.hands.hunting.application.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import jp.co.hands.hunting.application.helper.JsfManagedObjectFetcher;
import jp.co.hands.hunting.application.helper.fetcher.FetchPictureHelper;
import jp.co.hands.hunting.controller.BaseController;
import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLine;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLineId;
import jp.co.hands.hunting.repository.impl.HuntingTimeLineRepository;
import lombok.Getter;
import lombok.Setter;

@Named(value="timeLineController")
@ManagedBean(name="timeLineController")
@SessionScoped
public class TimeLineController extends BaseController {

	@Inject
	private HuntingTimeLineRepository huntingTimeLineRepository;
	
	@Getter
	@Setter
	private HuntingModel huntingModel;

	@Getter
	@Setter
	private HuntingTimeLine huntingTimeLine;
	
	@Getter
	@Setter
	private List<HuntingTimeLine> huntingTimeLineList;
	
	
	
	public String moveToTimeLinePage() {

		huntingModel = JsfManagedObjectFetcher.getObject(HuntingModel.class, "model");
		if (Optional.ofNullable(huntingModel).isPresent()) {
			return redirectTo("/huntingTimeLine");
		}
		addMessage(FacesMessage.SEVERITY_ERROR, "", "選択が上手く出来ていません。");
		return null;
	}

	/**
	 * 画像をレンダリングするメソッド(DBより取得した画像データのバイナリーをStreamedContentに変換して返す)
	 * 
	 * @param return
	 *            ds: DBから取得した画像データ
	 */
	public StreamedContent getConvertPic() {

		HuntingTimeLineId targetHuntingTimeLineId = HuntingTimeLineId.builder().build();
		HuntingTimeLine targetHuntingTimeLine = HuntingTimeLine.builder().build();

		System.out.println("ちゃんと取れている1？");
		targetHuntingTimeLineId = JsfManagedObjectFetcher.getObject(HuntingTimeLineId.class, "igTimeLine");
		System.out.println("ちゃんと取れている2？"+targetHuntingTimeLineId.getUserId());
		if (!Optional.ofNullable(targetHuntingTimeLine).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "うまく選択ができていません。");
			return null;
		}
		
		targetHuntingTimeLine = huntingTimeLineRepository.findByKey(targetHuntingTimeLineId);				
		return 	FetchPictureHelper.getConvertPic(targetHuntingTimeLine.getTimeLineImage());

	}

}
