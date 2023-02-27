package com.pc.ui;

import com.pc.entities.Notification;
import com.pc.framework.AbstractUI;
import com.pc.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("notificationUI")
@ViewScoped
public class NotificationUI extends AbstractUI {

    @Autowired
    NotificationService notificationService;

    private Notification notification;

    private ArrayList<Notification> notificationList = new ArrayList<>();

    @PostConstruct
    public void init() {

        try {
            super.prepareCurrentUser();
            notification = new Notification();
            notificationList = (ArrayList<Notification>) findByToUserAndViewed();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }


    public void saveNotification() {
        try {

            notificationService.saveNotification(notification);

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            displayErrorMssg(e.getMessage());
        }
    }

    public void deleteNotification() {
        try {
            notificationService.deleteNotification(notification);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Notification> findAllNotification() {
        List<Notification> list = new ArrayList<>();
        try {
            list = notificationService.findAllNotification();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<Notification> findAllNotificationPageable() {
        Pageable p = null;
        try {
            return notificationService.findAllNotification(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Notification> findAllNotificationSort() {
        Sort s = null;
        List<Notification> list = new ArrayList<>();
        try {
            list = notificationService.findAllNotification(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        notification = new Notification();
    }


    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public List<Notification> findByToUserAndViewed() throws Exception {
        prepareCurrentUser();
        return notificationService.findByToUserAndViewed(currentUser, false);
    }

    public ArrayList<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(ArrayList<Notification> notificationList) {
        this.notificationList = notificationList;
    }

}
