package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.lookup.Role;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.RoleService;
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

@Component("roleUI")
@ViewScoped
public class RoleUI extends AbstractUI {

    @Autowired
    RoleService roleService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<Role> roleList;
    private Role role;
    private LazyDataModel<Role> dataModel;

    @PostConstruct
    public void init() {

        try {
            super.prepareCurrentUser();
            role = new Role();
            loadRoleInfo();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveRole() {
        try {
            roleService.saveRole(role);
            displayInfoMssg("Role added successful...!!");
            roleList = (ArrayList<Role>) findAllRole();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteRole() {
        try {
            roleService.deleteRole(role);
            displayWarningMssg("Role deleted  successful...!!");
            roleList = (ArrayList<Role>) findAllRole();
            reset();
        } catch (Exception e) {
            reset();
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Role> findAllRole() {
        List<Role> list = new ArrayList<>();
        try {
            list = roleService.findAllRole();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public void reset() {
        role = new Role();
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ArrayList<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(ArrayList<Role> roleList) {
        this.roleList = roleList;
    }

    public void loadRoleInfo() {
        dataModel = new LazyDataModel<Role>() {

            private static final long serialVersionUID = 1L;
            private List<Role> list = new ArrayList<Role>();

            @Override
            public List<Role> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<Role>) entityDAOFacade.getResultList(Role.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, Role.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(Role obj) {
                return obj.getId();
            }

            @Override
            public Role getRowData(String rowKey) {
                for (Role obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public LazyDataModel<Role> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<Role> dataModel) {
        this.dataModel = dataModel;
    }

}
