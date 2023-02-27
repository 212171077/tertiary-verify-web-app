package com.pc.repositories.lookup;


import com.pc.entities.UserRole;
import com.pc.entities.lookup.WorkFlowRoleUser;
import com.pc.entities.lookup.WorkflowRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkFlowRoleUserRepository extends JpaRepository<WorkFlowRoleUser, Integer> {
    WorkFlowRoleUser findById(Long parseLong);

    List<WorkFlowRoleUser> findByWorkflowRoleAndUserRole(WorkflowRole workflowRole, UserRole userRole);

    List<WorkFlowRoleUser> findByWorkflowRole(WorkflowRole wfr);
}
