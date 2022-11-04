package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.Course;
import com.example.springTestProj.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public void saveCourseToRepository(Course course){
        courseRepository.save(course);
        System.out.println("Course saved?");

    }

    public Course createCourse(String courseNumber, String section){
        String courseID = String.valueOf(UUID.randomUUID());
        Course newCourse = new Course(courseID, courseNumber);

        return newCourse;
    }


    public List<Course> readCourses(){
        return courseRepository.findAll();
    }


    public Course returnCourse(String course, String section){
        return courseRepository.findCoursesByCoursenameAndSection(course, section);
    }


    public Course returnCourseByCourseName(String course){
        return courseRepository.findCoursesByCoursename(course);
    }


//    @Transactional
//    public String deleteUser(User user){
//        if (courseRepository.existsByUserID(user.getUserID())){
//            try {
//                User userDelete = courseRepository.findByUserID(user.getUserID());
//                courseRepository.delete(userDelete);
//                return "User record deleted successfully.";
//            }catch (Exception e){
//                throw e;
//            }
//
//        }else {
//            return "User does not exist";
//        }
//    }
}
