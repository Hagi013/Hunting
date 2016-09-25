package jp.co.hands.hunting.repository.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import jp.co.hands.hunting.entity.model.impl.HuntingGoodsImage;
import jp.co.hands.hunting.repository.JpaDaoSupport;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class HuntingGoodsImageRepository extends JpaDaoSupport<HuntingGoodsImage, Long> {
	
	public HuntingGoodsImageRepository() {
		super(HuntingGoodsImage.class);
	}
}
