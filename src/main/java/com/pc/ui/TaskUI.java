package com.pc.ui;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.Task;
import com.pc.framework.AbstractUI;
import com.pc.service.TaskService;
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

@Component("taskUI")
@ViewScoped
public class TaskUI extends AbstractUI {

    @Autowired
    TaskService taskService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<Task> taskList;
    private Task task;
    private LazyDataModel<Task> dataModel;

    @PostConstruct
    public void init() {
        task = new Task();
        loadTaskInfo();
    }

    public void saveTask() {
        try {
            taskService.saveTask(task);
            displayInfoMssg("Task added successful...!!");
            loadTaskInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteTask() {
        try {
            taskService.deleteTask(task);
            displayWarningMssg("Task deleted successful...!!");
            loadTaskInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Task> findAllTask() {
        List<Task> list = new ArrayList<>();
        try {
            list = taskService.findAllTask();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<Task> findAllTaskPageable() {
        Pageable p = null;
        try {
            return taskService.findAllTask(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Task> findAllTaskSort() {
        Sort s = null;
        List<Task> list = new ArrayList<>();
        try {
            list = taskService.findAllTask(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        task = new Task();
    }

    public void loadTaskInfo() {
        dataModel = new LazyDataModel<Task>() {

            private static final long serialVersionUID = 1L;
            private List<Task> list = new ArrayList<Task>();

            @Override
            public List<Task> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<Task>) entityDAOFacade.getResultList(Task.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, Task.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(Task obj) {
                return obj.getId();
            }

            @Override
            public Task getRowData(String rowKey) {
                for (Task obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public LazyDataModel<Task> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<Task> dataModel) {
        this.dataModel = dataModel;
    }


}
