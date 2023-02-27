package com.pc.ui;

import com.pc.entities.UserBrowserInfo;
import com.pc.framework.AbstractUI;
import com.pc.service.UserBrowserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("userBrowserInfoUI")
@ViewScoped
public class UserBrowserInfoUI extends AbstractUI {

    @Autowired
    UserBrowserInfoService userBrowserInfoService;
    private ArrayList<UserBrowserInfo> userBrowserInfoList;

    private UserBrowserInfo userBrowserInfo;

    @PostConstruct
    public void init() {
        userBrowserInfo = new UserBrowserInfo();
        userBrowserInfoList = (ArrayList<UserBrowserInfo>) findAllUserBrowserInfo();
    }

    public void saveUserBrowserInfo() {
        try {
            userBrowserInfoService.saveUserBrowserInfo(userBrowserInfo);
            displayInfoMssg("User browser info added successful...!!");
            userBrowserInfoList = (ArrayList<UserBrowserInfo>) findAllUserBrowserInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteUserBrowserInfo() {
        try {
            userBrowserInfoService.deleteUserBrowserInfo(userBrowserInfo);
            displayWarningMssg("User browser info deleted successful...!!");
            userBrowserInfoList = (ArrayList<UserBrowserInfo>) findAllUserBrowserInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<UserBrowserInfo> findAllUserBrowserInfo() {
        List<UserBrowserInfo> list = new ArrayList<>();
        try {
            list = userBrowserInfoService.findAllUserBrowserInfo();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<UserBrowserInfo> findAllUserBrowserInfoPageable() {
        Pageable p = null;
        try {
            return userBrowserInfoService.findAllUserBrowserInfo(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<UserBrowserInfo> findAllUserBrowserInfoSort() {
        Sort s = null;
        List<UserBrowserInfo> list = new ArrayList<>();
        try {
            list = userBrowserInfoService.findAllUserBrowserInfo(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        userBrowserInfo = new UserBrowserInfo();
    }


    public UserBrowserInfo getUserBrowserInfo() {
        return userBrowserInfo;
    }

    public void setUserBrowserInfo(UserBrowserInfo userBrowserInfo) {
        this.userBrowserInfo = userBrowserInfo;
    }

    public ArrayList<UserBrowserInfo> getUserBrowserInfoList() {
        return userBrowserInfoList;
    }

    public void setUserBrowserInfoList(ArrayList<UserBrowserInfo> userBrowserInfoList) {
        this.userBrowserInfoList = userBrowserInfoList;
    }

}
