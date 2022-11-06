package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.CompositeKeys.SectionPrimaryKey;
import com.example.springTestProj.Entities.Section;
import com.example.springTestProj.Entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, SectionPrimaryKey> {

//    boolean existsBySectionNum(String sectionNum);
//    boolean existsBySectionPrimaryKey_SectionNum(String sectionNum);
//    boolean existsByCourseUUIDAndSectionNum(String courseUUID, String sectionNum);

    boolean existsByCourseUUIDAndSectionPrimaryKey_SectionNum(String courseID, String sectionNum);
  //  Section findBySectionNumAndCourseUUID(String sectionNum, String courseUUID);

  //  List<Section> findSectionsByCourseUUID(String courseUUID);
//    List<Section> findSectionsByCourseUUID(String courseUUID);


    //  Section findSectionBySectionname(String Sectionname);
}
