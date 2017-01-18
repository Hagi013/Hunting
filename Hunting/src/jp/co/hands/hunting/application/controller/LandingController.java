package jp.co.hands.hunting.application.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import jp.co.hands.hunting.application.domain.HuntingLandingBl;
import jp.co.hands.hunting.controller.BaseController;
import jp.co.hands.hunting.entity.model.impl.HuntingModel;

@Named(value="landingController")
@ManagedBean(name="landingController")
@SessionScoped
public class LandingController extends BaseController {
	
	@Inject
	private HuntingLandingBl huntingLandingBl;

	/**
	 * LandingPageのモデルを取得(Hunting-Index.yamlに記載)
	 * @return  モデルリスト
	*/
	public List<HuntingModel> getSelectedModel() {
		
		return huntingLandingBl.getSelectedModel();
	}

}
