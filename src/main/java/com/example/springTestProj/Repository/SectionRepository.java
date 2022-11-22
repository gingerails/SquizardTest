package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.CompositeKeys.SectionID;
import com.example.springTestProj.Entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, SectionID> {

//    boolean existsBySectionNum(String sectionNum);
//    boolean existsBySectionPrimaryKey_SectionNum(String sectionNum);
//    boolean existsByCourseUUIDAndSectionNum(String courseUUID, String sectionNum);

    boolean existsByCourseUUIDAndSectionPrimaryKey_SectionNum(String courseID, String sectionNum);
    //  Section findBySectionNumAndCourseUUID(String sectionNum, String courseUUID);

    Section findBySectionPrimaryKey_SectionNum(String sectionNum);

    Section findSectionByCourseUUIDAndSectionPrimaryKey_SectionNum(String courseID, String sectionNum);

    Section findSectionBySectionPrimaryKeySectionNumAndAndCourseUUID(String sectionNum, String courseID);

    List<Section> findSectionsByCourseUUID(String courseUUID);
//    List<Section> findSectionsByCourseUUID(String courseUUID);

    //    void deleteCoursesByCoursesPrimaryKey_CoursesUUID(String courseID); // delete coursenum is used to update a course and add new sections
    void deleteSectionBySectionPrimaryKeySectionUUID(String sectionID); // delete coursenum is used to update a course and add new sections

    //  Section findSectionBySectionname(String Sectionname);
}
