package jp.co.hands.hunting.repository.impl;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import jp.co.hands.hunting.entity.model.impl.HuntingGoods;
import jp.co.hands.hunting.entity.model.impl.HuntingGoods_;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLine;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLineId;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLineId_;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLine_;
import jp.co.hands.hunting.repository.JpaDaoSupport;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class HuntingGoodsRepository extends JpaDaoSupport<HuntingGoods, Long> {
	
	public HuntingGoodsRepository() {
		super(HuntingGoods.class);
	}
	
	/**	
	 * HuntingGoodsの主キーはauto_incrementであるため、主キーは不要であるためオーバーロードする。 
	*/
	public void save(HuntingGoods huntingGoods) {	
		if(Optional.ofNullable(huntingGoods).isPresent()) {
			persist(huntingGoods);
		} else {
			System.out.println("hunitngGoods is null");
		}
	}
	
	
	/**
	 * ユーザIDとタイムラインIDより商品を取得する。
	 * 
	 * 
	 */
	public List<HuntingGoods> fetchGoodsByUserAndTimeLine(String targetUserId, String targetTimeLineId) {
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<HuntingGoods> query = cb.createQuery(HuntingGoods.class);
		Root<HuntingGoods> r = query.from(HuntingGoods.class);
		query.select(r).where(cb.equal(r.get(HuntingGoods_.huntingTimeLine).get(HuntingTimeLine_.huntingTimeLineId),
				HuntingTimeLineId.builder().userId(targetUserId).timeLineId(targetTimeLineId).build()));
		return em.createQuery(query).getResultList();
		
	}
	
	
	/**
	 * ユーザIDより商品を取得する。
	 * 
	 */
	public List<HuntingGoods> fetchGoodsByUserId(String targetUserId) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<HuntingGoods> query = cb.createQuery(HuntingGoods.class);
		Root<HuntingGoods> r = query.from(HuntingGoods.class);
		query.select(r).where(cb.equal(r.get(HuntingGoods_.huntingTimeLine).get(HuntingTimeLine_.huntingTimeLineId)
				.get(HuntingTimeLineId_.userId), targetUserId));
		return em.createQuery(query).getResultList();		
	}

	
}
