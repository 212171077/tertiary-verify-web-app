package com.pc.service.lookup;

import com.pc.entities.lookup.WorkFlowRoleUser;
import com.pc.entities.lookup.WorkflowRole;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.WorkFlowRoleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WorkFlowRoleUserService extends AbstractService {
    @Autowired
    WorkFlowRoleUserRepository repository;

    public void saveWorkFlowRoleUser(WorkFlowRoleUser workFlowRoleUser) throws Exception {
        if (workFlowRoleUser.getId() == null) {
            List<WorkFlowRoleUser> list = repository.findByWorkflowRoleAndUserRole(workFlowRoleUser.getWorkflowRole(), workFlowRoleUser.getUserRole());
            if (list != null && list.size() > 0) {
                throw new Exception("The user is already added");
            }
            workFlowRoleUser.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                workFlowRoleUser.setLastUpdateUser(getCurrentUser());
            }
            workFlowRoleUser.setLastUpdateDate(new Date());
        }
        repository.save(workFlowRoleUser);
    }

    public void deleteWorkFlowRoleUser(WorkFlowRoleUser workFlowRoleUser) throws Exception {
        repository.delete(workFlowRoleUser);
    }

    public void deleteWorkFlowRoleUserByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<WorkFlowRoleUser> findAllWorkFlowRoleUser() throws Exception {
        return repository.findAll();
    }

    public Page<WorkFlowRoleUser> findAllWorkFlowRoleUser(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<WorkFlowRoleUser> findAllWorkFlowRoleUser(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public WorkFlowRoleUser findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

    public List<WorkFlowRoleUser> findByWorkflowRole(WorkflowRole wfr) throws Exception {
        // TODO Auto-generated method stub
        return repository.findByWorkflowRole(wfr);
    }


}
