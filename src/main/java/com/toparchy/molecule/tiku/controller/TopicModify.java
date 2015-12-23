package com.toparchy.molecule.tiku.controller;

import java.io.Serializable;
import java.util.Iterator;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.toparchy.molecule.tiku.data.TagRepository;
import com.toparchy.molecule.tiku.data.TopicRepository;
import com.toparchy.molecule.tiku.model.Tag;
import com.toparchy.molecule.tiku.model.Topic;
import com.toparchy.molecule.tiku.service.TopicRegistration;

@Model
@SessionScoped
public class TopicModify implements Serializable {
	private static final long serialVersionUID = -8610807547430234208L;
	@Inject
	private FacesContext facesContext;
	@Inject
	private TopicRepository topicRepository;
	@Inject
	private TopicRegistration topicRegistration;
	@Inject
	private TagRepository tagRepository;
	private Topic selectTopic;

	private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Topic getSelectTopic() {
		return selectTopic;
	}

	public void setSelectTopic(Topic selectTopic) {
		this.selectTopic = selectTopic;
	}

	public void find(String id) {
		setSelectTopic(topicRepository.findById(id));
		StringBuilder sb = new StringBuilder();
		Iterator<Tag> itag = selectTopic.getTags().iterator();
		while (itag.hasNext()) {
			sb.append(itag.next().getTagName() + " ");
		}
		tag = sb.toString();
	}

	public void delete() {
		topicRegistration.delete(selectTopic);
	}

	public String modify() throws Exception {
		try {
			for (String tag : tag.split(" ")) {
				Tag t = tagRepository.findById(tag);
				if (t == null) {
					selectTopic.addTag(new Tag(tag));
				}
			}
			topicRegistration.modify(selectTopic);
			return "success";
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Modify  unsuccessful");
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
