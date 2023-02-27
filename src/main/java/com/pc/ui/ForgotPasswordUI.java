package com.pc.ui;

import com.pc.framework.AbstractUI;
import com.pc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Component
@ManagedBean(name = "forgotPasswordUI")
@ViewScoped
public class ForgotPasswordUI extends AbstractUI {

    @Autowired
    UserService userService;
    private String email;

    @PostConstruct
    public void init() {

    }

    public void submit() {

        try {
            userService.notifyUserNewPasswordEmail(email);
            displayInfoMssg("Password sent to your email");

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
