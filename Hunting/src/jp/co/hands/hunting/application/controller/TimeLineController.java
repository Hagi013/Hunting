package jp.co.hands.hunting.application.controller;

import java.io.ByteArrayInputStream;
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

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

import jp.co.hands.hunting.application.helper.JsfManagedObjectFetcher;
import jp.co.hands.hunting.controller.BaseController;
import jp.co.hands.hunting.entity.model.impl.HuntingGoods;
import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLine;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLineId;
import jp.co.hands.hunting.repository.impl.HuntingGoodsImageRepository;
import jp.co.hands.hunting.repository.impl.HuntingGoodsRepository;
import jp.co.hands.hunting.repository.impl.HuntingModelRepository;
import jp.co.hands.hunting.repository.impl.HuntingTimeLineRepository;
import lombok.Getter;
import lombok.Setter;

@Named(value = "timeLineController")
@ManagedBean(name = "timeLineController")
@SessionScoped
public class TimeLineController extends BaseController {

	@Inject
	private HuntingModelRepository huntingModelRepository;
	
	@Inject
	private HuntingTimeLineRepository huntingTimeLineRepository;
	
	@Inject
	private HuntingGoodsRepository huntingGoodsRepository;

	@Inject
	private HuntingGoodsImageRepository huntingGoodsImageRepository;
	
	@Getter @Setter
	private HuntingModel huntingModel;
	
	@Getter @Setter
	private String targetModelId;
	
	
	/**
	 * タイムラインページへの遷移メソッド
	 * @param  model選択ページで指定されたmodelインスタンス
	 * @return　huntingTimeLineページ　
	 */
	public String moveToTimeLinePage(HuntingModel targetModel) {

		huntingModel = targetModel;
		/*for(HuntingTimeLine  target : huntingModel.getHuntingTimeLines()) {
			System.out.println("タイムライン画像のURL"+ target.getTimeLineImageUrl());
		}*/
		if (Optional.ofNullable(huntingModel).isPresent()) {
			return redirectTo("/huntingTimeLine")+"&id=" + huntingModel.getUserId();
			//return "huntingTimeLine.xhtml?faces-redirect=true&id=" +huntingModel.getUserId();
		}
		addMessage(FacesMessage.SEVERITY_ERROR, "", "選択が上手く出来ていません。");
		return null;
	}

	/**
	 * PrettyFacesよりID指定のURLで入ってきた場合の処理
	 * @param モデルID
	 * 
	*/
	public void recieveDirectUrl() {
		
		if (!Optional.ofNullable(targetModelId).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "不正なURLです。");
			return;
		}
		
		if (!Optional.ofNullable(huntingModelRepository.findByKey(targetModelId)).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "存在しないmidです。");
			return;
		}
		
		huntingModel = huntingModelRepository.findByKey(targetModelId);
	
		if (Optional.ofNullable(huntingModel.getUserId()).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "モデルが存在しません。");
			return;
		}

		return;

	}	
	
	/**
	 * 状態を初期化してhuntingUser.xhtml画面に戻る
	 * @return パス	
	*/
	public String moveToLandingPage() {
		huntingModel = HuntingModel.builder().build();
		return redirectTo("/huntingUser");
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
	
	/**
	 * 商品画像すべてをレンダリングするメソッド(DBより取得した画像データのバイナリーをStreamedContentに変換して返す) 
	 * @return　DBから取得した画像データ
	 */	
	public StreamedContent getConvertGoodsIndivImg() {
		
		FacesContext con = FacesContext.getCurrentInstance();
		if(con.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		}
		
		long targetGoodsImgId = Long.parseLong(JsfManagedObjectFetcher.getString("igGoodsImageId")); 
		if(Optional.ofNullable(targetGoodsImgId).isPresent() && targetGoodsImgId != 0) {
			return new DefaultStreamedContent(new ByteArrayInputStream(huntingGoodsImageRepository.findByKey(targetGoodsImgId).getGoodsImageData()));
		}
		
		return new DefaultStreamedContent();
	}
	
	/**
	 * TimeLineの一番最新の画像をレンダリングするメソッド
	 * @param return ds: DBから取得した画像のURL
	*/	
	public String getLatestTimeLineImg(String userId) {
		System.out.println("取れているか？:  "+ userId);
		
		if(huntingTimeLineRepository.getLatestTimeLine(userId).size() == 0) {
			return null;
		}
		HuntingTimeLine huntingTimeLine = huntingTimeLineRepository.getLatestTimeLine(userId).get(0);
		
		// huntingTimeLineに値が入っていなかった場合
		if(!Optional.ofNullable(huntingTimeLine.getHuntingTimeLineId().getTimeLineId()).isPresent()) {
			System.out.println("TimeLineの画像取得に失敗");
			return null;
		}	
		System.out.println("確認：　　　　"+ huntingTimeLine.getHuntingTimeLineId().getTimeLineId());
		return  huntingTimeLine.getTimeLineImageUrl();
	}
	
}
