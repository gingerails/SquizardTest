package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.CompositeKeys.SectionPrimaryKey;
import com.example.springTestProj.Entities.Section;
import com.example.springTestProj.Repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class SectionService {

    @Autowired
    SectionRepository sectionRepository;

    public void saveSectionToRepository(Section section){
        sectionRepository.save(section);
    }

    public Section createNewSection(String courseUUID, String sectionNum){
        String sectionID = String.valueOf(UUID.randomUUID());
        SectionPrimaryKey newSectionPrimaryKey = new SectionPrimaryKey(sectionID, sectionNum);
        Section newSection = new Section(newSectionPrimaryKey, courseUUID);

        return newSection;
    }

//    public Section returnSectionBySectionNum(String sectionNum){
//        return sectionRepository.findBySectionPrimaryKey_SectionNum(sectionNum);
//    }
    public Section returnSectionBySectionAndCourseID(String sectionNum, String courseID){
        return sectionRepository.findSectionBySectionPrimaryKeySectionNumAndAndCourseUUID(sectionNum, courseID);
    }
    public boolean existsByCourseSection(String section, String courseUUID){
        return sectionRepository.existsByCourseUUIDAndSectionPrimaryKey_SectionNum(section, courseUUID);
    }
   // public bool checkExistingSections(String )
    public List<Section> readSections(){
        return sectionRepository.findAll();
    }
}
