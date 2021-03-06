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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "COURSE_")
@XmlRootElement
public class Course implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5315810119516507304L;
	@Id
	@Column(name = "ID_", length = 50)
	@GeneratedValue(generator = "course-uuid")
	@GenericGenerator(name = "course-uuid", strategy = "uuid")
	private String id;
	@Column(name = "NAME_", length = 100)
	private String name;
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "course")
	@JsonIgnore
	@OrderBy("name asc")
	private Set<Chapter> chapters = new HashSet<Chapter>();

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

	public Set<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(Set<Chapter> chapters) {
		this.chapters = chapters;
	}

}
