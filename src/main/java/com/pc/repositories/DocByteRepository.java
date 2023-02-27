package com.pc.repositories;


import com.pc.entities.DocByte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocByteRepository extends JpaRepository<DocByte, Integer> {

    DocByte findById(Long parseLong);
    //public List<DocByte> findByDescriptionStartingWith(String description);
}
