package com.toparchy.molecule.tiku.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.Model;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.TagCloudItem;

import com.toparchy.molecule.tiku.data.TagRepository;
import com.toparchy.molecule.tiku.data.TopicRepository;
import com.toparchy.molecule.tiku.model.KnowledgePoint;
import com.toparchy.molecule.tiku.model.Tag;
import com.toparchy.molecule.tiku.model.Topic;

@Model
@ViewScoped
public class KnowledgePointView implements Serializable {

	private static final long serialVersionUID = -6526022580265886607L;
	private KnowledgePoint selectedKnowledgePoint;
	private List<Topic> topics;
	private Set<Tag> tags;
	private String key;
	private Tag selectedTag;
	@Inject
	private TagRepository tagRepository;
	@Inject
	private TopicRepository topicRepository;

	public Tag getSelectedTag() {
		return selectedTag;
	}

	public void setSelectedTag(Tag selectedTag) {
		this.selectedTag = selectedTag;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public KnowledgePoint getSelectedKnowledgePoint() {
		return selectedKnowledgePoint;
	}

	public void setSelectedKnowledgePoint(KnowledgePoint selectedKnowledgePoint) {
		this.selectedKnowledgePoint = selectedKnowledgePoint;
	}

	public void onSelectByKnowledgePoint(SelectEvent event) {
		TagCloudItem item = (TagCloudItem) event.getObject();
		topics = topicRepository.findTopicByKnowledgePointName(item.getLabel());
		System.out.println(item.getLabel());
	}

	public void onSelectByTag(SelectEvent event) {
		TagCloudItem item = (TagCloudItem) event.getObject();
		topics = tagRepository.findTopicByTagName(item.getLabel());
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void select() {
		tags = tagRepository.findLikeByName(key.split(" "));
	}

	public void select2() {
		topics = topicRepository.findAllOrderedByName(key);
	}

	public void onRowSelect(SelectEvent event) {
		topics = tagRepository.findTopicByTagName(((Tag) event.getObject()).getTagName());
	}
}
