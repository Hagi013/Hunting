package jp.co.hands.hunting.entity.model.impl;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jp.co.hands.hunting.entity.model.BaseEntity_;

@Generated(value="Dali", date="2016-08-11T18:07:14.867+0900")
@StaticMetamodel(HuntingGoods.class)
public class HuntingGoods_ extends BaseEntity_ {
	public static volatile SingularAttribute<HuntingGoods, Long> goodsId;
	public static volatile SingularAttribute<HuntingGoods, String> goodsName;
	public static volatile SingularAttribute<HuntingGoods, Integer> goodsPrice;
	public static volatile SingularAttribute<HuntingGoods, String> goodsUrl;
	public static volatile SingularAttribute<HuntingGoods, Integer> goodsLikes;
	public static volatile SingularAttribute<HuntingGoods, HuntingTimeLine> huntingTimeLine;
	public static volatile ListAttribute<HuntingGoods, HuntingGoodsImage> huntingGoodsImages;
}
