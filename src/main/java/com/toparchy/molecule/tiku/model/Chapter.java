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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CHAPTER_")
@XmlRootElement
public class Chapter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5834280507796759641L;
	@Id
	@Column(name = "ID_", length = 50)
	@GeneratedValue(generator = "chapter-uuid")
	@GenericGenerator(name = "chapter-uuid", strategy = "uuid")
	private String id;
	@Column(name = "NAME_", length = 100)
	private String name;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Course course;
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "chapter")
	@JsonIgnore
	@OrderBy("name asc")
	private Set<KnowledgePoint> knowledgePoints = new HashSet<KnowledgePoint>();

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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Set<KnowledgePoint> getKnowledgePoints() {
		return knowledgePoints;
	}

	public void setKnowledgePoints(Set<KnowledgePoint> knowledgePoints) {
		this.knowledgePoints = knowledgePoints;
	}

}
