package jp.co.hands.hunting.manage.controller;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;


import jp.co.hands.hunting.controller.BaseController;
import jp.co.hands.hunting.entity.model.impl.HuntingGoods;
import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLine;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLineId;
import jp.co.hands.hunting.manage.helper.UploadFileHundler;
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
	
	@Getter @Setter
	private HuntingModel huntingModel;

	@Getter @Setter
	private HuntingTimeLine huntingTimeLine;

	@Getter @Setter
	private HuntingGoods huntingGoods;

	@Getter @Setter	
	private Part uploadedFile;
		
	
	
	@PostConstruct
	public void init() {
		
		this.huntingModel = HuntingModel.builder().build();
		this.huntingTimeLine = HuntingTimeLine.builder().huntingTimeLineId(HuntingTimeLineId.builder().build()).build();
		
	}
		
	
	/** method area */

	/**
	 * Instagram のユーザを登録する
	 *
	 */
	public void registerModel() {

		HuntingModel targetModel = this.huntingModel;
		
		System.out.println("uploadedFile"+uploadedFile);
		Part uploadedFile = this.uploadedFile;
		
		if(this.huntingModel.getUserId() == null) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "UserIdを入力してください。");
			System.out.println("UserId"+this.huntingModel.getUserId());
			return;
		}
		
		if(Optional.ofNullable(uploadedFile).isPresent()) {
			targetModel.setProfilePicture(UploadFileHundler.fileHundle(uploadedFile));
			System.out.println("変えました");
			// targetModel = uploadFileHundle(targetModel, uploadedFile);
		}
				
		if(!Optional.ofNullable(huntingModelRepository.findByKey(targetModel.getUserId())).isPresent()) {

			huntingModelRepository.save(targetModel, targetModel.getUserId());
			addMessage(FacesMessage.SEVERITY_INFO, "", "正常に登録が完了しました。");
		} else {
			huntingModelRepository.updata(targetModel, targetModel.getUserId());
			addMessage(FacesMessage.SEVERITY_INFO, "", "データの更新が完了しました。");			
		}
		
	}
	
	
	
	/*private HuntingModel uploadFileHundle(HuntingModel targetModel, Part target) {
				
		// byte型の配列を出力先とするクラス。
		// 通常、バイト出力ストリームはファイルやソケットを出力先とするが、
		// ByteArrayOutputStreamクラスはbyte[]変数、つまりメモリを出力先とする。
		ByteArrayOutputStream modelImageByteArray = new ByteArrayOutputStream();

		try {
			InputStream modelImage = target.getInputStream();
			int c;
			while ((c = modelImage.read()) != -1) {
				modelImageByteArray.write(c);
			}

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			if (modelImageByteArray != null) {

				try {
					modelImageByteArray.flush();
					modelImageByteArray.close();

				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}

		System.out.println(modelImageByteArray.toByteArray().toString());
		
		// 書き込み先はByteArrayOutputStreamクラス内部となる。
		// この書き込まれたバイトデータをbyte型配列として取り出す場合には、
		// toByteArray()メソッドを呼び出す。
		targetModel.setProfilePicture(modelImageByteArray.toByteArray());

		System.out.println("huntingModel:" + huntingModel.getProfilePicture());
				
		return targetModel;
	}*/

	
	/**
	 * Instagram ユーザのタイムラインを登録する
	 *
	 */	
	public void registerTimeLine() {
	
		HuntingTimeLine huntingTimeLine = this.huntingTimeLine;
		
		if(!Optional.ofNullable(huntingTimeLine.getHuntingTimeLineId().getUserId()).isPresent()) {			
			addMessage(FacesMessage.SEVERITY_ERROR, "", "ユーザIDを入力してください。");
			return;
		}
		
		if(!Optional.ofNullable(huntingTimeLine.getHuntingTimeLineId().getTimeLineId()).isPresent()) {			
			addMessage(FacesMessage.SEVERITY_ERROR, "", "タイムラインIDを入力してください。");
			return;
		}
		
		Part uploadedFile = this.uploadedFile;
		if(Optional.ofNullable(uploadedFile).isPresent()) {
			huntingTimeLine.setTimeLineImage(UploadFileHundler.fileHundle(uploadedFile));
		}
		
		huntingTimeLineRepository.save(huntingTimeLine, huntingTimeLine.getHuntingTimeLineId());
		
		addMessage(FacesMessage.SEVERITY_INFO, "", "登録が完了しました。");
		
	}
	
}
