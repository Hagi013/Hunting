package jp.co.hands.hunting.manage.instagram.api.oauth.service;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;

import jp.co.hands.hunting.manage.instagram.api.oauth.entity.AccessToken;
import jp.co.hands.hunting.manage.instagram.api.oauth.entity.Code;

public interface IGService {

	
	public AccessToken getAccessToken(String code);
	
	
	
}
