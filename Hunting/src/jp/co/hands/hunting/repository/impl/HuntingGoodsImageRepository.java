package jp.co.hands.hunting.repository.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.internal.jpa.parsing.jpql.antlr.JPQLParser.selectItem_return;

import jp.co.hands.hunting.entity.model.impl.HuntingGoodsImage;
import jp.co.hands.hunting.entity.model.impl.HuntingGoodsImage_;
import jp.co.hands.hunting.entity.model.impl.HuntingGoods_;
import jp.co.hands.hunting.repository.JpaDaoSupport;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class HuntingGoodsImageRepository extends JpaDaoSupport<HuntingGoodsImage, Long> {
	
	public HuntingGoodsImageRepository() {
		super(HuntingGoodsImage.class);
	}
	
	public Long countGoodsImage(long goodsId) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(long.class);
		Root<HuntingGoodsImage> r = query.from(HuntingGoodsImage.class);
		query.select(cb.count(r)).where(cb.equal(r.get(HuntingGoodsImage_.huntingGoods).get(HuntingGoods_.goodsId), goodsId));
		
		return em.createQuery(query).getSingleResult();
		
	}
}
