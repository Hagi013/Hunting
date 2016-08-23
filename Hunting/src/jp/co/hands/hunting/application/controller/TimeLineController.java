package jp.co.hands.hunting.application.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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

@Named(value = "timeLineController")
@ManagedBean(name = "timeLineController")
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

	
	@Getter
	@Setter
	private byte[] timeLineImage;
	
	
	public String moveToTimeLinePage(HuntingModel targetModel) {

		//huntingModel = JsfManagedObjectFetcher.getObject(HuntingModel.class, "model");
		System.out.println("userId;    "+targetModel.getUserId());
		huntingModel = targetModel;
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
	public StreamedContent getConvertTimeLineImg(HuntingTimeLineId targetHuntingTimeLineId) {

		System.out.println("targetHuntingTimeLineId     "+targetHuntingTimeLineId);
		if(targetHuntingTimeLineId == null) {
			return new DefaultStreamedContent(new ByteArrayInputStream(this.timeLineImage));
			//return new DefaultStreamedContent();
		}

		//HuntingTimeLine targetHuntingTimeLine = HuntingTimeLine.builder().build();
		//HuntingTimeLineId targetHuntingTimeLineId = JsfManagedObjectFetcher.<HuntingTimeLine>getObject(HuntingTimeLine.class,
		//		"timeLine").getHuntingTimeLineId();
		
		/*HuntingTimeLine targetHuntingTimeLine = JsfManagedObjectFetcher.<HuntingTimeLine>getObject(HuntingTimeLine.class,
						"timeLine");*/
		//System.out.println("ちゃんと取れている1234？" + targetHuntingTimeLineId);
		
		/*if (!Optional.ofNullable(targetHuntingTimeLineId).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "うまく選択ができていません。");
			return null;
		}*/
		
		HuntingTimeLine targetHuntingTimeLine = huntingTimeLineRepository.findByKey(targetHuntingTimeLineId);
		System.out.println("ちゃんと取れている1234？" + targetHuntingTimeLineId);
		System.out.println("ちゃんと取れている1235？" + targetHuntingTimeLine.getTimeLineImage());
		this.timeLineImage = targetHuntingTimeLine.getTimeLineImage();
		return FetchPictureHelper.getConvertPic(targetHuntingTimeLine.getTimeLineImage());

	}

}
