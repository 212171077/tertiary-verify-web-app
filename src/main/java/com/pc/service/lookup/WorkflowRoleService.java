package com.pc.service.lookup;

import com.pc.entities.enums.PermissionEnum;
import com.pc.entities.lookup.WorkFlowRoleUser;
import com.pc.entities.lookup.Workflow;
import com.pc.entities.lookup.WorkflowRole;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.WorkflowRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WorkflowRoleService extends AbstractService {
    @Autowired
    WorkflowRoleRepository repository;

    @Autowired
    WorkFlowRoleUserService workflowRoleUserService;

    public void saveWorkflowRole(WorkflowRole workflowRole) throws Exception {
        if (workflowRole.getId() == null) {
            if (workflowRole.getWorkflow() != null) {
                workflowRole.setPosition(countByWorkflow(workflowRole.getWorkflow()) + 1);
            } else {
                if (getCurrentUser() != null) {
                    workflowRole.setLastUpdateUser(getCurrentUser());
                }
                workflowRole.setLastUpdateDate(new Date());
            }
            workflowRole.setCreateDate(new Date());
        }
        repository.save(workflowRole);
    }

    private Integer countByWorkflow(Workflow workflow) {
        return repository.countByWorkflow(workflow);
    }

    public void deleteWorkflowRole(WorkflowRole workflowRole) throws Exception {
        List<WorkFlowRoleUser> listWorkflowRoleUser = workflowRoleUserService.findByWorkflowRole(workflowRole);
        if (listWorkflowRoleUser != null && listWorkflowRoleUser.size() > 0) {
            throw new Exception("This workflow role is linked to one or more users, revome them first");
        }
        repository.delete(workflowRole);
    }

    public void deleteWorkflowRoleByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<WorkflowRole> findAllWorkflowRole() throws Exception {
        return repository.findAll();
    }

    public Page<WorkflowRole> findAllWorkflowRole(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<WorkflowRole> findAllWorkflowRole(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public WorkflowRole findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

    public List<WorkflowRole> findByDescriptionStartingWith(String description) throws Exception {
        return repository.findByDescriptionStartingWith(description);
    }

    public List<WorkflowRole> findByWorkflowOrderByPosition(Workflow wf) throws Exception {
        return repository.findByWorkflowOrderByPosition(wf);
    }

    public List<WorkflowRole> getUploadWorkflowRoles(Workflow wf, PermissionEnum p1, PermissionEnum p2) {
        return repository.getUploadWorkflowRoles(wf, p1, p2);
    }

    public List<WorkflowRole> findByWorkflowAndResolveUser(Workflow wf) throws Exception {
        List<WorkflowRole> list = findByWorkflowOrderByPosition(wf);
        for (WorkflowRole wfr : list) {
            wfr.setWorkflowRoleUserList(workflowRoleUserService.findByWorkflowRole(wfr));
        }

        return list;
    }


}
