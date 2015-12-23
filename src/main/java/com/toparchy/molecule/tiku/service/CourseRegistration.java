package com.toparchy.molecule.tiku.service;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.toparchy.molecule.tiku.model.Course;

@Stateless
public class CourseRegistration {
	@Inject
	private EntityManager em;

	@Inject
	private Event<Course> courseEventSrc;

	public void register(Course course) throws Exception {
		em.merge(course);
		courseEventSrc.fire(course);
	}

	public void modify(Course course) {
		em.merge(course);
		courseEventSrc.fire(course);
	}

	public void delete(Course course) {
		em.remove(em.merge(course));
		courseEventSrc.fire(course);
	}
}
