package com.pc.service;

import com.pc.entities.UserTask;
import com.pc.framework.AbstractService;
import com.pc.repositories.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserTaskService extends AbstractService {
    @Autowired
    UserTaskRepository repository;

    public void saveUserTask(UserTask userTask) throws Exception {
        if (userTask.getId() == null) {
            userTask.setCreateDate(new Date());
        } else {

            if (getCurrentUser() != null) {
                userTask.setLastUpdateUser(getCurrentUser());
            }
            userTask.setLastUpdateDate(new Date());
        }
        repository.save(userTask);
    }

    public void deleteUserTask(UserTask userTask) throws Exception {
        repository.delete(userTask);
    }

    public void deleteUserTaskByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<UserTask> findAllUserTask() throws Exception {
        return repository.findAll();
    }

    public Page<UserTask> findAllUserTask(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<UserTask> findAllUserTask(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public UserTask findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

}
