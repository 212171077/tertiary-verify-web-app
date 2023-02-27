package com.pc.repositories.lookup;

import com.pc.entities.lookup.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {

    Gender findByIdAndDeleted(Long parseLong, boolean deleted);

    Gender findByGenderNameAndDeleted(String name, boolean deleted);

    List<Gender> findByDeleted(boolean deleted);

}
