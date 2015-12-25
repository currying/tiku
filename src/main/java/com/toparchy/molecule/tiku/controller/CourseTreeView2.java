package com.toparchy.molecule.tiku.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.toparchy.molecule.tiku.data.ChapterListProducer;
import com.toparchy.molecule.tiku.data.ChapterRepository;
import com.toparchy.molecule.tiku.data.CourseRepository;
import com.toparchy.molecule.tiku.data.KnowledgePointListProducer;
import com.toparchy.molecule.tiku.model.Chapter;
import com.toparchy.molecule.tiku.model.Course;
import com.toparchy.molecule.tiku.model.KnowledgePoint;
import com.toparchy.molecule.tiku.service.ChapterRegistration;
import com.toparchy.molecule.tiku.service.CourseRegistration;
import com.toparchy.molecule.tiku.service.KnowledgePointRegistration;

@Model
public class CourseTreeView2 implements Serializable {

	private static final long serialVersionUID = 4979209521004256054L;

	@Produces
	@Named
	private Chapter newChapter;
	@Produces
	@Named
	private KnowledgePoint newKnowledgePoint;

	@Produces
	@Named
	private Chapter selectChapter;

	private boolean disabled = true;
	@Inject
	private ChapterListProducer chapterListProducer;
	@Inject
	private ChapterRegistration chapterRegistration;
	@Inject
	private CourseRepository courseRepository;
	@Inject
	private KnowledgePointListProducer knowledgePointListProducer;
	@Inject
	private KnowledgePointRegistration knowledgePointRegistration;
	@Inject
	private ChapterRepository chapterRepository;
	private String chapterId;
	private String courseId;

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	@PostConstruct
	public void init() {
		newKnowledgePoint = new KnowledgePoint();
		newChapter = new Chapter();
	}

	public Chapter getSelectChapter() {
		return selectChapter;
	}

	public void setSelectChapter(Chapter selectChapter) {
		this.selectChapter = selectChapter;
	}

//
//	public void querySelectCourse() {
//		selectCourse = courseRepository.findById(courseId);
//	}

	public void querySelectChapter() {
		selectChapter = chapterRepository.findById(chapterId);
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

//	public void onCourseRowSelect(SelectEvent event) {
//		selectCourse = (Course) event.getObject();
//		chapterListProducer.retrieveChapterListByCourse(selectCourse.getId());
//		if (selectCourse != null)
//			disabled = false;
//	}

	public void onCourseRowUnselect(UnselectEvent event) {
	}

	public void onChapterRowSelect(SelectEvent event) {
		selectChapter = (Chapter) event.getObject();
		System.out.println(selectChapter);
		if (selectChapter != null)
			disabled = false;
	}

	public void onChapterRowUnselect(UnselectEvent event) {
	}

	public void openAddChapterDialog(ActionEvent event) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("draggable", false);
		options.put("modal", true);
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> param = new ArrayList<String>();
		param.add((String) event.getComponent().getAttributes().get("courseId"));
		params.put("courseId", param);
		RequestContext.getCurrentInstance().openDialog("addChapter", options, params);
	}

	public void onAddChapter(SelectEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "新增章节",
				((Chapter) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

//	public void addChapter() {
//		newChapter.setCourse(selectCourse);
//		try {
//			chapterRegistration.register(newChapter);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		RequestContext.getCurrentInstance().closeDialog(newChapter);
//	}

	public void openAddKnowledgePointDialog(ActionEvent event) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("draggable", false);
		options.put("modal", true);
		Map<String, List<String>> params = new HashMap<String, List<String>>();
		List<String> param = new ArrayList<String>();
		param.add((String) event.getComponent().getAttributes().get("chapterId"));
		params.put("chapterId", param);
		RequestContext.getCurrentInstance().openDialog("addKnowledgePoint", options, params);
	}

	public void onAddKnowledgePoint(SelectEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "新增知识点",
				((Chapter) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addKnowledgePoint() {
		newKnowledgePoint.setChapter(selectChapter);
		try {
			knowledgePointRegistration.register(newKnowledgePoint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().closeDialog(newKnowledgePoint);
	}
}
