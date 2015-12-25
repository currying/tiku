package com.toparchy.molecule.tiku.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.toparchy.molecule.tiku.model.KnowledgePoint;
import com.toparchy.molecule.tiku.model.Topic;

@ApplicationScoped
public class KnowledgePointRepository {
	@Inject
	private EntityManager em;

	public List<KnowledgePoint> findFromTo(int start, int max) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KnowledgePoint> criteria = cb.createQuery(KnowledgePoint.class);
		Root<KnowledgePoint> knowledgePoint = criteria.from(KnowledgePoint.class);
		criteria.select(knowledgePoint);
		return em.createQuery(criteria).setFirstResult(start).setMaxResults(max).getResultList();
	}
}
