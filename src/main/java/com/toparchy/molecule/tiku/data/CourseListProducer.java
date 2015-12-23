package com.toparchy.molecule.tiku.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.toparchy.molecule.tiku.model.Course;

@RequestScoped
public class CourseListProducer {
	@Inject
	private CourseRepository courseRepository;

	private List<Course> courses;

	@Produces
	@Named
	public List<Course> getCourses() {
		return courses;
	}

	public void onCourseListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Course course) {
		retrieveAllCoursesOrderedByName();
	}

	@PostConstruct
	public void retrieveAllCoursesOrderedByName() {
		courses = courseRepository.findAllOrderByName();
	}
}
