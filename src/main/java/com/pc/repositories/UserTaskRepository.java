package com.pc.repositories;


import com.pc.entities.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Integer> {

    UserTask findById(Long parseLong);
    //public List<UserTask> findByDescriptionStartingWith(String description);
}
