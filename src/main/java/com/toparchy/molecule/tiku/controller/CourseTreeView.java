package com.toparchy.molecule.tiku.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.toparchy.molecule.tiku.data.ChapterRepository;
import com.toparchy.molecule.tiku.data.CourseRepository;
import com.toparchy.molecule.tiku.data.KnowledgePointRepository;
import com.toparchy.molecule.tiku.data.TopicRepository;
import com.toparchy.molecule.tiku.model.Chapter;
import com.toparchy.molecule.tiku.model.Course;
import com.toparchy.molecule.tiku.model.KnowledgePoint;
import com.toparchy.molecule.tiku.model.Topic;
import com.toparchy.molecule.tiku.service.CourseRegistration;

@Model
@ViewScoped
public class CourseTreeView implements Serializable {

	private static final long serialVersionUID = 4979209521004256054L;
	@Inject
	private CourseRepository courseRepository;
	@Inject
	private ChapterRepository chapterRepository;
	@Inject
	private KnowledgePointRepository knowledgePointRepository;
	@Inject
	private TopicRepository topicRepository;
	// private List<KnowledgePoint> knowledgePoints;
	private List<Topic> topics;
	private TreeNode root;
	private TreeNode selectedNode;
	private boolean deleteCourseDisabled = true;
	private Course selectCourse;
	@Inject
	private CourseRegistration courseRegistration;
	@Produces
	@Named
	private Course newCourse;

	public Course getNewCourse() {
		return newCourse;
	}

	public void setNewCourse(Course newCourse) {
		this.newCourse = newCourse;
	}

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

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	// public List<KnowledgePoint> getKnowledgePoints() {
	// return knowledgePoints;
	// }
	//
	// public void setKnowledgePoints(List<KnowledgePoint> knowledgePoints) {
	// this.knowledgePoints = knowledgePoints;
	// }

	@PostConstruct
	public void init() {
		newCourse = new Course();
		root = new DefaultTreeNode("Root", null);
		for (Course course : courseRepository.findAllOrderByName()) {
			TreeNode courseNode = new DefaultTreeNode(course, root);
			for (Chapter chapter : chapterRepository.findByCourse(course.getId())) {
				TreeNode chapterNode = new DefaultTreeNode(chapter, courseNode);
				for (KnowledgePoint knowledgePoint : knowledgePointRepository.findByChapter(chapter.getId())) {
					chapterNode.getChildren().add(new DefaultTreeNode(knowledgePoint));
				}
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
		if (event.getTreeNode().getData() instanceof Course) {
			deleteCourseDisabled = false;
			selectCourse = (Course) event.getTreeNode().getData();
		}
		if (event.getTreeNode().getData() instanceof KnowledgePoint) {
			topics = topicRepository
					.findTopicByKnowledgePointId(((KnowledgePoint) event.getTreeNode().getData()).getId());
		}
	}

	public void onNodeUnselect(NodeUnselectEvent event) {
		deleteCourseDisabled = true;
	}

	public void deleteCourse() {
		courseRegistration.delete(selectCourse);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "删除课程", selectCourse.getName());
		FacesContext.getCurrentInstance().addMessage(null, message);
		init();
	}

	public void openAddCourseDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("draggable", false);
		options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog("addCourse", options, null);
	}

	public void onAddCourse(SelectEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "新增课程",
				((Course) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, message);
		init();
	}

	public void openAddChapterDialog(ActionEvent event) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("draggable", false);
		options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog("addChapter", options, null);
	}

	public void onAddChapter(SelectEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "新增章节",
				((Chapter) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, message);
		init();
	}

	public void openAddKnowledgePointDialog(ActionEvent event) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("resizable", false);
		options.put("draggable", false);
		options.put("modal", true);
		RequestContext.getCurrentInstance().openDialog("addKnowledgePoint", options, null);
	}

	public void onAddKnowledgePoint(SelectEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "新增知识点",
				((KnowledgePoint) event.getObject()).getName());
		FacesContext.getCurrentInstance().addMessage(null, message);
		init();
	}

	public void addCourse() {
		try {
			courseRegistration.register(newCourse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().closeDialog(newCourse);
	}
}
