package com.pc.repositories;


import com.pc.entities.UserBrowserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBrowserInfoRepository extends JpaRepository<UserBrowserInfo, Integer> {

    UserBrowserInfo findById(Long parseLong);
    //public List<UserBrowserInfo> findByDescriptionStartingWith(String description);
}
