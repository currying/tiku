package com.toparchy.molecule.tiku.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.toparchy.molecule.tiku.model.Chapter;

@RequestScoped
public class ChapterListProducer {
	@Inject
	private ChapterRepository chapterRepository;

	private List<Chapter> chapters;

	private String courseId;

	@Produces
	@Named
	public List<Chapter> getChapters() {
		return chapters;
	}

	public void onChapterListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Chapter chapter) {
		retrieveChapterListByCourse(courseId);
	}

	public void retrieveChapterListByCourse(String courseId) {
		this.courseId = courseId;
		chapters = chapterRepository.findByCourse(courseId);
	}
}
