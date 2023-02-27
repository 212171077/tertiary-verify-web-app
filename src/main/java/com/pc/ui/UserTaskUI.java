package com.pc.ui;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.UserTask;
import com.pc.framework.AbstractUI;
import com.pc.service.UserTaskService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("userTaskUI")
@ViewScoped
public class UserTaskUI extends AbstractUI {

    @Autowired
    UserTaskService userTaskService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<UserTask> userTaskList;
    private UserTask userTask;
    private LazyDataModel<UserTask> dataModel;

    @PostConstruct
    public void init() {
        userTask = new UserTask();
        loadUserTaskInfo();
    }

    public void saveUserTask() {
        try {
            userTaskService.saveUserTask(userTask);
            displayInfoMssg("Update Successful...!!");
            loadUserTaskInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteUserTask() {
        try {
            userTaskService.deleteUserTask(userTask);
            displayWarningMssg("Update Successful...!!");
            loadUserTaskInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<UserTask> findAllUserTask() {
        List<UserTask> list = new ArrayList<>();
        try {
            list = userTaskService.findAllUserTask();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<UserTask> findAllUserTaskPageable() {
        Pageable p = null;
        try {
            return userTaskService.findAllUserTask(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<UserTask> findAllUserTaskSort() {
        Sort s = null;
        List<UserTask> list = new ArrayList<>();
        try {
            list = userTaskService.findAllUserTask(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        userTask = new UserTask();
    }

    public void loadUserTaskInfo() {
        dataModel = new LazyDataModel<UserTask>() {

            private static final long serialVersionUID = 1L;
            private List<UserTask> list = new ArrayList<UserTask>();

            @Override
            public List<UserTask> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<UserTask>) entityDAOFacade.getResultList(UserTask.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, UserTask.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(UserTask obj) {
                return obj.getId();
            }

            @Override
            public UserTask getRowData(String rowKey) {
                for (UserTask obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public UserTask getUserTask() {
        return userTask;
    }

    public void setUserTask(UserTask userTask) {
        this.userTask = userTask;
    }

    public ArrayList<UserTask> getUserTaskList() {
        return userTaskList;
    }

    public void setUserTaskList(ArrayList<UserTask> userTaskList) {
        this.userTaskList = userTaskList;
    }

    public LazyDataModel<UserTask> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<UserTask> dataModel) {
        this.dataModel = dataModel;
    }


}
