package com.pc.repositories.lookup;


import com.pc.entities.lookup.InstitutionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionTypeRepository extends JpaRepository<InstitutionType, Integer> {

    InstitutionType findByIdAndDeleted(Long parseLong, boolean deleted);

    List<InstitutionType> findByDescriptionAndDeleted(String desc, boolean deleted);

    List<InstitutionType> findByDeleted(boolean deleted);
}
