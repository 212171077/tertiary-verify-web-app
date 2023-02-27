package com.pc.service;

import com.pc.entities.User;
import com.pc.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Date;


public class LoginServiceImp implements UserDetailsService {

    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        UserBuilder builder = null;
        try {
            user = validateUser(username);
            if (user != null && !user.getDeleted()) {
                builder = org.springframework.security.core.userdetails.User.withUsername(username);
                builder.password(user.getPassword());
                builder.roles(user.getRoles());
                user.setLastLoginDate(new Date());
                userService.saveUser(user);
            } else {
                throw new UsernameNotFoundException("User not found.");
            }

        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }


    private User validateUser(String username) throws Exception {
        User user = null;
        user = userService.findByEmail(username);
        //Getting User roles
        if (user != null) {
            ArrayList<UserRole> roleList = (ArrayList<UserRole>) userRoleService.findByUser(user);
            String[] roles = new String[roleList.size()];
            int index = 0;
            for (UserRole ur : roleList) {
                roles[index] = ur.getRole().getCode();
                index++;
            }
            user.setRoles(roles);
        }
        return user;
    }
}