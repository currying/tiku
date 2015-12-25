package com.toparchy.molecule.tiku.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.toparchy.molecule.tiku.data.CourseRepository;
import com.toparchy.molecule.tiku.data.KnowledgePointRepository;
import com.toparchy.molecule.tiku.model.Chapter;
import com.toparchy.molecule.tiku.model.Course;
import com.toparchy.molecule.tiku.model.KnowledgePoint;
import com.toparchy.molecule.tiku.service.CourseRegistration;

@Model
@ViewScoped
public class CourseTreeView implements Serializable {

	private static final long serialVersionUID = 4979209521004256054L;
	@Inject
	private CourseRepository courseRepository;
	@Inject
	private KnowledgePointRepository knowledgePointRepository;
	private List<KnowledgePoint> knowledgePoints;
	private TreeNode root;
	private TreeNode selectedNode;
	private boolean deleteCourseDisabled = true;
	@Produces
	@Named
	private Course selectCourse;
	@Inject
	private CourseRegistration courseRegistration;

	public void setSelectCourse(Course selectCourse) {
		this.selectCourse = selectCourse;
	}

	public Course getSelectCourse() {
		return selectCourse;
	}

	public boolean isDeleteCourseDisabled() {
		return deleteCourseDisabled;
	}

	public void setDeleteCourseDisabled(boolean deleteCourseDisabled) {
		this.deleteCourseDisabled = deleteCourseDisabled;
	}

	public List<KnowledgePoint> getKnowledgePoints() {
		return knowledgePoints;
	}

	public void setKnowledgePoints(List<KnowledgePoint> knowledgePoints) {
		this.knowledgePoints = knowledgePoints;
	}

	@PostConstruct
	public void init() {
		root = new DefaultTreeNode("Root", null);
		for (Course course : courseRepository.findAllOrderByName()) {
			TreeNode children = new DefaultTreeNode(course, root);
			for (Chapter chapter : course.getChapters()) {
				children.getChildren().add(new DefaultTreeNode(chapter));
			}
		}
	}

	public TreeNode getRoot() {
		return root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public void queryKnowledgePointListByChapter(String chapterId) {

	}

	public void onNodeSelect(NodeSelectEvent event) {
		if (event.getTreeNode().getData() instanceof Course)
			deleteCourseDisabled = false;
		if (event.getTreeNode().getData() instanceof Chapter)
			knowledgePoints = knowledgePointRepository.findByChapter(((Chapter) event.getTreeNode().getData()).getId());
	}

	public void onNodeUnselect(NodeUnselectEvent event) {
		deleteCourseDisabled = true;
	}

	public void deleteCourse() {
		courseRegistration.delete(selectCourse);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "删除课程", selectCourse.getName());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
