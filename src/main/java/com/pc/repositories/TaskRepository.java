package com.pc.repositories;


import com.pc.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    Task findById(Long parseLong);
    //public List<Task> findByDescriptionStartingWith(String description);
}
