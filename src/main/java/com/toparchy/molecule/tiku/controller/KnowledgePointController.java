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

import com.toparchy.molecule.tiku.data.ChapterRepository;
import com.toparchy.molecule.tiku.model.Chapter;
import com.toparchy.molecule.tiku.model.KnowledgePoint;
import com.toparchy.molecule.tiku.service.KnowledgePointRegistration;

@Model
@ViewScoped
public class KnowledgePointController implements Serializable {
	private static final long serialVersionUID = -5379862102041891081L;
	@Inject
	private KnowledgePointRegistration knowledgePointRegistration;
	@Inject
	private ChapterRepository chapterRepository;

	@Produces
	@Named
	private KnowledgePoint newKnowledgePoint;

	private String chapterId;
	private Chapter selectChapter;

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public Chapter getSelectChapter() {
		selectChapter = chapterRepository.findById(chapterId);
		return selectChapter;
	}

	@PostConstruct
	public void initNewKnowledgePoint() {
		newKnowledgePoint = new KnowledgePoint();
	}

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
