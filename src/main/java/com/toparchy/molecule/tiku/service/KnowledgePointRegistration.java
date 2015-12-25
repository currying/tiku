package com.toparchy.molecule.tiku.service;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.toparchy.molecule.tiku.model.KnowledgePoint;

@Stateless
public class KnowledgePointRegistration {
	@Inject
	private EntityManager em;

	@Inject
	private Event<KnowledgePoint> knowledgePointEventSrc;

	public void register(KnowledgePoint knowledgePoint) throws Exception {
		em.persist(knowledgePoint);
		knowledgePointEventSrc.fire(knowledgePoint);
	}

	public void modify(KnowledgePoint knowledgePoint) {
		em.merge(knowledgePoint);
		knowledgePointEventSrc.fire(knowledgePoint);
	}

	public void delete(KnowledgePoint knowledgePoint) {
		em.remove(em.merge(knowledgePoint));
		knowledgePointEventSrc.fire(knowledgePoint);
	}
}
