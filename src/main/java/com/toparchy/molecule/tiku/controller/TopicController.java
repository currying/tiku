package com.toparchy.molecule.tiku.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.toparchy.molecule.tiku.data.TagRepository;
import com.toparchy.molecule.tiku.model.Tag;
import com.toparchy.molecule.tiku.model.Topic;
import com.toparchy.molecule.tiku.service.TopicRegistration;

@Model
public class TopicController {

	@Inject
	private FacesContext facesContext;
	@Inject
	private TopicRegistration topicRegistration;
	@Inject
	private TagRepository tagRepository;
	@Produces
	@Named
	private Topic newTopic;

	private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@PostConstruct
	public void initNewTopic() {
		newTopic = new Topic();
	}

	public String register() throws Exception {
		try {
			for (String tag : tag.split(" ")) {
				Tag t = tagRepository.findById(tag);
				if (t != null) {
					newTopic.addTag(t);
				} else {
					newTopic.addTag(new Tag(tag));
				}
			}
			topicRegistration.register(newTopic);
			initNewTopic();
			return "success";
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Newly added unsuccessful");
			facesContext.addMessage(null, m);
			return null;
		}
	}

	private String getRootErrorMessage(Exception e) {
		String errorMessage = "Newly added failed. See server log for more information";
		if (e == null) {
			return errorMessage;
		}
		Throwable t = e;
		while (t != null) {
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		return errorMessage;
	}

}
