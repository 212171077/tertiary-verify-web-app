package com.pc.ui.lookup;

import com.pc.entities.lookup.Workflow;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("workflowUI")
@ViewScoped
public class WorkflowUI extends AbstractUI {

    @Autowired
    WorkflowService workflowService;
    private ArrayList<Workflow> workflowList;

    private Workflow workflow;

    @PostConstruct
    public void init() {
        workflow = new Workflow();
        workflowList = (ArrayList<Workflow>) findAllWorkflow();
    }

    public void saveWorkflow() {
        try {
            workflowService.saveWorkflow(workflow);
            displayInfoMssg("Update Successful...!!");
            workflowList = (ArrayList<Workflow>) findAllWorkflow();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteWorkflow() {
        try {
            workflowService.deleteWorkflow(workflow);
            displayWarningMssg("Update Successful...!!");
            workflowList = (ArrayList<Workflow>) findAllWorkflow();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Workflow> findAllWorkflow() {
        List<Workflow> list = new ArrayList<>();
        try {
            list = workflowService.findAllWorkflow();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<Workflow> findAllWorkflowPageable() {
        Pageable p = null;
        try {
            return workflowService.findAllWorkflow(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Workflow> findAllWorkflowSort() {
        Sort s = null;
        List<Workflow> list = new ArrayList<>();
        try {
            list = workflowService.findAllWorkflow(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        workflow = new Workflow();
    }


    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public ArrayList<Workflow> getWorkflowList() {
        return workflowList;
    }

    public void setWorkflowList(ArrayList<Workflow> workflowList) {
        this.workflowList = workflowList;
    }

}
