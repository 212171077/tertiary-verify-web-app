package com.pc.repositories.lookup;


import com.pc.entities.lookup.DocumentLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentLevelRepository extends JpaRepository<DocumentLevel, Integer> {

    DocumentLevel findById(Long parseLong);

    List<DocumentLevel> findByDescriptionStartingWith(String description);
}
