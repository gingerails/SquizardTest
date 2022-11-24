package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.CompositeKeys.CoursesPrimaryKey;
import com.example.springTestProj.Entities.Courses;
import com.example.springTestProj.Repository.CourseRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;


    public void saveCourseToRepository(Courses courses){
        courseRepository.save(courses);
        System.out.println("Course saved?");
    }
    public Courses createCourse(String courseNumber){
        String courseID = String.valueOf(UUID.randomUUID());
        CoursesPrimaryKey coursesPrimaryKey = new CoursesPrimaryKey(courseID, courseNumber);
        Courses newCourse = new Courses(coursesPrimaryKey);

        return newCourse;
    }
    


    @Transactional
    public void addSectionsToExistingCourseAndSave(Courses updatedCourse){
        // the updatedCourse already has all the new attributes added to it via addCourseController
        String courseUUID = updatedCourse.getCoursesPrimaryKey().getCoursesUUID();
        courseRepository.deleteCoursesByCoursesPrimaryKey_CoursesUUID(courseUUID);  // delete existing vers of this course, preserving the uuid
        saveCourseToRepository(updatedCourse);
    }


    public List<Courses> readCourses(){
        return courseRepository.findAll();
    }

    public Courses returnCourseByCourseNum(String course){
        return courseRepository.findCoursesByCoursesPrimaryKey_CourseNum(course);
    }

    public boolean existsByCourseNum(String courseNum){
        return courseRepository.existsByCoursesPrimaryKey_CourseNum(courseNum);
    }
   
}
