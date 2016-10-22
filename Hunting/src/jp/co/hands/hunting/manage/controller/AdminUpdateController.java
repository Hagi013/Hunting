package jp.co.hands.hunting.manage.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import jp.co.hands.hunting.controller.BaseController;
import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLine;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLineId;
import jp.co.hands.hunting.repository.impl.HuntingTimeLineRepository;
import jp.co.hands.hunting.repository.impl.HuntingModelRepository;
import lombok.Getter;
import lombok.Setter;

@Named("adminUpdateController")
@ManagedBean(name="adminUpdateController")
@SessionScoped
public class AdminUpdateController extends BaseController {
	
	@Inject
	private HuntingModelRepository huntingModelRepository;
	
	@Inject
	private  HuntingTimeLineRepository huntingTimeLineRepository;
	
	@Getter @Setter
	private List<HuntingModel> huntingModelList;
	
	@Getter @Setter
	private HuntingModel targetModel;
	
	@Getter @Setter
	private HuntingTimeLine targetTimeLine;
	
	
	@PostConstruct
	public void init() {
		
		this.huntingModelList = huntingModelRepository.findAll();
	}
	
	
	public String moveToUpdateModel(HuntingModel targetModel) {
		
		this.targetModel = targetModel;
		return redirectTo("/updateIGModel");
		
	}
	
	/**
	 * モデルデータの更新
	 * 
	*/
	public void updateModel() {
		
		// 更新できる状態になっているか確認（targetModelが問題なく保持されているか）
		if(!Optional.ofNullable(targetModel.getUserId()).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "もう一度最初からやり直してください。");
			return;
		}
		
		String userId = targetModel.getUserId();
		
		// DB内にデータがそもそも存在している?
		if(!Optional.ofNullable(huntingModelRepository.findByKey(userId)).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "すでにデータが削除されています。");
		}
		
		// データの更新
		huntingModelRepository.updata(targetModel, userId);		
		addMessage(FacesMessage.SEVERITY_INFO, "", "更新が完了しました。");
		
	}
	
	/**
	 * タイムラインの更新画面へ遷移
	 * 
	*/
	public String moveToUpdateTimeLine(HuntingTimeLine targetTimeLine) {
		
		if(!Optional.ofNullable(targetTimeLine.getHuntingTimeLineId().getUserId()).isPresent() &&
				Optional.ofNullable(targetTimeLine.getHuntingTimeLineId().getTimeLineId()).isPresent()) {
			
			addMessage(FacesMessage.SEVERITY_ERROR, "", "もう一度モデルを選択してください。");
			return null;
		}
		
		this.targetTimeLine = targetTimeLine;
		return redirectTo("/updateIGModelTimeLine");
	}

	/**
	 * タイムラインを更新する。
	 * 
	*/
	public void updateTimeLine() {
		
		// 更新する対象のタイムラインが存在しているかどうか
		if(!Optional.ofNullable(targetTimeLine.getHuntingTimeLineId().getUserId()).isPresent() &&
				Optional.ofNullable(targetTimeLine.getHuntingTimeLineId().getTimeLineId()).isPresent()) {
			
			addMessage(FacesMessage.SEVERITY_ERROR, "", "もう一度タイムラインを選択してください");
			return;
		}
		
		HuntingTimeLineId huntingTimeLineId = targetTimeLine.getHuntingTimeLineId();
		
		// 選択したタイムラインのユーザはDBにそんざいするかどうか
		if(!Optional.ofNullable(huntingModelRepository.findByKey(huntingTimeLineId.getUserId())).isPresent()) {
			
			addMessage(FacesMessage.SEVERITY_ERROR, "", "モデルが削除されています");
			return;
		}
		
		// 選択したタイムラインはDBに存在するかどうか
		if(!Optional.ofNullable(huntingTimeLineRepository.findByKey(huntingTimeLineId)).isPresent()) {
			
			addMessage(FacesMessage.SEVERITY_ERROR, "", "タイムラインはDBに存在しないため、新規登録してください");
			return;
		}
		
		huntingTimeLineRepository.updata(targetTimeLine, huntingTimeLineId);
		addMessage(FacesMessage.SEVERITY_INFO, "", "正常に更新が完了しました。");
		return;
	}
	
	
	
}
