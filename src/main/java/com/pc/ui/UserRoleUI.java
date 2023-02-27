package com.pc.ui;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.User;
import com.pc.entities.UserRole;
import com.pc.entities.lookup.Role;
import com.pc.framework.AbstractUI;
import com.pc.service.UserRoleService;
import com.pc.service.UserService;
import com.pc.service.lookup.RoleService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@ManagedBean(name = "userRoleUI")
@ViewScoped
public class UserRoleUI extends AbstractUI {

    private final ArrayList<Role> roleListSource = new ArrayList<Role>();
    private ArrayList<Role> currentRoles = new ArrayList<Role>();
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private EntityDAOFacade entityDAOFacade;
    private UserRole userRole;
    private LazyDataModel<User> userListDataModel;
    private User selectedUser;
    private ArrayList<UserRole> selectedUserRoles;
    private ArrayList<UserRole> currentUserRoles;
    private ArrayList<Role> roleList;
    private ArrayList<Role> roleListTarget = new ArrayList<Role>();
    private DualListModel<Role> rolesDualList;
    private Boolean showRoleDetails;
    private User user = new User();

    @PostConstruct
    public void init() {

        try {
            userRole = new UserRole();
            allUsers();
            roleList = (ArrayList<Role>) roleService.findAllRole();
            rolesDualList = new DualListModel<>(roleList, roleListTarget);
            selectedUserRoles = new ArrayList<>();
            currentUserRoles = new ArrayList<>();
            showRoleDetails = false;
        } catch (Exception e) {
            e.printStackTrace();
            displayErrorMssg(e.getMessage());
        }
    }

    public void deleteUser() {
        try {
            user.setDeleted(true);
            userService.saveUser(user);
            allUsers();
            displayInfoMssg("User deleted Successful..!!");
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadUsers() {
        try {
            allUsers();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<UserRole> alluserRoles(User user) {
        try {
            return (ArrayList<UserRole>) userRoleService.findByUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            displayErrorMssg(e.getMessage());
            return new ArrayList<UserRole>();
        }
    }

    public void saveUserRole() {
        try {
            userRoleService.saveUserRole(userRole);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void allUsers() {
        userListDataModel = new LazyDataModel<User>() {

            private static final long serialVersionUID = 1L;
            private List<User> list = new ArrayList<User>();

            @Override
            public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    filters.put("deleted", false);
                    list = (List<User>) entityDAOFacade.getResultList(User.class, first, pageSize, sortField, sortOrder, filters);
                    userListDataModel.setRowCount(entityDAOFacade.count(filters, User.class));
                } catch (Exception e) {
                    logger.error(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(User obj) {
                return obj.getId();
            }

            @Override
            public User getRowData(String rowKey) {
                for (User obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey))) {
                        return obj;
                    }
                }
                return null;
            }

        };

    }


    public void saveUserRoles() {
        try {

            if (rolesDualList.getTarget().size() <= 0) {
                throw new Exception("Please select at least one role before submitting " + rolesDualList.getTarget().size());
            }

            removingLastAdminValidation(currentUserRoles, rolesDualList.getTarget());

            //Deleting current user roles
            userRoleService.deleteAll(currentUserRoles);
            for (Role ur : rolesDualList.getTarget()) {
                UserRole userRole = new UserRole();
                userRole.setRole(ur);
                userRole.setUser(selectedUser);
                selectedUserRoles.add(userRole);

            }
            userService.saveUser(selectedUser);
            userRoleService.saveAll(selectedUserRoles);

            clearSelectUser();
            showRoleDetails = false;


        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    private void removingLastAdminValidation(ArrayList<UserRole> currentUserRoles, List<Role> newRoles) throws Exception {
        System.out.println("------------REMOVING LAST ADMIN VALIDATION------------");
        String mssg = "You can not remove the admin role from this user because there won't be no admin on the system, at least one admin is required on the system";
        UserRole currentAdmin = currentUserRoles.stream().filter(ur -> ur.getRole().getCode().equals("ADMIN")).findAny().orElse(null);
        Role admin = newRoles.stream().filter(role -> role.getCode().equals("ADMIN")).findAny().orElse(null);
        if (currentAdmin != null && admin == null) {
            List<UserRole> adminUserRoleList = userRoleService.findByRole(roleService.findByCode("ADMIN"));
            System.out.println("ADMIN USER ROLE LIST: "+adminUserRoleList.size());
            if (adminUserRoleList == null || adminUserRoleList.isEmpty() || adminUserRoleList.size() < 2) {
                throw new Exception(mssg);
            }
        } else {
            System.out.println("CURRENT ADMIN != NULL: " + (currentAdmin != null) + "  ADMIN == NULL: " + (admin == null));
        }
    }

    public void deleteUserRole() {
        try {
            userRoleService.deleteUserRole(userRole);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<UserRole> findAllUserRole() {
        List<UserRole> list = new ArrayList<>();
        try {
            list = userRoleService.findAllUserRole();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<UserRole> findAllUserRolePageable() {
        Pageable p = null;
        try {
            return userRoleService.findAllUserRole(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<UserRole> findAllUserRoleSort() {
        Sort s = null;
        List<UserRole> list = new ArrayList<>();
        try {
            list = userRoleService.findAllUserRole(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void preperPopup() {
        try {
            currentUserRoles.clear();
            currentUserRoles = (ArrayList<UserRole>) userRoleService.findByUser(selectedUser);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            displayErrorMssg(e.getMessage());
        }
    }

    public void loadUserRoles() {
        try {

            showRoleDetails = true;
            //displayInfoMssg("Loading User data");
            currentUserRoles.clear();
            currentUserRoles = (ArrayList<UserRole>) userRoleService.findByUser(selectedUser);
            currentRoles.clear();
            for (UserRole ur : currentUserRoles) {
                currentRoles.add(ur.getRole());
            }

            //Clearing roleListSource
            roleListSource.clear();
            for (Role role : roleList) {
                boolean hasThisRole = false;
                for (Role r : currentRoles) {
                    if (r.getId().longValue() == role.getId().longValue()) {
                        hasThisRole = true;
                        break;
                    }
                }
                if (!hasThisRole) {
                    roleListSource.add(role);
                }
            }

            rolesDualList = new DualListModel<>(roleListSource, currentRoles);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }


    public void clearSelectUser() {
        try {
            selectedUser = null;
            roleList = (ArrayList<Role>) roleService.findAllRole();
            rolesDualList = new DualListModel<>(roleList, roleListTarget);
            selectedUserRoles = new ArrayList<>();
            showRoleDetails = false;
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

    }

    public void reset() {
        userRole = new UserRole();
    }


    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public ArrayList<UserRole> getSelectedUserRoles() {
        return selectedUserRoles;
    }

    public void setSelectedUserRoles(ArrayList<UserRole> selectedUserRoles) {
        this.selectedUserRoles = selectedUserRoles;
    }

    public ArrayList<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(ArrayList<Role> roleList) {
        this.roleList = roleList;
    }

    public DualListModel<Role> getRolesDualList() {
        return rolesDualList;
    }

    public void setRolesDualList(DualListModel<Role> rolesDualList) {
        this.rolesDualList = rolesDualList;
    }

    public ArrayList<UserRole> getCurrentUserRoles() {
        return currentUserRoles;
    }

    public void setCurrentUserRoles(ArrayList<UserRole> currentUserRoles) {
        this.currentUserRoles = currentUserRoles;
    }

    public ArrayList<Role> getRoleListTarget() {
        return roleListTarget;
    }

    public void setRoleListTarget(ArrayList<Role> roleListTarget) {
        this.roleListTarget = roleListTarget;
    }

    public ArrayList<Role> getCurrentRoles() {
        return currentRoles;
    }

    public void setCurrentRoles(ArrayList<Role> currentRoles) {
        this.currentRoles = currentRoles;
    }

    public Boolean getShowRoleDetails() {
        return showRoleDetails;
    }

    public void setShowRoleDetails(Boolean showRoleDetails) {
        this.showRoleDetails = showRoleDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LazyDataModel<User> getUserListDataModel() {
        return userListDataModel;
    }

    public void setUserListDataModel(LazyDataModel<User> userListDataModel) {
        this.userListDataModel = userListDataModel;
    }

}
