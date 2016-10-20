package jp.co.hands.hunting.entity.model.impl;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jp.co.hands.hunting.entity.model.BaseEntity_;

@Generated(value="Dali", date="2016-10-18T01:13:27.746+0900")
@StaticMetamodel(HuntingTimeLine.class)
public class HuntingTimeLine_ extends BaseEntity_ {
	public static volatile SingularAttribute<HuntingTimeLine, HuntingTimeLineId> huntingTimeLineId;
	public static volatile SingularAttribute<HuntingTimeLine, String> link;
	public static volatile SingularAttribute<HuntingTimeLine, byte[]> timeLineImage;
	public static volatile SingularAttribute<HuntingTimeLine, String> timeLineImageUrl;
	public static volatile SingularAttribute<HuntingTimeLine, String> createdTime;
	public static volatile SingularAttribute<HuntingTimeLine, String> text;
	public static volatile SingularAttribute<HuntingTimeLine, HuntingModel> huntingModel;
	public static volatile ListAttribute<HuntingTimeLine, HuntingGoods> huntingGoodsList;
}
