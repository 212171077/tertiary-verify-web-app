package com.pc.repositories;


import com.pc.entities.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, Integer> {

    ContactUs findById(Long parseLong);
    //public List<ContactUs> findByDescriptionStartingWith(String description);
}
