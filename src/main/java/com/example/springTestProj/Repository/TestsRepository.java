package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.CompositeKeys.SectionPrimaryKey;
import com.example.springTestProj.Entities.Section;
import com.example.springTestProj.Entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestsRepository extends JpaRepository<Test, String> {

}
