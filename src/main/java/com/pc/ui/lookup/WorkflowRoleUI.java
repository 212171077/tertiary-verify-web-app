package com.pc.ui.lookup;

import com.pc.entities.lookup.WorkflowRole;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.WorkflowRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("workflowRoleUI")
@ViewScoped
public class WorkflowRoleUI extends AbstractUI {

    @Autowired
    WorkflowRoleService workflowRoleService;
    private ArrayList<WorkflowRole> workflowRoleList;

    private WorkflowRole workflowRole;

    @PostConstruct
    public void init() {
        workflowRole = new WorkflowRole();
        workflowRoleList = (ArrayList<WorkflowRole>) findAllWorkflowRole();
    }

    public void saveWorkflowRole() {
        try {
            workflowRoleService.saveWorkflowRole(workflowRole);
            displayInfoMssg("Update Successful...!!");
            workflowRoleList = (ArrayList<WorkflowRole>) findAllWorkflowRole();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteWorkflowRole() {
        try {
            workflowRoleService.deleteWorkflowRole(workflowRole);
            displayWarningMssg("Update Successful...!!");
            workflowRoleList = (ArrayList<WorkflowRole>) findAllWorkflowRole();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<WorkflowRole> findAllWorkflowRole() {
        List<WorkflowRole> list = new ArrayList<>();
        try {
            list = workflowRoleService.findAllWorkflowRole();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<WorkflowRole> findAllWorkflowRolePageable() {
        Pageable p = null;
        try {
            return workflowRoleService.findAllWorkflowRole(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<WorkflowRole> findAllWorkflowRoleSort() {
        Sort s = null;
        List<WorkflowRole> list = new ArrayList<>();
        try {
            list = workflowRoleService.findAllWorkflowRole(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        workflowRole = new WorkflowRole();
    }


    public WorkflowRole getWorkflowRole() {
        return workflowRole;
    }

    public void setWorkflowRole(WorkflowRole workflowRole) {
        this.workflowRole = workflowRole;
    }

    public ArrayList<WorkflowRole> getWorkflowRoleList() {
        return workflowRoleList;
    }

    public void setWorkflowRoleList(ArrayList<WorkflowRole> workflowRoleList) {
        this.workflowRoleList = workflowRoleList;
    }

}
