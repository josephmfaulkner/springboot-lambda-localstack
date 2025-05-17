package com.restApi.service;

import org.springframework.stereotype.Service;

import com.restApi.dto.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    //RDS DB
    private final List<Course> courses = new ArrayList<>(
        List.of(
            new Course(101, "Intro to Thinking", 19.99),
            new Course(102, "Intro to Blinking", 2.00),
            new Course(103, "Intro to Stinking", 200.00),
            new Course(104, "Advanced Thinking", 1.50),
            new Course(105, "Remedial Thinking", 2.17)
        )
    );


    // Create a new course
    public void addCourse(Course course) {
        courses.add(course);
    }

    // Retrieve all courses
    public List<Course> getAllCourses() {
        return courses;
    }

    // Retrieve a course by id
    public Optional<Course> getCourseById(int id) {
        return courses.stream()
                .filter(course -> course.getId() == id)
                .findFirst();
    }

    // Update a course
    public boolean updateCourse(int id, Course newCourse) {
        return getCourseById(id).map(existingCourse -> {
            courses.remove(existingCourse);
            courses.add(newCourse);
            return true;
        }).orElse(false);
    }

    // Delete a course by id
    public boolean deleteCourse(int id) {
        return courses
                .removeIf(course -> course.getId() == id);
    }
}
