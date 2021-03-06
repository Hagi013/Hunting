package jp.co.hands.hunting.entity.model.impl;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import jp.co.hands.hunting.entity.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude="huntingModel")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name="HuntinTimeLine")
@Table(name="hunting_timeline")
public class HuntingTimeLine extends BaseEntity {

	@EmbeddedId @Getter @Setter
	private HuntingTimeLineId huntingTimeLineId;
	
	@Lob
	@Column(name="link")
	private String link;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="timeline_image")
	private byte[] timeLineImage;
	
	@Lob
	@Column(name="timeline_image_url")
	private String timeLineImageUrl;
	
	@Column(name="created_time")
	private String createdTime;

	@Column(name="text")
	private String text;
		
	@ManyToOne(targetEntity=HuntingModel.class)
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private HuntingModel huntingModel;
	
	@OneToMany(targetEntity = HuntingGoods.class, mappedBy="huntingTimeLine", cascade=CascadeType.ALL)
	@JoinColumn(name="goods_id")
	@OrderBy("registeredDateTime DESC")
	private List<HuntingGoods> huntingGoodsList;

}
