package com.pc.ui;

import com.pc.entities.ImageModel;
import com.pc.entities.User;
import com.pc.entities.UserRole;
import com.pc.framework.AbstractUI;
import com.pc.service.ImagesService;
import com.pc.service.UserRoleService;
import com.pc.service.UserService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
@ManagedBean(name = "profileUI")
@ViewScoped
@RequestScoped
public class ProfileUI extends AbstractUI {

    @Autowired
    private UserService userService;
    @Autowired
    private ImagesService imagesService;
    @Autowired
    private UserRoleService roleService;
    private List<UserRole> userRoles;
    private String newPassword;
    private String password;
    private String displayName;
    private String confirmPassword;

    @PostConstruct
    public void init() {

        try {
            prepareCurrentUser();
            displayName = (currentUser.getName() + " " + currentUser.getSurname()).toUpperCase();
            prepUserRole();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
    }

    private void prepUserRole() {
        try {
            userRoles = roleService.findByUser(currentUser);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
    }

    public void handleFileUpload(FileUploadEvent event) {

        try {
            userService.saveProfileImage(currentUser, event);
            prepareCurrentUser();
            displayInfoMssg("Profile image uploaded successfully...!!!");
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
    }

    public void updateUser() {
        try {
            if (userService.findByEmailAndRsaIdNot(currentUser.getEmail(), currentUser.getRsaId()) != null) {
                throw new Exception("Email address already exist, please provide a unique email");
            } else {
                userService.saveUser(currentUser);
                displayInfoMssg("Profile updated successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            displayErrorMssg(e.getMessage());
        }
    }

    public void updateUserPassword() {
        try {
            if (!confirmPassword.equals(newPassword)) {
                throw new Exception("Your new password and confirm password must be the same");
            }
            this.currentUser = userService.changePassword(this.currentUser.getEmail(), this.password, this.newPassword);
            displayInfoMssg("Password updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            displayErrorMssg(e.getMessage());
        }
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public StreamedContent getImageFromDB() throws Exception {
        prepareCurrentUser();
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] image = null;
            try {
                image = currentUser.getImage().getPic();
            } catch (Exception e) {
                displayErrorMssg(e.getMessage());
            }
            return new DefaultStreamedContent(new ByteArrayInputStream(image), currentUser.getImage().getType().trim());
        }
    }

    public StreamedContent getImageFromDB2() throws Exception {
        prepareCurrentUser();
        FacesContext context = FacesContext.getCurrentInstance();
        ImageModel imageModel = null;
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            String imageId = context.getExternalContext().getRequestParameterMap().get("imageId");
            byte[] image = null;
            try {

                imageModel = imagesService.getById(Long.parseLong(imageId) - 1);
                image = imageModel.getPic();
            } catch (Exception e) {
                displayErrorMssg(e.getMessage());
            }
            return new DefaultStreamedContent(new ByteArrayInputStream(image), imageModel.getType().trim());
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
