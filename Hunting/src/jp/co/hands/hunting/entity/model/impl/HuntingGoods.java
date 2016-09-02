package jp.co.hands.hunting.entity.model.impl;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import jp.co.hands.hunting.entity.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name="HuntingGoods")
@Table(name="hunting_goods")
public class HuntingGoods extends BaseEntity {

	@Id
	@Column(name="goods_id")
	@SequenceGenerator(name="hunting.hunting_goods_goods_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hunting.hunting_goods_goods_id_seq")
	private long goodsId;
	
	@Column(name="goods_name")
	private String goodsName;
	
	@Column(name="goods_price")
	private int goodsPrice;
	
	@Column(name="goods_url")
	private String goodsUrl;
	
	@Column(name="goods_like")
	private int goodsLikes;
	
	@ManyToOne(targetEntity=HuntingTimeLine.class)
	@JoinColumns(value={
			@JoinColumn(name="user_id", referencedColumnName="user_id"),
			@JoinColumn(name="timeline_id", referencedColumnName="timeline_id")})
	private HuntingTimeLine huntingTimeLine;	
	
	@OneToMany(targetEntity=HuntingGoodsImage.class, cascade=CascadeType.PERSIST, mappedBy="huntingGoods" )
	@JoinColumn(name="goods_image_id")
	private List<HuntingGoodsImage> huntingGoodsImages;
	
}
