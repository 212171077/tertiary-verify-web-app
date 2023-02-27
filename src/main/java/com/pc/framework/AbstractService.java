package com.pc.framework;

import com.pc.entities.User;
import com.pc.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import java.io.Serializable;


public abstract class AbstractService implements Serializable {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {

        try {

        } catch (Exception e) {

        }
    }


    public User getCurrentUser() throws Exception {
        User currentUser = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            currentUser = userService.findByEmail(authentication.getName());
        }

        return currentUser;

    }


}
