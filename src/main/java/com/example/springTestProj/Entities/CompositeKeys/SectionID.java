package com.example.springTestProj.Entities.CompositeKeys;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@Embeddable
public class SectionID implements Serializable {
    private String uuid;
    private String number;

    public SectionID(String id, String number) {
        this.uuid = id;
        this.number = number;
    }

}
