package com.example.springTestProj.Entities.CompositeKeys;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@Embeddable
public class SectionPrimaryKey implements Serializable {

    @Column(name = "section_uuid")
    private String sectionUUID;
    @Column(name = "section_num")
    private String sectionNum;

    public SectionPrimaryKey(String sectionUUID, String sectionNum) {
        this.sectionUUID = sectionUUID;
        this.sectionNum = sectionNum;
    }

}
