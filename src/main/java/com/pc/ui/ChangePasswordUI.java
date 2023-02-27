package com.pc.ui;

import com.pc.entities.User;
import com.pc.framework.AbstractUI;
import com.pc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@ManagedBean(name = "changePasswordUI")
@ViewScoped
public class ChangePasswordUI extends AbstractUI {


    @Autowired
    UserService userService;
    private String newPassword;
    private String confirmPassword;

    @PostConstruct
    public void init() {
        try {
            prepareCurrentUserNoChangePassCheck();
            if (currentUser == null || currentUser.getChangePassword() == null || currentUser.getChangePassword() == false) {
                super.ajaxRedirect("/dashboard.xhtml");
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void changeUserPassword() {
        try {
            if (!confirmPassword.equals(newPassword)) {
                throw new Exception("Your new password and confirm password must be the same");
            }
            checkPassword(this.newPassword, this.currentUser);
            userService.resetPassword(this.currentUser, this.newPassword);
            displayInfoMssg("Password updated sucessfully");
            super.ajaxRedirect("/dashboard.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
            displayErrorMssg(e.getMessage());
        }
    }


    public String getNewPassword() {
        return newPassword;
    }


    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


    public String getConfirmPassword() {
        return confirmPassword;
    }


    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void checkPassword(String newPassword, User u) throws Exception {
        if (newPassword != null) {
            if (newPassword.equals(u.getName()) || newPassword.equals(u.getSurname()))
                throw new Exception("Password cannot be your First name or last name.");

            if (newPassword.length() < 8)
                throw new Exception("Password must be 8 characters long.");

            String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&#^_=+/<>;:|.,`~])[A-Za-z\\d$@$!%*?&#^_=+/<>;:|.,`~]{8,}";
            // Create a Pattern object
            Pattern r = Pattern.compile(regex);
            Matcher m = r.matcher(newPassword);
            if (!m.find())
                throw new Exception("Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character");
        }
    }


}
