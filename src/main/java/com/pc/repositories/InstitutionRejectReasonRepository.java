package com.pc.repositories;


import com.pc.entities.Institution;
import com.pc.entities.InstitutionRejectReason;
import com.pc.entities.lookup.RejectReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRejectReasonRepository extends JpaRepository<InstitutionRejectReason, Integer> {

    InstitutionRejectReason findById(Long parseLong);

    List<InstitutionRejectReason> findByInstitution(Institution institution);

    long countByRejectReason(RejectReason rejectReason);
}
