package com.toparchy.molecule.tiku.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.toparchy.molecule.tiku.data.ChapterListProducer;
import com.toparchy.molecule.tiku.data.ChapterRepository;
import com.toparchy.molecule.tiku.model.Chapter;
import com.toparchy.molecule.tiku.model.KnowledgePoint;
import com.toparchy.molecule.tiku.service.KnowledgePointRegistration;

@Model
@ViewScoped
public class KnowledgePointController implements Serializable {
	private static final long serialVersionUID = -9142721171328467959L;
	@Produces
	@Named
	private KnowledgePoint newKnowledgePoint;
	@Inject
	private KnowledgePointRegistration knowledgePointRegistration;
	private String selectCourseId;
	private String selectChapterId;
	private List<Chapter> chapters;
	@Inject
	private ChapterListProducer chapterListProducer;
	@Inject
	private ChapterRepository chapterRepository;

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

	public String getSelectCourseId() {
		return selectCourseId;
	}

	public void setSelectCourseId(String selectCourseId) {
		this.selectCourseId = selectCourseId;
	}

	public String getSelectChapterId() {
		return selectChapterId;
	}

	public void setSelectChapterId(String selectChapterId) {
		this.selectChapterId = selectChapterId;
	}

	@PostConstruct
	public void init() {
		newKnowledgePoint = new KnowledgePoint();
	}

	public void addKnowledgePoint() {
		newKnowledgePoint.setChapter(chapterRepository.findById(selectChapterId));
		try {
			knowledgePointRegistration.register(newKnowledgePoint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().closeDialog(newKnowledgePoint);
	}

	public void onCourseChange() {
		chapters = chapterListProducer.retrieveChapterListByCourse(selectCourseId);
	}
}
