package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.CompositeKeys.CoursesPrimaryKey;
import com.example.springTestProj.Entities.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Courses, CoursesPrimaryKey> {

//    boolean existsByCoursesUUID(String courseID);
    boolean existsByCoursesPrimaryKey_CoursesUUID(String courseID);
    boolean existsByCoursesPrimaryKey_CourseNum(String courseNum);
   // boolean existsByCoursesNum(String courseNum);
//    boolean existsByCoursesNumAndSections(String courseNum, String sections);

  //  boolean existsByCoursesPrimaryKey
//    Courses findByCoursesUUID(String coursesUUID);

//    Courses findCoursesByCoursesNumAndSections(String courseNum, String sections);

    Courses findCoursesByCoursesPrimaryKey_CourseNum(String courseNum);

}
