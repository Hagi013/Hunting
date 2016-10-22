package jp.co.hands.hunting.manage.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import jp.co.hands.hunting.application.helper.JsfManagedObjectFetcher;
import jp.co.hands.hunting.controller.BaseController;
import jp.co.hands.hunting.entity.model.impl.HuntingGoods;
import jp.co.hands.hunting.entity.model.impl.HuntingGoodsImage;
import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLine;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLineId;
import jp.co.hands.hunting.helper.fetcher.FetchPictureHelper;
import jp.co.hands.hunting.manage.helper.UploadFileHundler;
import jp.co.hands.hunting.repository.impl.HuntingGoodsRepository;
import jp.co.hands.hunting.repository.impl.HuntingModelRepository;
import jp.co.hands.hunting.repository.impl.HuntingTimeLineRepository;
import lombok.Getter;
import lombok.Setter;

@Named(value = "adminRegistController")
@ManagedBean(name = "adminRegistController")
@SessionScoped
public class AdminRegistController extends BaseController {

	@Inject
	private HuntingModelRepository huntingModelRepository;

	@Inject
	private HuntingTimeLineRepository huntingTimeLineRepository;

	@Inject
	private HuntingGoodsRepository huntingGoodsRepository;

	@Getter
	@Setter
	private HuntingModel huntingModel;

	@Getter
	@Setter
	private List<HuntingModel> huntingModelList;

	@Getter
	@Setter
	private HuntingTimeLine huntingTimeLine;

	@Getter
	@Setter
	private List<HuntingTimeLine> huntingTimeLineList;

	@Getter
	@Setter
	private HuntingGoods huntingGoods;

	@Getter
	@Setter
	private HuntingGoodsImage huntingGoodsImage;

	@Getter
	@Setter
	private List<HuntingGoodsImage> huntingGoodsImageList;

	@Getter
	@Setter
	private Part uploadedFile;

	@Getter
	@Setter
	private String goodsImageUrl;
	
	/** method area */

	/**
	 * モデルの登録ページへ移動
	 */
	public String gotoModelRegister() {

		this.huntingModel = HuntingModel.builder().build();
		return redirectTo("/registerIGModel");
	}

	/**
	 * タイムラインの登録前のモデル選択ページへ移動
	 */
	public String gotoTimeLineRegister() {
		this.huntingModelList = huntingModelRepository.findAll();
		this.huntingTimeLine = HuntingTimeLine.builder().huntingTimeLineId(HuntingTimeLineId.builder().build()).build();
		return redirectTo("/selectModelBeforeRegisterTimeLine");
	}

	/**
	 * 商品登録ページへ移動
	 */
	public String gotoGoodsRegister() {

		this.huntingModelList = huntingModelRepository.findAll();
		return redirectTo("/selectModelBeforeRegisterGoods");
	}

	/**
	 * Instagram のユーザを登録する
	 *
	 */
	public void registerModel() {

		HuntingModel targetModel = this.huntingModel;

		// 不要となる予定
		Part uploadedFile = this.uploadedFile;

		if (this.huntingModel.getUserId() == null) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "UserIdを入力してください。");
			System.out.println("UserId" + this.huntingModel.getUserId());
			return;
		}
		
		// 不要となる予定
		if (Optional.ofNullable(uploadedFile).isPresent()) {
			targetModel.setProfilePicture(UploadFileHundler.fileHundle(uploadedFile));
			System.out.println("変えました");
			// targetModel = uploadFileHundle(targetModel, uploadedFile);
		}

		// 新規同ＩＤにてデータが登録されていた場合
		if (!Optional.ofNullable(huntingModelRepository.findByKey(targetModel.getUserId())).isPresent()) {

			// 表示名が入力されていなければ入力してもらう。
			if (targetModel.getDisplayName() == null || targetModel.getDisplayName().isEmpty()) {
				addMessage(FacesMessage.SEVERITY_ERROR, "", "表示名を入力してください。");
				return;
			}
			huntingModelRepository.save(targetModel, targetModel.getUserId());
			addMessage(FacesMessage.SEVERITY_INFO, "", "正常に登録が完了しました。");
		} else {
			// すでにIDが登録されていた場合
			huntingModelRepository.updata(targetModel, targetModel.getUserId());
			addMessage(FacesMessage.SEVERITY_INFO, "", "データの更新が完了しました。");
		}
	}

	/**
	 * タイムラインの登録ページへ遷移する
	 * @param targetModel タイムラインを表示するモデル
	 * @return pageURL
	*/
	public String moveToRegisterTimeLinePage(HuntingModel targetModel) {
		
		if(!Optional.ofNullable(targetModel.getUserId()).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "もう一度モデルを選択してください。");
		}
		
		String userId = targetModel.getUserId();
		this.huntingTimeLineList = huntingTimeLineRepository.getByModelId(userId);
		this.huntingTimeLine.getHuntingTimeLineId().setUserId(userId);
		return redirectTo("/registerIGModelTimeLine");
	}
	
	
	/**
	 * Instagram ユーザのタイムラインを新規登録する
	 *
	 */
	public void registerTimeLine() {

		HuntingTimeLine huntingTimeLine = this.huntingTimeLine;
		
		// ユーザIDがセットされていなかった場合
		if (!Optional.ofNullable(huntingTimeLine.getHuntingTimeLineId().getUserId()).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "ユーザIDを入力してください。");
			return;
		}
		
		// タイムラインIDがセットされていなかった場合
		if (!Optional.ofNullable(huntingTimeLine.getHuntingTimeLineId().getTimeLineId()).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "タイムラインIDを入力してください。");
			return;
		}
		
		// タイムライン画像のURLがセットされていなかった場合
		if (!Optional.ofNullable(huntingTimeLine.getTimeLineImageUrl()).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "timeline画像のURLを入力してください。");
			return;
		}
		
		//　不要となる予定
		Part uploadedFile = this.uploadedFile;
		if (Optional.ofNullable(uploadedFile).isPresent()) {
			huntingTimeLine.setTimeLineImage(UploadFileHundler.fileHundle(uploadedFile));
		}
		
		huntingTimeLineRepository.save(huntingTimeLine, huntingTimeLine.getHuntingTimeLineId());

		addMessage(FacesMessage.SEVERITY_INFO, "", "登録が完了しました。");

	}

	
	/**
	 * モデルの画像を取得する
	 * 
	 */
	/*public StreamedContent getConvertModelImg() {

		FacesContext con = FacesContext.getCurrentInstance();
		if (con.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		}
		String userId = JsfManagedObjectFetcher.getString("modelIdInAdmin");

		return new DefaultStreamedContent(
				new ByteArrayInputStream(huntingModelRepository.findByKey(userId).getProfilePicture()));
	}*/

	/**
	 * タイムラインの選択画面へ遷移する。
	 * 
	 */
	public String moveToTimeLinePage(HuntingModel selectedModel) {

		if (Optional.ofNullable(selectedModel).isPresent()) {
			this.huntingTimeLineList = huntingTimeLineRepository.getByModelId(selectedModel.getUserId());
			return redirectTo("/selectTimeLineBeforeRegisterGoods");
		}
		addMessage(FacesMessage.SEVERITY_ERROR, "", "もう一度モデルを選択してください。");
		return null;
	}

	/**
	 * モデルのタイムラインの画像を取得する
	 * 
	 */
	/*public StreamedContent getConvertTimeLineImg() {

		FacesContext con = FacesContext.getCurrentInstance();

		if (con.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		}
		String userId = JsfManagedObjectFetcher.getString("modelIdAdmin");
		String timeLineId = JsfManagedObjectFetcher.getString("timeLineIdAdmin");
		HuntingTimeLineId huntingTimeLineId = HuntingTimeLineId.builder().userId(userId).timeLineId(timeLineId).build();
		HuntingTimeLine huntingTimeLine = huntingTimeLineRepository.findByKey(huntingTimeLineId);

		return new DefaultStreamedContent(new ByteArrayInputStream(huntingTimeLine.getTimeLineImage()));
	}*/

	/**
	 * 商品の登録ページへ移動する。
	 * 
	 */
	public String moveToRegisterTimeLineGoods(HuntingTimeLine huntingTimeLine) {

		if (Optional.ofNullable(huntingTimeLine).isPresent()) {
			this.huntingTimeLine = huntingTimeLine;
			this.huntingGoods = HuntingGoods.builder().huntingTimeLine(huntingTimeLine).build();
			this.huntingGoodsImage = HuntingGoodsImage.builder().build();
			this.huntingGoodsImageList = new ArrayList<>();
			return redirectTo("/registerTimeLineGoods");
		}

		addMessage(FacesMessage.SEVERITY_ERROR, "", "もう一度タイムラインを選択し直してください。");
		return null;

	}

	/**
	 * 商品を登録する
	 *
	 */
	public void registerGoods() {

		HuntingGoods huntingGoods = this.huntingGoods;
		HuntingGoodsImage huntingGoodsImage = this.huntingGoodsImage;
		List<HuntingGoodsImage> huntingGoodsImageList = this.huntingGoodsImageList;

		// ありえないがhuntingGoodsインスタンスが存在していることを確認 ⇒ なければタイムラインの選択ページへ戻って再選択してもらう。
		if (!Optional.ofNullable(huntingGoods).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "前のページへ戻り、再度タイムラインを選択してください。");
			return;
		}

		// 画像があるかチェック ⇒ なければ画像を入れてもらう。
		if (!Optional.ofNullable(this.uploadedFile).isPresent() &&
				!Optional.ofNullable(goodsImageUrl).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "画像もしくは画像のURLをいれてください");
			return;
		}

		// 画像をbyte配列へ変換し、登録予定のhuntingGoodsへセットする。
		if(Optional.ofNullable(this.uploadedFile).isPresent()) {
			huntingGoodsImage.setGoodsImageData(UploadFileHundler.fileHundle(this.uploadedFile));
		}
		
		// 画像のURLを登録予定のhuntingGoodsへセットする。
		if(Optional.ofNullable(goodsImageUrl).isPresent()) {
			huntingGoodsImage.setGoodsImageUrl(goodsImageUrl);
		}
	
		huntingGoodsImageList.add(huntingGoodsImage);
		huntingGoods.setHuntingGoodsImages(huntingGoodsImageList);

		String userId = huntingGoods.getHuntingTimeLine().getHuntingTimeLineId().getUserId();
		System.out.println("登録前userId；     " + userId);
		String timeLineId = huntingGoods.getHuntingTimeLine().getHuntingTimeLineId().getTimeLineId();
		System.out.println("登録前timeLineId；     " + timeLineId);
		String url = huntingGoods.getGoodsUrl();

		// userIdとtimelineIdをキーにしてすでに登録されている商品のリストを取得
		List<HuntingGoods> cpList = huntingGoodsRepository.fetchGoodsByUserAndTimeLine(userId, timeLineId);

		// DBに選択したタイムラインに関する商品が登録されていない場合の処理。
		if (!Optional.ofNullable(cpList).isPresent()) {
			huntingGoodsRepository.save(huntingGoods);
			addMessage(FacesMessage.SEVERITY_INFO, "", "商品の登録が完了しました。");
			return;
		}

		// DBに選択したタイムラインに関する商品が登録されていた場合、すでに登録されているURLではないか確認。
		for (HuntingGoods compareGoods : cpList) {
			if (compareGoods.getGoodsUrl().equals(url)) {
				addMessage(FacesMessage.SEVERITY_ERROR, "", "すでに同じURLが登録されています。");
				return;
			}
		}
		huntingGoodsRepository.save(huntingGoods);
		addMessage(FacesMessage.SEVERITY_INFO, "", "商品の登録が完了しました。");

	}

}
