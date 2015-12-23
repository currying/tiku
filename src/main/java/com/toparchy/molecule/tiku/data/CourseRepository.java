package com.toparchy.molecule.tiku.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.toparchy.molecule.tiku.model.Course;

@ApplicationScoped
public class CourseRepository {
	@Inject
	private EntityManager em;

	public List<Course> findAllOrderByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> criteria = cb.createQuery(Course.class);
		Root<Course> course = criteria.from(Course.class);
		criteria.select(course).orderBy(cb.asc(course.get("name")));
		return em.createQuery(criteria).getResultList();
	}
}
