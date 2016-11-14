package jp.co.hands.hunting.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import jp.co.hands.hunting.entity.model.impl.HuntingModel_;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLine;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLineId;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLineId_;
import jp.co.hands.hunting.entity.model.impl.HuntingTimeLine_;
import jp.co.hands.hunting.repository.JpaDaoSupport;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class HuntingTimeLineRepository extends JpaDaoSupport<HuntingTimeLine, HuntingTimeLineId> {

	public HuntingTimeLineRepository() {
		super(HuntingTimeLine.class);
	}

	public List<HuntingTimeLine> getByModelId(String userId) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<HuntingTimeLine> query = cb.createQuery(HuntingTimeLine.class);
		Root<HuntingTimeLine> r = query.from(HuntingTimeLine.class);
		query.select(r)
				.where(cb.equal(r.get(HuntingTimeLine_.huntingTimeLineId).get(HuntingTimeLineId_.userId), userId))
				.orderBy(cb.asc(r.get(HuntingTimeLine_.registeredDateTime)));
		return em.createQuery(query).getResultList();
	}

	/**
	 * ユーザの最新のタイムラインの画像を返す(0件の場合にエラーとなるgetSingleResultを返すのではなくgetResultListで返す)
	 * @param userId ユーザID
	 * @return List<HuntingTimeLIne>
	*/
	public List<HuntingTimeLine> getLatestTimeLine(String userId) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<HuntingTimeLine> query = cb.createQuery(HuntingTimeLine.class);
		Root<HuntingTimeLine> r = query.from(HuntingTimeLine.class);
		Subquery<HuntingTimeLine> subQuery  = query.subquery(HuntingTimeLine.class);
		Root<HuntingTimeLine> sr = subQuery.from(HuntingTimeLine.class);
		subQuery.select(cb.greatest(sr.get(HuntingTimeLine_.registeredDateTime)).as(HuntingTimeLine.class)).
		where(cb.equal(sr.get(HuntingTimeLine_.huntingTimeLineId).get(HuntingTimeLineId_.userId), userId));
		List<Predicate> preds = new ArrayList<>();
		preds.add(cb.equal(r.get(HuntingTimeLine_.huntingModel).get(HuntingModel_.userId), userId));
		preds.add(r.get(HuntingTimeLine_.registeredDateTime).in(subQuery));
		query.select(r).where(cb.and((Predicate[])preds.toArray(new Predicate[]{})));
		return em.createQuery(query).getResultList();	
	}
}
