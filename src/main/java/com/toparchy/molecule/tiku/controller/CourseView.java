package com.toparchy.molecule.tiku.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.toparchy.molecule.tiku.model.Course;
import com.toparchy.molecule.tiku.service.CourseRegistration;

@Model
@ViewScoped
public class CourseView implements Serializable {

	private static final long serialVersionUID = -6693847761860132390L;
	@Produces
	@Named
	private Course newCourse;
	@Inject
	private CourseRegistration courseRegistration;

	@PostConstruct
	public void initNewCourse() {
		newCourse = new Course();
	}

	public void openDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("draggable", false);
		options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog("addCourse", options, null);
	}

	public void onAddCourse(SelectEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "新增课程",
				((Course) event.getObject()).getName());

		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addCourse() {
		try {
			courseRegistration.register(newCourse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().closeDialog(newCourse);
	}
}