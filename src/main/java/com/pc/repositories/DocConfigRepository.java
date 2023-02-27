package com.pc.repositories;


import com.pc.entities.DocConfig;
import com.pc.entities.lookup.DocumentLevel;
import com.pc.entities.lookup.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocConfigRepository extends JpaRepository<DocConfig, Integer> {

    DocConfig findById(Long parseLong);

    List<DocConfig> findByWorkflowAndDocumentLevelOrderByDescriptionAsc(Workflow workflow, DocumentLevel documentLevel);
    //public List<DocConfig> findByDescriptionStartingWith(String description);
}
