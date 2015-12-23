package com.toparchy.molecule.permission.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS_APPLICATION_ROLE")
@XmlRootElement
public class ApplicationRole implements Serializable {
	private static final long serialVersionUID = 5264853077631884414L;
	@Id
	@Column(name = "SYS_APPLICATION_ROLE_ID", length = 50)
	@GeneratedValue(generator = "applicationrole-uuid")
	@GenericGenerator(name = "applicationrole-uuid", strategy = "uuid")
	private String id;
	@Column(name = "SYS_APPLICATION_ROLE_KEY", length = 255)
	private String key;
	@Column(name = "SYS_APPLICATION_ROLE_NAME", length = 255)
	private String name;
	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@OrderBy("key ASC")
	private Set<ApplicationResource> applicationResources = new HashSet<ApplicationResource>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ApplicationResource> getApplicationResources() {
		return applicationResources;
	}

	public void addApplicationResource(ApplicationResource applicationResource) {
		applicationResources.add(applicationResource);
	}

	public void removeApplicationResource(ApplicationResource applicationResource) {
		applicationResources.remove(applicationResource);
	}

	public void setApplicationResources(Set<ApplicationResource> applicationResources) {
		this.applicationResources = applicationResources;
	}

}
