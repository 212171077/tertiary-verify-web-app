package com.pc.repositories;


import com.pc.entities.DocConfigDetailActionHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocConfigDetailActionHistRepository extends JpaRepository<DocConfigDetailActionHist, Integer> {

    DocConfigDetailActionHist findById(Long parseLong);
    //public List<DocConfigDetailActionHist> findByDescriptionStartingWith(String description);
}
