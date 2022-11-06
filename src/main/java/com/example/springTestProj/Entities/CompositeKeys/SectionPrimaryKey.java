package com.example.springTestProj.Entities.CompositeKeys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class SectionPrimaryKey implements Serializable {
    @Column(name = "SectionUUID")
    private String sectionUUID;
    @Column(name = "section_num")
    private String sectionNum;
}
