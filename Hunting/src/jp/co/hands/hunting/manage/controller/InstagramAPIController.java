package jp.co.hands.hunting.manage.controller;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import jp.co.hands.hunting.controller.BaseController;
import jp.co.hands.hunting.manage.instagram.api.oauth.service.IGModelService;
import jp.co.hands.hunting.manage.instagram.api.oauth.service.impl.IGModelServiceImpl;

@ManagedBean(name="instagramAPIController")
@Named("instagramAPIController")
@SessionScoped
public class InstagramAPIController extends BaseController {
	
	private IGModelService igService;
	
	public void startInstagramAPI() {
		
		igService = new IGModelServiceImpl();
	}
	
}
