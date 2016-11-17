package jp.co.hands.hunting.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import jp.co.hands.hunting.entity.model.BaseEntity;

public class JpaDaoSupport<T extends BaseEntity, K extends Serializable> implements Serializable {

	protected Class<T> targetEntity;

	@PersistenceContext(unitName = "Hunting", type = PersistenceContextType.TRANSACTION)
	public EntityManager em;

	public JpaDaoSupport(Class<T> targetEntity) {
		this.targetEntity = targetEntity;
	}

	public List<T> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(targetEntity);
		return em.createQuery(query.select(query.from(targetEntity))).getResultList();
	}
	
	public Long countAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		return em.createQuery(query.select(cb.count(query.from(targetEntity)))).getSingleResult();
	}

	public T findByKey(K key) {
		if (key != null) {
			return em.find(targetEntity, key);
		}
		return null;
	}

	public void save(T targetEntity, K key) {
		if (findByKey(key) == null) {
			persist(targetEntity);
			em.flush();
			em.clear();
		} else {
			System.out.println("dupuliate key -->" + key);
		}
	}

	protected void persist(T targetEntity) {
		if (targetEntity != null) {
			em.persist(targetEntity);
		} else {
			System.out.println("Entity not found");
		}
	}

	public void delete(K key) {
		if (findByKey(key) != null) {
			em.remove(findByKey(key));
			em.flush();
			em.clear();
		} else {
			System.out.println(key + " has already been deleted or not found.");
		}
	}

	public void updata(T targetEntity, K key) {
		if (findByKey(key) != null) {
			merge(targetEntity);
			em.flush();
			em.clear();
		} else {
			persist(targetEntity);
			em.flush();
			em.clear();
		}
	}

	protected void merge(T targetEntity) {
		if (targetEntity != null) {
			em.merge(targetEntity);
		} else {
			System.out.println("Entity not found");
		}
	}
}
