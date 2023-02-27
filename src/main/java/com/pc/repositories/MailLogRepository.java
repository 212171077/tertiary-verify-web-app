package com.pc.repositories;


import com.pc.entities.MailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailLogRepository extends JpaRepository<MailLog, Integer> {

    MailLog findById(Long parseLong);
    //public List<MailLog> findByDescriptionStartingWith(String description);
}
