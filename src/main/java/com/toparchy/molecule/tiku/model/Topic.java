package com.toparchy.molecule.tiku.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TOPIC_")
@XmlRootElement
public class Topic implements Serializable {
	private static final long serialVersionUID = 1360258123736471405L;
	@Id
	@Column(name = "TOPIC_ID_", length = 50)
	@GeneratedValue(generator = "topic-uuid")
	@GenericGenerator(name = "topic-uuid", strategy = "uuid")
	private String id;
	@Column(name = "TOPIC_NAME_", length = 255)
	private String name;
	@Column(name = "TOPIC_ANSWERFILE_", length = 50)
	private String answerFile;
	@Column(name = "TOPIC_STEMFILE_", length = 50)
	private String stemFile;
	@Column(name = "TOPIC_MATERIALS_", length = 50)
	private String topicMaterials;
	@Column(name = "TOPIC_CONTENT_")
	@Lob
	private String topicContent;
	@Column(name = "TOPIC_ANSWER_")
	@Lob
	private String topicAnswer;
	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.EAGER)
	@OrderBy("id ASC")
	private Set<Tag> tags = new HashSet<Tag>();
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private KnowledgePoint knowledgePoint;

	public Topic() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTopicContent() {
		return topicContent;
	}

	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}

	public String getTopicMaterials() {
		return topicMaterials;
	}

	public void setTopicMaterials(String topicMaterials) {
		this.topicMaterials = topicMaterials;
	}

	public String getTopicAnswer() {
		return topicAnswer;
	}

	public void setTopicAnswer(String topicAnswer) {
		this.topicAnswer = topicAnswer;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void addTag(Tag tag) {
		tags.add(tag);
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public String getAnswerFile() {
		return answerFile;
	}

	public void setAnswerFile(String answerFile) {
		this.answerFile = answerFile;
	}

	public String getStemFile() {
		return stemFile;
	}

	public void setStemFile(String stemFile) {
		this.stemFile = stemFile;
	}

	public KnowledgePoint getKnowledgePoint() {
		return knowledgePoint;
	}

	public void setKnowledgePoint(KnowledgePoint knowledgePoint) {
		this.knowledgePoint = knowledgePoint;
	}

}
