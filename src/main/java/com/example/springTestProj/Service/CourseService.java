package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.CompositeKeys.CourseID;
import com.example.springTestProj.Entities.Course;
import com.example.springTestProj.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;


    public void saveCourseToRepository(Course course) {
        courseRepository.save(course);
        System.out.println("Course saved?");
    }

    public Course createCourse(String courseNumber) {
        String courseID = String.valueOf(UUID.randomUUID());
        CourseID coursesPrimaryKey = new CourseID(courseID, courseNumber);
        Course newCourse = new Course(coursesPrimaryKey);

        return newCourse;
    }


    @Transactional
    public void addSectionsToExistingCourseAndSave(Course updatedCourse) {
        // the updatedCourse already has all the new attributes added to it via addCourseController
        String courseUUID = updatedCourse.getCourseID()
                .getUuid();
        courseRepository.deleteCourseByCourseID_Uuid(courseUUID);  // delete existing vers of this course, preserving the uuid
        saveCourseToRepository(updatedCourse);
    }


    public List<Course> readCourses() {
        return courseRepository.findAll();
    }

    public Course returnCourseByCourseNum(String course) {
        return courseRepository.findCourseByCourseID_Number(course);
    }

    public boolean existsByCourseNum(String courseNum) {
        return courseRepository.existsByCourseID_Number(courseNum);
    }

}
