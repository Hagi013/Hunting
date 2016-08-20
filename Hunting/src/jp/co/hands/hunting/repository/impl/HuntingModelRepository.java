package jp.co.hands.hunting.repository.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.repository.JpaDaoSupport;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class HuntingModelRepository extends JpaDaoSupport<HuntingModel, String> {
	
	public HuntingModelRepository() {
		super(HuntingModel.class);
	}

}
