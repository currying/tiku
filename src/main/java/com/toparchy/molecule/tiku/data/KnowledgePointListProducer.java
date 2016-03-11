package com.toparchy.molecule.tiku.data;

import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;

import com.toparchy.molecule.tiku.model.KnowledgePoint;

@Model
@RequestScoped
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
	private static int getRandom(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}

	@PostConstruct
	public void retrieveAllKnowledgePointsOrderedByName() {
		knowledgePoints = knowledgePointRepository.findFromTo(getRandom(0, knowledgePointRepository.count() - 30), 30);
		model = new DefaultTagCloudModel();
		for (KnowledgePoint knowledgePoint : knowledgePoints) {
			model.addTag(new DefaultTagCloudItem(knowledgePoint.getName(), (int) ((Math.random()) * 5 + 1)));
		}
	}

	public List<KnowledgePoint> retrieveKnowledgePointsByChapter(String chapterId) {
		return knowledgePointRepository.findByChapter(chapterId);
	}

	public TagCloudModel getModel() {
		return model;
	}
}
