package com.pc.ui;

import com.pc.entities.User;
import com.pc.framework.AbstractUI;
import com.pc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

@Component("dashboardUI")
@ViewScoped
public class DashboardUI extends AbstractUI {

    @Autowired
    UserService userService;
    private String authUser = "";
    private User currentUser;

    @PostConstruct
    public void init() {

        try {
            prepareCurrentUser();
            authUser = showGreeting();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
    }

    public String showGreeting() {

        return "Hello " + currentUser.getName() + " " + currentUser.getSurname() + "!";
    }

    public void prepareCurrentUser() throws Exception {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        currentUser = userService.findByEmail(authentication.getName());
    }

    public String trimErrorMessage(String mssg) {
        if (mssg != null && mssg.length() > 50) {
            return mssg.substring(0, 50) + "...";
        } else if (mssg == null) {
            return "";
        } else {
            return mssg;
        }
    }

    public String getAuthUser() {
        return authUser;
    }


    public void setAuthUser(String authUser) {
        this.authUser = authUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public  boolean isNullOrEmpty(String value){
        return value == null || value.isEmpty();
    }
}
