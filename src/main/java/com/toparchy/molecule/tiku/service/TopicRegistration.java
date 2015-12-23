package com.toparchy.molecule.tiku.service;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.toparchy.molecule.tiku.model.Topic;

@Stateless
public class TopicRegistration {
	@Inject
	private EntityManager em;

	@Inject
	private Event<Topic> topicEventSrc;

	public void register(Topic topic) throws Exception {
		em.merge(topic);
		topicEventSrc.fire(topic);
	}

	public void modify(Topic topic) {
		em.merge(topic);
		topicEventSrc.fire(topic);
	}

	public void delete(Topic topic) {
		em.remove(em.merge(topic));
		topicEventSrc.fire(topic);
	}
}
