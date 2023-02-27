package com.pc.service.lookup;

import com.pc.entities.lookup.Workflow;
import com.pc.entities.lookup.WorkflowRole;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WorkflowService extends AbstractService {
    @Autowired
    WorkflowRepository repository;

    @Autowired
    WorkflowRoleService workflowRoleService;

    @Autowired
    EmailTemplateService emailTemplateService;

    public void saveWorkflow(Workflow workflow) throws Exception {
        if (workflow.getId() == null) {
            workflow.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                workflow.setLastUpdateUser(getCurrentUser());
            }
            workflow.setLastUpdateDate(new Date());
        }
        repository.save(workflow);
    }

    public void deleteWorkflow(Workflow workflow) throws Exception {
        List<WorkflowRole> linkedObjList = workflowRoleService.findByWorkflowOrderByPosition(workflow);
        if (linkedObjList != null && linkedObjList.size() > 0) {
            throw new Exception("This workflow is linked to one or more workflow roles, please remove them first");
        }
        repository.delete(workflow);
    }

    public void deleteWorkflowByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<Workflow> findAllWorkflow() throws Exception {
        return repository.findAll();
    }


    public List<Workflow> findAllWorkflowResolveWorkflowRoles() throws Exception {
        List<Workflow> list = repository.findAll();
        for (Workflow wf : list) {
            wf.setWorkflowRoleRoleList(workflowRoleService.findByWorkflowOrderByPosition(wf));
            wf.setEmailTemplateList(emailTemplateService.findByWorkflow(wf));
        }
        return list;
    }

    public Page<Workflow> findAllWorkflow(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<Workflow> findAllWorkflow(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public Workflow findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

    public List<Workflow> findByDescriptionStartingWith(String description) throws Exception {
        return repository.findByDescriptionStartingWith(description);
    }

    public Workflow resolveWorkflowData(Workflow workflow) throws Exception {
        workflow.setWorkflowRoleRoleList(workflowRoleService.findByWorkflowAndResolveUser(workflow));
        workflow.setEmailTemplateList(emailTemplateService.findByWorkflow(workflow));
        return workflow;
    }


}
