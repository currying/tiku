package com.toparchy.molecule.tiku.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.toparchy.molecule.tiku.data.TopicRepository;
import com.toparchy.molecule.tiku.model.Topic;

@Model
public class TopicView {
	@Inject
	private TopicRepository topicRepository;

	private Topic topic;

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public void find(String id) {
		topic = topicRepository.findById(id);
	}
}
