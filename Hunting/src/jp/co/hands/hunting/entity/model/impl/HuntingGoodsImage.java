package jp.co.hands.hunting.entity.model.impl;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import jp.co.hands.hunting.entity.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(exclude="huntingGoods")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name="HuntingGoodsImage")
@Table(name="hunting_goods_image")
public class HuntingGoodsImage extends BaseEntity {
	
	@Id
	@SequenceGenerator(name="hunting_goods_image_id_seq", allocationSize = 1, initialValue = 1)	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hunting_goods_image_id_seq")
	@Column(name="goods_image_id")
	private long goodsImageId;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="goods_image_data")
	private byte[] goodsImageData;
	
	@Lob
	@Column(name="goods_image_url")
	private String goodsImageUrl;
	
	@ManyToOne(targetEntity=HuntingGoods.class)
	@JoinColumn(name="goods_id")
	private HuntingGoods huntingGoods;
		
}
