package com.toparchy.molecule.tiku.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.toparchy.molecule.tiku.data.CourseRepository;
import com.toparchy.molecule.tiku.model.Chapter;
import com.toparchy.molecule.tiku.service.ChapterRegistration;

@Model
@ViewScoped
public class ChapterController implements Serializable {
	private static final long serialVersionUID = -1348122293489451361L;
	@Produces
	@Named
	private Chapter newChapter;
	private String selectCourseId;
	@Inject
	private ChapterRegistration chapterRegistration;
	@Inject
	private CourseRepository courseRepository;

	public String getSelectCourseId() {
		return selectCourseId;
	}

	public void setSelectCourseId(String selectCourseId) {
		this.selectCourseId = selectCourseId;
	}

	public Chapter getNewChapter() {
		return newChapter;
	}

	public void setNewChapter(Chapter newChapter) {
		this.newChapter = newChapter;
	}

	@PostConstruct
	public void init() {
		newChapter = new Chapter();
	}

	public void addChapter() {
		newChapter.setCourse(courseRepository.findById(selectCourseId));
		try {
			chapterRegistration.register(newChapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().closeDialog(newChapter);
	}
}
