package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.CompositeKeys.SectionID;
import com.example.springTestProj.Entities.Section;
import com.example.springTestProj.Repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SectionService {

    @Autowired
    SectionRepository sectionRepository;

    public void saveSectionToRepository(Section section) {
        sectionRepository.save(section);
    }

    public Section createNewSection(String courseUUID, String sectionNum) {
        String sectionID = String.valueOf(UUID.randomUUID());
        SectionID newSectionID = new SectionID(sectionID, sectionNum);
        Section newSection = new Section(newSectionID, courseUUID);

        return newSection;
    }

    @Transactional
    public void addTestToSection(Section updatedSection) {
        // the updatedCourse already has all the new attributes added to it via addCourseController
        String sectionUUID = updatedSection.getSectionID()
                .getUuid();
        sectionRepository.deleteSectionBySectionID_Uuid(sectionUUID);  // delete existing vers of this course, preserving the uuid
        saveSectionToRepository(updatedSection);
    }

    public Section returnSectionBySectionAndCourseID(String sectionNum, String courseID) {
        return sectionRepository.findSectionBySectionID_NumberAndCourseUUID(sectionNum, courseID);
    }

    public boolean existsByCourseSection(String section, String courseUUID) {
        return sectionRepository.existsByCourseUUIDAndSectionID_Number(section, courseUUID);
    }

    public List<Section> readSections() {
        return sectionRepository.findAll();
    }

    public List<Section> findCourseSections(String courseID) {
        return sectionRepository.findSectionsByCourseUUID(courseID);
    }

}
