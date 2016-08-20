package jp.co.hands.hunting.repository.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
				.where(cb.equal(r.get(HuntingTimeLine_.huntingTimeLineId).get(HuntingTimeLineId_.userId), userId));
		return em.createQuery(query).getResultList();
	}

}
