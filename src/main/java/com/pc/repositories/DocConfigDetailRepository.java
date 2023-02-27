package com.pc.repositories;


import com.pc.entities.DocConfigDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocConfigDetailRepository extends JpaRepository<DocConfigDetail, Integer> {

    DocConfigDetail findById(Long parseLong);
    //public List<DocConfigDetail> findByDescriptionStartingWith(String description);
}
