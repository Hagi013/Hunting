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
import jp.co.hands.hunting.entity.model.impl.HuntingGoods;
import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLine;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLineId;
import jp.co.hands.hunting.repository.impl.HuntingGoodsRepository;
import jp.co.hands.hunting.repository.impl.HuntingTimeLineRepository;
import lombok.Getter;
import lombok.Setter;

@Named(value = "timeLineController")
@ManagedBean(name = "timeLineController")
@SessionScoped
public class TimeLineController extends BaseController {

	@Inject
	private HuntingTimeLineRepository huntingTimeLineRepository;
	
	@Inject
	private HuntingGoodsRepository huntingGoodsRepository;

	@Getter @Setter
	private HuntingModel huntingModel;
	
	
	
	/**
	 * タイムラインページへの遷移メソッド
	 * @param targetModel model選択ページで指定されたmodelインスタンス
	 * @return　string huntingTimeLineページ　
	 */
	public String moveToTimeLinePage(HuntingModel targetModel) {

		huntingModel = targetModel;
		if (Optional.ofNullable(huntingModel).isPresent()) {
			return redirectTo("/huntingTimeLine");
		}
		addMessage(FacesMessage.SEVERITY_ERROR, "", "選択が上手く出来ていません。");
		return null;
	}

	/**
	 * タイムライン画像をレンダリングするメソッド(DBより取得した画像データのバイナリーをStreamedContentに変換して返す)
	 * 
	 * @return　DBから取得した画像データ
	 */
	public StreamedContent getConvertTimeLineImg() {
		
		FacesContext con = FacesContext.getCurrentInstance();
		if(con.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		}			
		String userId = JsfManagedObjectFetcher.getString("igModelId");
		String timeLineId = JsfManagedObjectFetcher.getString("igTimeLineId");
		HuntingTimeLineId huntingTimeLineId = HuntingTimeLineId.builder().userId(userId).timeLineId(timeLineId).build();		
		HuntingTimeLine targetHuntingTimeLine = huntingTimeLineRepository.findByKey(huntingTimeLineId);
		
		return new DefaultStreamedContent(new ByteArrayInputStream(targetHuntingTimeLine.getTimeLineImage()));
	}

	
	/**
	 * 商品画像をレンダリングするメソッド(DBより取得した画像データのバイナリーをStreamedContentに変換して返す) 
	 * @return　DBから取得した画像データ
	 */
	public StreamedContent getConvertGoodsImage() {
		
		FacesContext con = FacesContext.getCurrentInstance();
		if(con.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		}
		if(Optional.ofNullable(JsfManagedObjectFetcher.getString("igGoodsId")).isPresent()) {
			long goodsId = Long.parseLong(JsfManagedObjectFetcher.getString("igGoodsId"));
			HuntingGoods targetGoods = huntingGoodsRepository.findByKey(goodsId);
			System.out.println("targetGoods:   "+targetGoods.getHuntingGoodsImages());		
			if(Optional.ofNullable(targetGoods.getHuntingGoodsImages()).isPresent() && 
					targetGoods.getHuntingGoodsImages().size() > 0) {
				byte[] targetGoodsImage = targetGoods.getHuntingGoodsImages().get(0).getGoodsImageData();
				return new DefaultStreamedContent(new ByteArrayInputStream(targetGoodsImage));
			}
		}	
		return new DefaultStreamedContent();
	}
	
}
