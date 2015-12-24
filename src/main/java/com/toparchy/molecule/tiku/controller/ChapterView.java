package com.toparchy.molecule.tiku.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.toparchy.molecule.tiku.data.CourseRepository;
import com.toparchy.molecule.tiku.model.Chapter;
import com.toparchy.molecule.tiku.model.Course;
import com.toparchy.molecule.tiku.service.ChapterRegistration;

@Model
@ViewScoped
public class ChapterView implements Serializable {

	private static final long serialVersionUID = -8729108434025995268L;

	@Inject
	private ChapterRegistration chapterRegistration;
	@Inject
	private CourseRepository courseRepository;

	@Produces
	@Named
	private Chapter newChapter;

	private String courseId;
	private Course selectCourse;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Course getSelectCourse() {
		selectCourse = courseRepository.findById(courseId);
		return selectCourse;
	}

	@PostConstruct
	public void initNewChapter() {
		newChapter = new Chapter();
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

	public void addChapter() {
		newChapter.setCourse(selectCourse);
		try {
			chapterRegistration.register(newChapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().closeDialog(newChapter);
	}
}
