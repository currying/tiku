package com.toparchy.molecule.tiku.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.TagCloudItem;

import com.toparchy.molecule.tiku.data.TagRepository;
import com.toparchy.molecule.tiku.data.TopicRepository;
import com.toparchy.molecule.tiku.model.Tag;
import com.toparchy.molecule.tiku.model.Topic;

@Model
@ViewScoped
public class TagView implements Serializable {

	private static final long serialVersionUID = -1943371862083487307L;
	@Inject
	private TagRepository tagRepository;
	private List<Tag> tags;
	private String key;
	private List<Topic> topics;
	private Tag selectedTag;
	@Inject
	private TopicRepository topicRepository;

	public List<Topic> getTopics() {
		return topics;
	}

	public Tag getSelectedTag() {
		return selectedTag;
	}

	public void setSelectedTag(Tag selectedTag) {
		this.selectedTag = selectedTag;
	}

	public void onSelect(SelectEvent event) {
		TagCloudItem item = (TagCloudItem) event.getObject();
		topics = tagRepository.findTopicByTagName(item.getLabel());
	}

	public List<Tag> getTags() {
		return tags;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void select() {
		tags = tagRepository.findLikeByName(key);
	}

	public void select2() {
		topics = topicRepository.findAllOrderedByName(key);
	}

	public void onRowSelect(SelectEvent event) {
		topics = tagRepository.findTopicByTagName(((Tag) event.getObject()).getTagName());
	}
}
