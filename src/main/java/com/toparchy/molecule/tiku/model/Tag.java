package com.toparchy.molecule.tiku.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TAG_")
@XmlRootElement
public class Tag implements Serializable {
	private static final long serialVersionUID = -4687001946199876622L;
	@Id
	@Column(name = "TAG_NAME_", length = 255)
	private String tagName;
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "tags")
	@JsonIgnore
	@OrderBy("tagName ASC")
	private Set<Topic> topics = new HashSet<Topic>();

	public Tag() {
	}

	public Tag(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

}
