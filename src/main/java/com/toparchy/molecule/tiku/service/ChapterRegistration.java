package com.toparchy.molecule.tiku.service;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.toparchy.molecule.tiku.model.Chapter;

@Stateless
public class ChapterRegistration {
	@Inject
	private EntityManager em;

	@Inject
	private Event<Chapter> chapterEventSrc;

	public void register(Chapter chapter) throws Exception {
		em.persist(chapter);
		chapterEventSrc.fire(chapter);
	}

	public void modify(Chapter chapter) {
		em.merge(chapter);
		chapterEventSrc.fire(chapter);
	}

	public void delete(Chapter chapter) {
		em.remove(em.merge(chapter));
		chapterEventSrc.fire(chapter);
	}
}
