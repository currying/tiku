package com.toparchy.molecule.tiku.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.toparchy.molecule.tiku.model.Tag;
import com.toparchy.molecule.tiku.model.Topic;

@ApplicationScoped
public class TagRepository {
	@Inject
	private EntityManager em;

	@PersistenceUnit(name = "molecule")
	private EntityManagerFactory emf;
	private String sql;
	private Query query;
	private List<Topic> objecArraytList;

	public List<Topic> findTopicByTagName(String name) {
		EntityManager em = emf.createEntityManager();
		sql = "select topic.TOPIC_ID_,topic.TOPIC_NAME_,topic.TOPIC_ANSWERFILE_,topic.TOPIC_STEMFILE_,topic.TOPIC_CONTENT_,topic.TOPIC_ANSWER_,TOPIC_MATERIALS_ from TOPIC__TAG_ tt "
				+ "left join TAG_ tag on tag.TAG_NAME_=tt.tags_TAG_NAME_ "
				+ "left join TOPIC_ topic on topic.TOPIC_ID_=tt.topics_TOPIC_ID_ " + "where tag.TAG_NAME_=?1";
		query = em.createNativeQuery(sql, Topic.class);
		query.setParameter(1, name);
		objecArraytList = query.getResultList();
		em.close();
		return objecArraytList;
	}

	public Tag findById(String name) {
		return em.find(Tag.class, name);
	}

	public Tag findByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tag> criteria = cb.createQuery(Tag.class);
		Root<Tag> tag = criteria.from(Tag.class);
		criteria.select(tag).where(cb.equal(tag.get("tagName"), name));
		return em.createQuery(criteria).getSingleResult();
	}

	public List<Tag> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tag> criteria = cb.createQuery(Tag.class);
		Root<Tag> tag = criteria.from(Tag.class);
		criteria.select(tag).orderBy(cb.asc(tag.get("tagName")));
		return em.createQuery(criteria).getResultList();
	}

	public List<Tag> findLikeByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tag> criteria = cb.createQuery(Tag.class);
		Root<Tag> tag = criteria.from(Tag.class);
		criteria.select(tag).where(cb.like(tag.<String> get("tagName"), "%" + name + "%"));
		return em.createQuery(criteria).getResultList();
	}

	public List<Tag> findFromTo(int start, int max) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tag> criteria = cb.createQuery(Tag.class);
		Root<Tag> tag = criteria.from(Tag.class);
		criteria.select(tag);
		return em.createQuery(criteria).setFirstResult(start).setMaxResults(max).getResultList();
	}
}
