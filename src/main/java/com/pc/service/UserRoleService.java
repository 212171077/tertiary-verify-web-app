package com.pc.service;

import com.pc.entities.User;
import com.pc.entities.UserRole;
import com.pc.entities.lookup.Role;
import com.pc.framework.AbstractService;
import com.pc.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserRoleService extends AbstractService {
    @Autowired
    UserRoleRepository repository;

    public void saveUserRole(UserRole userRole) throws Exception {
        if (userRole.getId() == null) {
            userRole.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                userRole.setLastUpdateUser(getCurrentUser());
            }
            userRole.setLastUpdateDate(new Date());
        }
        repository.save(userRole);
    }

    public void deleteUserRole(UserRole userRole) throws Exception {
        repository.delete(userRole);
    }

    public List<UserRole> findAllUserRole() throws Exception {
        return repository.findAll();
    }

    public List<UserRole> findByUser(User user) throws Exception {
        return repository.findByUser(user);
    }

    public List<UserRole> findByRole(Role role) throws Exception {
        return repository.findByRole(role);
    }

    public Page<UserRole> findAllUserRole(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<UserRole> findAllUserRole(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public void saveAll(ArrayList<UserRole> userRoleList) {
        repository.saveAll(userRoleList);
    }

    public void deleteAll(List<UserRole> arg0) throws Exception {
        repository.deleteAll(arg0);
    }

    public UserRole findById(long parseLong) throws Exception {
        // TODO Auto-generated method stub
        return repository.findById(parseLong);
    }

    public long countByRole(Role role) {
        return repository.countByRole(role);
    }


}
