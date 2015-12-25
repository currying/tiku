package com.toparchy.molecule.tiku.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.toparchy.molecule.tiku.model.Topic;

@ApplicationScoped
public class TopicRepository {
	@Inject
	private EntityManager em;

	public Topic findById(String id) {
		return em.find(Topic.class, id);
	}

	public Topic findByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Topic> criteria = cb.createQuery(Topic.class);
		Root<Topic> Topic = criteria.from(Topic.class);
		criteria.select(Topic).where(cb.equal(Topic.get("topicName"), name));
		return em.createQuery(criteria).getSingleResult();
	}

	public List<Topic> findAllOrderedByName(String topicName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Topic> criteria = cb.createQuery(Topic.class);
		Root<Topic> topic = criteria.from(Topic.class);
		criteria.select(topic).where(cb.like(topic.<String> get("topicName"), "%" + topicName + "%"));
		return em.createQuery(criteria).getResultList();
	}

	public List<Topic> findFromTo(int start, int max) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Topic> criteria = cb.createQuery(Topic.class);
		Root<Topic> topic = criteria.from(Topic.class);
		criteria.select(topic);
		return em.createQuery(criteria).setFirstResult(start).setMaxResults(max).getResultList();
	}

	public List<Topic> findTopicByKnowledgePoint(String KnowledgePointId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Topic> criteria = cb.createQuery(Topic.class);
		Root<Topic> topic = criteria.from(Topic.class);
		criteria.select(topic).where(cb.equal(topic.get("knowledgePoint").get("name"), KnowledgePointId))
				.orderBy(cb.asc(topic.get("name")));
		return em.createQuery(criteria).getResultList();
	}
}
