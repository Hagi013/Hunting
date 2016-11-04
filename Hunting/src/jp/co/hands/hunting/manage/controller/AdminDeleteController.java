package jp.co.hands.hunting.manage.controller;

import java.util.Optional;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import jp.co.hands.hunting.controller.BaseController;
import jp.co.hands.hunting.entity.model.impl.HuntingGoods;
import jp.co.hands.hunting.entity.model.impl.HuntingGoodsImage;
import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLine;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLineId;
import jp.co.hands.hunting.repository.impl.HuntingGoodsImageRepository;
import jp.co.hands.hunting.repository.impl.HuntingGoodsRepository;
import jp.co.hands.hunting.repository.impl.HuntingModelRepository;
import jp.co.hands.hunting.repository.impl.HuntingTimeLineRepository;

@Named(value="adminDeleteController")
@ManagedBean(name="adminDeleteController")
@SessionScoped
public class AdminDeleteController extends BaseController{
	
	@Inject
	private HuntingModelRepository huntingModelRepository;
	
	@Inject 
	private HuntingTimeLineRepository huntingTimeLineRepository;
	
	@Inject
	private HuntingGoodsRepository huntingGoodsRepository;
	
	@Inject
	private HuntingGoodsImageRepository huntingGoodsImageRepository;
	
	/**
	 * モデルの削除を実行する
	 * 
	*/
	public void deleteModel(HuntingModel targetModel) {
		
		// 渡された削除対象がNullでないことを確認
		if(!Optional.ofNullable(targetModel.getUserId()).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "削除対象をもう一度選択してください。");
			return;
		}
		
		String targetId = targetModel.getUserId();
		
		// 削除対象がDBに存在していることを確認
		if(!Optional.ofNullable(huntingModelRepository.findByKey(targetId)).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "すでにDB上から削除されています。");
			return;
		}
		
		//削除の実行
		huntingModelRepository.delete(targetId);
		addMessage(FacesMessage.SEVERITY_INFO, "", "正常に削除が完了しました。");
	}
	
	/**
	 * タイムラインの削除を実行する
	 * 
	*/
	public void deleteTimeLine(HuntingTimeLine targetTimeLine) {
		
		// 渡された削除対象がNullでないことを確認
		if(!Optional.ofNullable(targetTimeLine.getHuntingTimeLineId()).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "削除対象をもう一度選択してください。");
			return;
		}
		
		HuntingTimeLineId targetId = targetTimeLine.getHuntingTimeLineId();
		
		// 対象がDBに存在しているか確認
		if(!Optional.ofNullable(huntingTimeLineRepository.findByKey(targetId)).isPresent()) {
			addMessage(FacesMessage.SEVERITY_WARN, "", "すでにDB上から削除されています。");
			return;
		}
		
		// 削除の実行
		huntingTimeLineRepository.delete(targetId);
		addMessage(FacesMessage.SEVERITY_INFO, "", "正常に削除が完了しました。");
	}	
	
	/**
	 * 商品関連（商品画像　⇒　商品画像が0枚になれば商品）の削除を実行する
	 * 
	*/
	public void deleteRelateToGoods(HuntingGoodsImage targetGoodsImage) {
		
		System.out.println(targetGoodsImage.getGoodsImageId());
		
		//HuntingGoodsImage targetGoodsImage = huntingGoodsImageRepository.findByKey(targetGoodsImageId);
		
		if(!Optional.ofNullable(targetGoodsImage.getHuntingGoods().getGoodsId()).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "システム管理者へ問合わせてください。");
			return;
		}
		
		deleteGoodsImage(targetGoodsImage);
		
		long goodsId = targetGoodsImage.getHuntingGoods().getGoodsId();
		System.out.println("goodsIdどうなっているのか？？:  "+goodsId);
		// 商品画像イメージが0であれば商品を削除する
		if(huntingGoodsImageRepository.countGoodsImage(goodsId) == 0) {
			System.out.println("ここまで来ているか？:  "+goodsId);
			System.out.println("これはどうですか？:   "+huntingGoodsRepository.findByKey(goodsId));
			deleteGoods(huntingGoodsRepository.findByKey(goodsId));
			addMessage(FacesMessage.SEVERITY_INFO, "", "画像イメージが0枚のため、商品を削除しました。");
			return;
		}
		/* return redirectTo("/registerTimeLineGoods")*/;
		
	}
	
		
	/**
	 * 商品イメージの削除を実行する
	 * 
	*/
	private void deleteGoodsImage(HuntingGoodsImage targetGoodsImage) {
		
		// 渡された削除対象がNullでないことを確認
		if(!Optional.ofNullable(targetGoodsImage.getGoodsImageId()).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "削除対象をもう一度選択してください。");
			return;
		}
		
		long targetId = targetGoodsImage.getGoodsImageId();
		
		// 対象がDBに存在しているか確認
		if(!Optional.ofNullable(huntingGoodsImageRepository.findByKey(targetId)).isPresent()) {
			addMessage(FacesMessage.SEVERITY_WARN, "", "すでにDB上から削除されています。");
			return;
		}
		
		// 削除の実行
		huntingGoodsImageRepository.delete(targetId);
				
		addMessage(FacesMessage.SEVERITY_INFO, "", "正常に商品画像の削除が完了しました。");
		
	}
	
	
	/**
	 * 商品の削除を実行する
	 * 
	*/
	private void deleteGoods(HuntingGoods targetGoods) {
				
		// 渡された削除対象がNullでないことを確認
		if(!Optional.ofNullable(targetGoods.getGoodsId()).isPresent()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "", "削除対象の商品がNullです。");
			return;
		}
		
		long targetId = targetGoods.getGoodsId();
		
		// 対象がDBに存在しているか確認
		if(!Optional.ofNullable(huntingGoodsRepository.findByKey(targetId)).isPresent()) {
			addMessage(FacesMessage.SEVERITY_WARN, "", "すでに商品はDB上から削除されています。");
			return;
		}

		System.out.println("ここには来ている3    " + targetId);
		// 削除の実行
		huntingGoodsRepository.delete(targetId);
		addMessage(FacesMessage.SEVERITY_INFO, "", "正常に商品の削除が完了しました。");
	}

	
}
