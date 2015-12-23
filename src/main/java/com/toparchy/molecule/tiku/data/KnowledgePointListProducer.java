package com.toparchy.molecule.tiku.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;

import com.toparchy.molecule.tiku.model.KnowledgePoint;

public class KnowledgePointListProducer {
	@Inject
	private KnowledgePointRepository knowledgePointRepository;

	private List<KnowledgePoint> knowledgePoints;
	private TagCloudModel model;

	@Produces
	@Named
	public List<KnowledgePoint> getKnowledgePoints() {
		return knowledgePoints;
	}

	// public void onKnowledgePointListChanged(
	// @Observes(notifyObserver = Reception.IF_EXISTS) final KnowledgePoint
	// knowledgePoint) {
	// retrieveAllKnowledgePointsOrderedByName();
	// }

	@PostConstruct
	public void retrieveAllKnowledgePointsOrderedByName() {
		knowledgePoints = knowledgePointRepository.findFromTo(0, 100);
		model = new DefaultTagCloudModel();
		for (KnowledgePoint knowledgePoint : knowledgePoints) {
			model.addTag(new DefaultTagCloudItem(knowledgePoint.getName(), (int) ((Math.random()) * 5 + 1)));
		}
	}

	public TagCloudModel getModel() {
		return model;
	}
}
