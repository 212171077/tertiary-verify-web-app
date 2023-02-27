package com.pc.repositories.lookup;


import com.pc.entities.lookup.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Integer> {

    Workflow findById(Long parseLong);

    List<Workflow> findByDescriptionStartingWith(String description);
}
