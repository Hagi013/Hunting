package jp.co.hands.hunting.entity.model.impl;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jp.co.hands.hunting.entity.model.BaseEntity_;

@Generated(value="Dali", date="2017-01-01T00:12:48.990+0900")
@StaticMetamodel(HuntingModel.class)
public class HuntingModel_ extends BaseEntity_ {
	public static volatile SingularAttribute<HuntingModel, String> userId;
	public static volatile SingularAttribute<HuntingModel, String> userName;
	public static volatile SingularAttribute<HuntingModel, String> userFirstName;
	public static volatile SingularAttribute<HuntingModel, byte[]> profilePicture;
	public static volatile SingularAttribute<HuntingModel, String> profilePictureUrl;
	public static volatile SingularAttribute<HuntingModel, String> userLastName;
	public static volatile SingularAttribute<HuntingModel, String> displayName;
	public static volatile ListAttribute<HuntingModel, HuntingTimeLine> huntingTimeLines;
}
