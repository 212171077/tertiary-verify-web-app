package com.pc.repositories;

import com.pc.entities.Notification;
import com.pc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByToUserAndViewed(User to, Boolean viewed);

    Notification findByGuide(String guide);

}
