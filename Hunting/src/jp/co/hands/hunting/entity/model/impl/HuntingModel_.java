package jp.co.hands.hunting.entity.model.impl;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jp.co.hands.hunting.entity.model.BaseEntity_;

@Generated(value="Dali", date="2016-09-29T02:26:28.098+0900")
@StaticMetamodel(HuntingModel.class)
public class HuntingModel_ extends BaseEntity_ {
	public static volatile SingularAttribute<HuntingModel, String> userId;
	public static volatile SingularAttribute<HuntingModel, String> userName;
	public static volatile SingularAttribute<HuntingModel, String> userFirstName;
	public static volatile SingularAttribute<HuntingModel, byte[]> profilePicture;
	public static volatile SingularAttribute<HuntingModel, String> userLastName;
	public static volatile SingularAttribute<HuntingModel, String> displayName;
	public static volatile ListAttribute<HuntingModel, HuntingTimeLine> huntingTimeLines;
}
