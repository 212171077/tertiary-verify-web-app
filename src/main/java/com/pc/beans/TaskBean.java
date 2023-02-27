package com.pc.beans;

import com.pc.entities.Task;
import com.pc.entities.User;
import com.pc.entities.lookup.Workflow;

import java.util.List;

public class TaskBean {

    private String taskDescription;
    private Workflow workflow;
    private Task previousTask;
    private Boolean sendEmail;
    private Boolean createTaskToSingleUser;
    private Integer workflowRolePosition;
    private User createUser;
    private String targetClass;
    private String targetKey;
    private List<User> userList;
    private Boolean createPreviousTask;

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public Task getPreviousTask() {
        return previousTask;
    }

    public void setPreviousTask(Task previousTask) {
        this.previousTask = previousTask;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public Boolean getCreateTaskToSingleUser() {
        return createTaskToSingleUser;
    }

    public void setCreateTaskToSingleUser(Boolean createTaskToSingleUser) {
        this.createTaskToSingleUser = createTaskToSingleUser;
    }

    public Integer getWorkflowRolePosition() {
        return workflowRolePosition;
    }

    public void setWorkflowRolePosition(Integer workflowRolePosition) {
        this.workflowRolePosition = workflowRolePosition;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Boolean getCreatePreviousTask() {
        return createPreviousTask;
    }

    public void setCreatePreviousTask(Boolean createPreviousTask) {
        this.createPreviousTask = createPreviousTask;
    }

}
