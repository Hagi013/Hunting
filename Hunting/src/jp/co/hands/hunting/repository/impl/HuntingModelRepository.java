package jp.co.hands.hunting.repository.impl;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import jp.co.hands.hunting.entity.model.impl.HuntingModel;
import jp.co.hands.hunting.entity.model.impl.HuntingModel_;
import jp.co.hands.hunting.repository.JpaDaoSupport;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class HuntingModelRepository extends JpaDaoSupport<HuntingModel, String> {
	
	public HuntingModelRepository() {
		super(HuntingModel.class);
	}
	
	public List<HuntingModel> findByKeyList(String[] modelIds) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<HuntingModel> query = cb.createQuery(HuntingModel.class);
		Root<HuntingModel> r = query.from(HuntingModel.class);
		query.select(r).where(r.get(HuntingModel_.userId).in(Arrays.asList(modelIds)));
		return em.createQuery(query).getResultList();
	}

}
