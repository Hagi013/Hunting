package jp.co.hands.hunting.manage.instagram.api.users.service;

import java.util.List;

import jp.co.hands.hunting.manage.instagram.api.users.entity.IgModelInfo;

public interface IgModelService {
	
	public List<IgModelInfo> recieveIgModel(String accessToken, List<String> ids);
	
	public IgModelInfo recieveIgModel(String accessToken, String id);
}
