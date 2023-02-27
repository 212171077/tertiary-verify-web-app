package com.pc.service.rest;

import com.pc.entities.User;
import com.pc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class MainRESTController {

    @Autowired
    UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/rest/welcome")
    @ResponseBody
    public String welcome() {
        return "Welcome to RestTemplate Example.";
    }

    /**
     * host/FindMyTutor/rest/findUserByEmail/{email}
     */
    @RequestMapping(value = "/rest/findUserByEmail/{email}", method = RequestMethod.GET)
    @ResponseBody
    public User findUserByEmail(@PathVariable("email") String email) {
        User user;
        try {
            user = userService.getUserByEmail(email);
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    /**
     * host/FindMyTutor/rest/findUserByEmail/{email}
     */
    @RequestMapping(value = "/rest/findAllUser", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<User> findAllUser() {
        ArrayList<User> users;
        try {
            users = (ArrayList<User>) userService.findAllUser();
        } catch (Exception e) {
            users = new ArrayList<>();
        }
        return users;
    }

    /**
     * host/FindMyTutor/rest/validateLogin/{email}/{password}
     */
    @RequestMapping(path = "/rest/validateLogin/{email}/{password}", method = RequestMethod.GET)
    @ResponseBody
    public User validateLogin(@PathVariable("email") String email, @PathVariable("password") String password) {

        User user;
        try {
            // String[] roles={"Admin","User"};
            user = userService.getUserByEmail(email);

            if (user != null) {
                //user.setRoles(roles);
                boolean correctPass = bCryptPasswordEncoder.matches(password, user.getPassword());
                if (!correctPass) {
                    user = null;
                }
            } else {
                user = null;
            }

        } catch (Exception e) {
            user = null;
        }
        return user;
    }

}
