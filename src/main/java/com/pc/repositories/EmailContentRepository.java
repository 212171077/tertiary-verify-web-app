package com.pc.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pc.entities.EmailContent;

@Repository
public interface EmailContentRepository extends JpaRepository<EmailContent, Integer> {

    EmailContent findById(Long parseLong);

    List<EmailContent> findTop100ByOrderByIdDesc();
}
