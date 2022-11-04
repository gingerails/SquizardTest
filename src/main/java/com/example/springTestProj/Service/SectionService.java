package com.example.springTestProj.Service;

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
        Section newSection = new Section(sectionID, courseUUID, sectionNum);

        return newSection;
    }
    public boolean existsByCourseSection(String section, String courseUUID){
        return sectionRepository.existsByCourseUUIDAndSectionPrimaryKey_SectionNum(section, courseUUID);
    }
   // public bool checkExistingSections(String )
    public List<Section> readSections(){
        return sectionRepository.findAll();
    }
}
