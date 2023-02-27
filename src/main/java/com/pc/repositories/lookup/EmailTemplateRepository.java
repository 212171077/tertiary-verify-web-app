package com.pc.repositories.lookup;


import com.pc.entities.lookup.EmailTemplate;
import com.pc.entities.lookup.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Integer> {

    EmailTemplate findById(Long parseLong);

    List<EmailTemplate> findByDescriptionStartingWith(String description);

    List<EmailTemplate> findByWorkflow(Workflow wf);
}
