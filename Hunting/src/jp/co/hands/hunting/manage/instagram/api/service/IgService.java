package jp.co.hands.hunting.manage.instagram.api.service;

import java.io.Serializable;
import java.util.List;

import jp.co.hands.hunting.manage.instagram.api.users.entity.IgModelInfo;

public interface IgService extends Serializable {
	
	public IgModelInfo recieveIgModel(String id);
}
