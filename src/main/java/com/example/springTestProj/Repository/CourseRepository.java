package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.CompositeKeys.CoursesPrimaryKey;
import com.example.springTestProj.Entities.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Courses, CoursesPrimaryKey> {
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

    Courses findCoursesByCoursesPrimaryKey_CourseNum(String courseNum);

}
