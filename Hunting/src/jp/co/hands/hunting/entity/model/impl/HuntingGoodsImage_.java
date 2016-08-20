package jp.co.hands.hunting.entity.model.impl;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jp.co.hands.hunting.entity.model.BaseEntity_;

@Generated(value="Dali", date="2016-08-11T18:07:14.907+0900")
@StaticMetamodel(HuntingGoodsImage.class)
public class HuntingGoodsImage_ extends BaseEntity_ {
	public static volatile SingularAttribute<HuntingGoodsImage, Long> goodsImageId;
	public static volatile SingularAttribute<HuntingGoodsImage, byte[]> goodsImageData;
	public static volatile SingularAttribute<HuntingGoodsImage, HuntingGoods> huntingGoods;
}
