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
        courseRepository.deleteCoursesByCoursesPrimaryKey_CoursesUUID(courseUUID);  // delete existing vers of this course, preserving the uuid
        saveCourseToRepository(updatedCourse);
    }


    public List<Course> readCourses() {
        return courseRepository.findAll();
    }

    public Course returnCourseByCourseNum(String course) {
        return courseRepository.findCoursesByCoursesPrimaryKey_CourseNum(course);
    }

    public boolean existsByCourseNum(String courseNum) {
        return courseRepository.existsByCoursesPrimaryKey_CourseNum(courseNum);
    }

//    public boolean existsByCourseNumAndSection(String courseNum, String section){
//        return courseRepository.existsByCoursesNumAndSection(courseNum,section);
//    }

//
//    String[] sectionsList = sections.split(",");
//        for (String section:sectionsList) {
//        Courses existingCourse = courseRepository.findCoursesByCoursesNum(courseNum);
//        String courseUUID = existingCourse.getCoursesUUID();
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
