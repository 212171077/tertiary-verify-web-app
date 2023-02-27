package com.pc.service;

import com.pc.beans.Mail;
import com.pc.beans.TaskBean;
import com.pc.constants.AppConstants;
import com.pc.entities.Task;
import com.pc.entities.User;
import com.pc.entities.UserRole;
import com.pc.entities.UserTask;
import com.pc.entities.enums.TastStatusEnum;
import com.pc.entities.lookup.WorkFlowRoleUser;
import com.pc.entities.lookup.Workflow;
import com.pc.entities.lookup.WorkflowRole;
import com.pc.framework.AbstractService;
import com.pc.mail.MailSender;
import com.pc.repositories.TaskRepository;
import com.pc.service.lookup.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class TaskService extends AbstractService {
    @Autowired
    TaskRepository repository;

    @Autowired
    WorkflowService workflowService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    UserTaskService userTaskService;

    @Autowired
    MailSender mailSender;

    public void saveTask(Task task) throws Exception {
        if (task.getId() == null) {
            task.setStatus(TastStatusEnum.open);
            task.setCreateDate(new Date());
        } else {

            if (getCurrentUser() != null) {
                task.setLastUpdateUser(getCurrentUser());
            }
            task.setLastUpdateDate(new Date());
        }
        repository.save(task);
    }

    public void deleteTask(Task task) throws Exception {
        repository.delete(task);
    }

    public void deleteTaskByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<Task> findAllTask() throws Exception {
        return repository.findAll();
    }

    public Page<Task> findAllTask(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<Task> findAllTask(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public Task findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

    public void createTaskUser(Task task, TaskBean taskBean) throws Exception {
        List<User> userList = task.getUserList();
        if (taskBean.getCreateTaskToSingleUser()) {
            User u = getRandomUser(userList);
            UserTask ut = new UserTask(u, task);
            userTaskService.saveUserTask(ut);
            if (taskBean.getSendEmail()) {
                taskNotificationEmail(u, task);
            }
        } else {
            for (User u : userList) {
                UserTask ut = new UserTask(u, task);
                userTaskService.saveUserTask(ut);
                if (taskBean.getSendEmail()) {
                    taskNotificationEmail(u, task);
                }
            }
        }
    }

    public void createTask(TaskBean taskBean) throws Exception {
        Task task = new Task();
        resolveTaskDat(task, taskBean);
        task.setCreateUser(taskBean.getCreateUser());
        task.setTargetClass(taskBean.getTargetClass());
        task.setTargetKey(taskBean.getTargetKey());
        saveTask(task);
        createTaskUser(task, taskBean);
    }

    public void completeTask(Task task) throws Exception {
        task.setStatus(TastStatusEnum.completed);
        saveTask(task);
    }

    public void resolveTaskDat(Task task, TaskBean taskBean) throws Exception {

        boolean success = false;
        List<User> userList = new ArrayList<>();
        if (task == null) {
            throw new Exception("Your Task cannot be null");
        }
        if (taskBean == null) {
            throw new Exception("Your Task Bean cannot be null");
        }
        resolveNextTaskInWorkflow(taskBean);
        if (taskBean.getWorkflow() == null) {
            throw new Exception("Please specify the workflow");
        }
        Workflow workflow = workflowService.resolveWorkflowData(taskBean.getWorkflow());
        List<WorkflowRole> wfrList = workflow.getWorkflowRoleRoleList();
        if (wfrList == null || wfrList.size() <= 0) {
            throw new Exception("Workflow roles for this worklfow are not configured, please add workflow roles");
        }
        if (taskBean.getWorkflowRolePosition() == null) {
            throw new Exception("Please specify workflow position");
        }
        if (taskBean.getUserList() == null || taskBean.getUserList().size() <= 0) {
            for (WorkflowRole wfr : wfrList) {
                if (wfr.getPosition() == taskBean.getWorkflowRolePosition()) {
                    task.setWorkflowRole(wfr);
                    if (taskBean.getTaskDescription().isEmpty() || taskBean.getTaskDescription().equals("") || taskBean.getTaskDescription().equals(" ")) {
                        task.setDescription(wfr.getApprovalMessage());
                    } else {
                        task.setDescription(taskBean.getTaskDescription());
                    }
                    if (wfr.getWorkflowRoleUserList() != null && wfr.getWorkflowRoleUserList().size() > 0) {
                        List<WorkFlowRoleUser> wfrUserList = wfr.getWorkflowRoleUserList();
                        for (WorkFlowRoleUser wfrUser : wfrUserList) {
                            userList.add(wfrUser.getUserRole().getUser());
                        }
                        task.setUserList(userList);
                    } else {
                        List<UserRole> userRoleList = userRoleService.findByRole(wfr.getRole());
                        if (userRoleList != null && userRoleList.size() <= 0) {
                            throw new Exception("No users with " + wfr.getRole().getDescription() + " role found");
                        }
                        for (UserRole ur : userRoleList) {
                            userList.add(ur.getUser());
                        }
                        task.setUserList(userList);
                    }
                    success = true;
                    break;
                }
            }
        } else {
            task.setUserList(taskBean.getUserList());
        }
        if (!success) {
            boolean validPosition = false;
            for (WorkflowRole wfr : wfrList) {
                if (wfr.getPosition() == taskBean.getWorkflowRolePosition()) {
                    validPosition = true;
                    break;
                }
            }
            if (!validPosition) {
                if (task.getId() != null) {
                    completeTask(task);
                } else {
                    throw new Exception("Workflow position is not available");
                }
            } else {
                throw new Exception("Unknown error occured when assigning user");
            }
        }

    }

    public void resolveNextTaskInWorkflow(TaskBean taskBean) throws Exception {
        if (taskBean.getCreatePreviousTask()) {
            if (taskBean.getWorkflowRolePosition() == null) {
                if (taskBean.getPreviousTask() == null) {
                    throw new Exception("Please set previous task");
                }
                taskBean.setWorkflowRolePosition(taskBean.getPreviousTask().getWorkflowRole().getPosition());
            }
        } else {
            if (taskBean.getPreviousTask() != null) {
                if (taskBean.getWorkflowRolePosition() == null) {
                    taskBean.setWorkflowRolePosition((taskBean.getPreviousTask().getWorkflowRole().getPosition()) + 1);
                }
            } else if (taskBean.getPreviousTask() == null && taskBean.getWorkflowRolePosition() == null) {
                taskBean.setWorkflowRolePosition(1);
            }
        }

    }

    public void taskNotificationEmail(User u, Task task) throws Exception {

        String welcome = "<p>Dear #NAME#,</p><br/>"
                + "<p>There is a new " + task.getWorkflowRole().getWorkflow().getDescription() + " task "
                + "logged on " + AppConstants.APP_NAME + " web application. Please login and action it.</p><br/>"

                + "<p>Regards</p>"
                + "<p>" + AppConstants.APP_NAME + " Team</p>"
                + "<br/>";
        welcome = welcome.replaceAll("#NAME#", u.getName() + " " + u.getSurname());
        Mail mail = new Mail();
        mail.setContent(welcome);
        mail.setFrom(AppConstants.FROM_EMAIL);
        String[] to = {u.getEmail()};
        mail.setTo(to);
        mail.setSubject("");
        mail.setCc(to);
        mailSender.saveEmail(mail);
    }

    public User getRandomUser(List<User> userList) throws Exception {
        User user = null;
        if (userList != null && userList.size() > 0) {
            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(userList.size());
            user = userList.get(index);
        }
        return user;
    }


}
