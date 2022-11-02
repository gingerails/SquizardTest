package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.Courses;
import com.example.springTestProj.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.UUID;

public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public void saveCourseToRepository(Courses courses){
        courseRepository.save(courses);
        System.out.println("Course saved?");

    }

    public Courses createCourse(String courseNumber, String courseSection){
        String courseID = String.valueOf(UUID.randomUUID());
        Courses newCourse = new Courses(courseID, courseNumber, courseSection);

        return newCourse;
    }


    public List<Courses> readCourses(){
        return courseRepository.findAll();
    }


//    public User returnUser(String username, String password){
//        return courseRepository.findUsersByUsernameAndPassword(username, password);
//    }


//    public User returnUserByUsername(String username){
//        return courseRepository.findUsersByUsername(username);
//    }


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
