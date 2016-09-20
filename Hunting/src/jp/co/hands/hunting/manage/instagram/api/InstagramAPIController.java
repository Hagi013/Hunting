package jp.co.hands.hunting.manage.instagram.api;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import jp.co.hands.hunting.controller.BaseController;
import jp.co.hands.hunting.manage.instagram.api.oauth.service.IGService;
import jp.co.hands.hunting.manage.instagram.api.oauth.service.impl.IGServiceImpl;

@ManagedBean(name="instagramAPIController")
@Named("instagramAPIController")
@SessionScoped
public class InstagramAPIController extends BaseController {
	
	private IGService igService;
	
	public void startInstagramAPI() {
		
		igService = new IGServiceImpl();
	}
	
}
