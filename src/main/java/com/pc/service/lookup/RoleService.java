package com.pc.service.lookup;

import com.pc.entities.lookup.Role;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.RoleRepository;
import com.pc.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService extends AbstractService {
    @Autowired
    private RoleRepository repository;

    @Autowired
    private UserRoleService userRoleService;

    public void saveRole(Role role) throws Exception {

        if (role.getId() == null) {
            checkIfExist(role.getDescription());
            role.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                role.setLastUpdateUser(getCurrentUser());
            }
            role.setLastUpdateDate(new Date());
        }
        repository.save(role);
    }

    public void deleteRole(Role role) throws Exception {
        checkIfInUse(role);
        role.setDeleted(true);
        repository.save(role);
    }

    public List<Role> findAllRole() throws Exception {
        return repository.findByDeleted(false);
    }

    public Role findById(Long id) throws Exception {
        return repository.findByIdAndDeleted(id,false);
    }

    public Role findByCode(String code) throws Exception {
        return repository.findByCodeAndDeleted(code,false);
    }

    private void checkIfExist(String desc) throws Exception {
        List<Role> list = repository.findByDescriptionAndDeleted(desc,false);
        if (list != null && list.size() > 0) {
            throw new Exception("Role already exist");
        }
    }

    private void checkIfInUse(Role role) throws Exception {
        long count = userRoleService.countByRole(role);
        if (count > 0) {
            throw new Exception("This role cannot be deleted because it's being used");
        }
    }


}
