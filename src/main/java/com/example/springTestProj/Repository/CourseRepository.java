package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.CompositeKeys.CourseID;
import com.example.springTestProj.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, CourseID> {
    // @Override
    // boolean existsById(CoursesPrimaryKey coursesPrimary);
    //    boolean existsByCoursesUUID(String courseID);
    boolean existsByCoursesPrimaryKey_CoursesUUID(String courseID);

    boolean existsByCoursesPrimaryKey_CourseNum(String courseNum);

    void deleteCoursesByCoursesPrimaryKey_CoursesUUID(String courseID); // delete coursenum is used to update a course and add new sections
    // boolean existsByCoursesNum(String courseNum);
//    boolean existsByCoursesNumAndSections(String courseNum, String sections);

    //  boolean existsByCoursesPrimaryKey
//    Courses findByCoursesUUID(String coursesUUID);

    // List<Courses> readCoursesBy
    // Courses findCoursesByCoursesNumAndSections(String courseNum, String sections);

    Course findCoursesByCoursesPrimaryKey_CourseNum(String courseNum);

}
