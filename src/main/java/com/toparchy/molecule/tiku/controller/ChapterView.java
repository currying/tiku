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

import com.toparchy.molecule.tiku.model.Chapter;
import com.toparchy.molecule.tiku.service.ChapterRegistration;

@Model
@ViewScoped
public class ChapterView implements Serializable {

	private static final long serialVersionUID = -8729108434025995268L;

	@Inject
	private ChapterRegistration chapterRegistration;

	@Produces
	@Named
	private Chapter newChapter;

	@PostConstruct
	public void initNewChapter() {
		newChapter = new Chapter();
	}

	public void openAddChapterDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("draggable", false);
		options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog("addChapter", options, null);
	}

	public void onAddChapter(SelectEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "新增章节",
				((Chapter) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addChapter() {
		try {
			chapterRegistration.register(newChapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().closeDialog(newChapter);
	}
}
