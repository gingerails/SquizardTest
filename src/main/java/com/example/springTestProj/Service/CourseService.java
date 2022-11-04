package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.CompositeKeys.CoursesPrimaryKey;
import com.example.springTestProj.Entities.Courses;
import com.example.springTestProj.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

//    private final SectionService sectionService;
//
//    public CourseService(SectionService sectionService) {
//        this.sectionService = sectionService;
//    }

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


    public Courses createCourseWithSection(String courseNumber, String section){
        String courseID = String.valueOf(UUID.randomUUID());
        CoursesPrimaryKey coursesPrimaryKey = new CoursesPrimaryKey(courseID, courseNumber);
        Courses newCourse = new Courses(coursesPrimaryKey, section);

        return newCourse;
    }

    public void updateCourse(String courseNumber, String sections){
        String[] sectionsList = sections.split(",");
        for (String section:sectionsList) {

        }
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
