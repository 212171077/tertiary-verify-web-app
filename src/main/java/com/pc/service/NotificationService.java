package com.pc.service;

import com.pc.entities.Notification;
import com.pc.entities.User;
import com.pc.framework.AbstractService;
import com.pc.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService extends AbstractService {
    @Autowired
    NotificationRepository repository;

    public void saveNotification(Notification notification) throws Exception {
        if (notification.getId() == null) {
            //generating guide
            notification.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                notification.setLastUpdateUser(getCurrentUser());
            }
            notification.setLastUpdateDate(new Date());
        }

        repository.save(notification);
    }

    public void deleteNotification(Notification notification) throws Exception {
        repository.delete(notification);
    }

    public void deleteNotificationByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<Notification> findAllNotification() throws Exception {
        return repository.findAll();
    }

    public Page<Notification> findAllNotification(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<Notification> findAllNotification(Sort s) throws Exception {
        return repository.findAll(s);
    }


    public Optional<Notification> findNotificationById(Integer arg0) throws Exception {
        return repository.findById(arg0);
    }

    public Notification findByGuide(String guide) throws Exception {
        return repository.findByGuide(guide);
    }

    public List<Notification> findByToUserAndViewed(User to, Boolean viewed) throws Exception {
        return repository.findByToUserAndViewed(to, viewed);
    }

}
