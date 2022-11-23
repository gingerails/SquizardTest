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
public class SectionID implements Serializable {
    @Column(name = "section_uuid")
    private String uuid;
    @Column(name = "section_num")
    private String number;

    public SectionID(String id, String number) {
        this.uuid = id;
        this.number = number;
    }

}
