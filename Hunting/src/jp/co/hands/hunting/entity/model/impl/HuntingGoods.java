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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import jp.co.hands.hunting.entity.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude="huntingTimeLine")
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
	
	@Lob
	@Column(name="goods_url")
	private String goodsUrl;
	
	@Column(name="goods_like")
	private int goodsLikes;
	
	@ManyToOne(targetEntity=HuntingTimeLine.class)
	@JoinColumns(value={
			@JoinColumn(name="user_id", referencedColumnName="user_id", updatable=false),
			@JoinColumn(name="timeline_id", referencedColumnName="timeline_id", updatable=false)})
	private HuntingTimeLine huntingTimeLine;	
	
	@OneToMany(targetEntity=HuntingGoodsImage.class, cascade=CascadeType.ALL, mappedBy="huntingGoods" )
	@JoinColumn(name="goods_image_id")
	private List<HuntingGoodsImage> huntingGoodsImages;
	
	@PostPersist
	public void postPersist() {
		System.out.println("this.goodsId:  "+ this.goodsId);
		for(HuntingGoodsImage targetGoodsImage : huntingGoodsImages) {
			targetGoodsImage.setHuntingGoods(HuntingGoods.builder().goodsId(this.goodsId).goodsLikes(this.goodsLikes)
					.goodsName(this.goodsName).goodsPrice(this.goodsPrice).goodsUrl(this.goodsUrl).build());
		}
		
	}
	
}
