package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCourseID(String CourseID);

    Course findByCourseID(String CourseID);

    Course findCoursesByCoursenameAndSection(String Coursename, String password);

    Course findCoursesByCoursename(String Coursename);
}
