package com.toparchy.molecule.tiku.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.toparchy.molecule.tiku.model.Chapter;

@ApplicationScoped
public class ChapterRepository {
	@Inject
	private EntityManager em;

	public List<Chapter> findAllOrderByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Chapter> criteria = cb.createQuery(Chapter.class);
		Root<Chapter> chapter = criteria.from(Chapter.class);
		criteria.select(chapter).orderBy(cb.asc(chapter.get("name")));
		return em.createQuery(criteria).getResultList();
	}

	public List<Chapter> findByCourse(String courseId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Chapter> criteria = cb.createQuery(Chapter.class);
		Root<Chapter> chapter = criteria.from(Chapter.class);
		criteria.select(chapter).where(cb.equal(chapter.get("course").get("id"), courseId))
				.orderBy(cb.asc(chapter.get("name")));
		return em.createQuery(criteria).getResultList();
	}
}
