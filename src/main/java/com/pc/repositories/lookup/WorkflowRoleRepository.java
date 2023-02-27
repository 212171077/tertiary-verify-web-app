package com.pc.repositories.lookup;


import com.pc.entities.enums.PermissionEnum;
import com.pc.entities.lookup.Workflow;
import com.pc.entities.lookup.WorkflowRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowRoleRepository extends JpaRepository<WorkflowRole, Integer> {
    String FIND_UPLOAD_WORKFLOWROLE = "SELECT o FROM WorkflowRole o WHERE (o.permission =?2 OR permission =?3) AND o.workflow =?1";

    WorkflowRole findById(Long parseLong);

    List<WorkflowRole> findByDescriptionStartingWith(String description);

    List<WorkflowRole> findByWorkflowOrderByPosition(Workflow wf);

    Integer countByWorkflow(Workflow workflow);

    @Query(FIND_UPLOAD_WORKFLOWROLE)
    List<WorkflowRole> getUploadWorkflowRoles(Workflow wf, PermissionEnum p1, PermissionEnum p2);
}
