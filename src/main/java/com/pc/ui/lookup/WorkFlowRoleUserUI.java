package com.pc.ui.lookup;

import com.pc.entities.lookup.WorkFlowRoleUser;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.WorkFlowRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("workFlowRoleUserUI")
@ViewScoped
public class WorkFlowRoleUserUI extends AbstractUI {

    @Autowired
    WorkFlowRoleUserService workFlowRoleUserService;
    private ArrayList<WorkFlowRoleUser> workFlowRoleUserList;

    private WorkFlowRoleUser workFlowRoleUser;

    @PostConstruct
    public void init() {
        workFlowRoleUser = new WorkFlowRoleUser();
        workFlowRoleUserList = (ArrayList<WorkFlowRoleUser>) findAllWorkFlowRoleUser();
    }

    public void saveWorkFlowRoleUser() {
        try {
            workFlowRoleUserService.saveWorkFlowRoleUser(workFlowRoleUser);
            displayInfoMssg("Update Successful...!!");
            workFlowRoleUserList = (ArrayList<WorkFlowRoleUser>) findAllWorkFlowRoleUser();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteWorkFlowRoleUser() {
        try {
            workFlowRoleUserService.deleteWorkFlowRoleUser(workFlowRoleUser);
            displayWarningMssg("Update Successful...!!");
            workFlowRoleUserList = (ArrayList<WorkFlowRoleUser>) findAllWorkFlowRoleUser();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<WorkFlowRoleUser> findAllWorkFlowRoleUser() {
        List<WorkFlowRoleUser> list = new ArrayList<>();
        try {
            list = workFlowRoleUserService.findAllWorkFlowRoleUser();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<WorkFlowRoleUser> findAllWorkFlowRoleUserPageable() {
        Pageable p = null;
        try {
            return workFlowRoleUserService.findAllWorkFlowRoleUser(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<WorkFlowRoleUser> findAllWorkFlowRoleUserSort() {
        Sort s = null;
        List<WorkFlowRoleUser> list = new ArrayList<>();
        try {
            list = workFlowRoleUserService.findAllWorkFlowRoleUser(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        workFlowRoleUser = new WorkFlowRoleUser();
    }


    public WorkFlowRoleUser getWorkFlowRoleUser() {
        return workFlowRoleUser;
    }

    public void setWorkFlowRoleUser(WorkFlowRoleUser workFlowRoleUser) {
        this.workFlowRoleUser = workFlowRoleUser;
    }

    public ArrayList<WorkFlowRoleUser> getWorkFlowRoleUserList() {
        return workFlowRoleUserList;
    }

    public void setWorkFlowRoleUserList(ArrayList<WorkFlowRoleUser> workFlowRoleUserList) {
        this.workFlowRoleUserList = workFlowRoleUserList;
    }

}
