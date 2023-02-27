package com.pc.repositories.lookup;


import com.pc.entities.lookup.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    Faculty findByIdAndDeleted(Long parseLong, boolean deleted);

    List<Faculty> findByDescriptionAndDeleted(String desc, boolean deleted);
}
