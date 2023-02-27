package com.pc.repositories.lookup;


import com.pc.entities.lookup.RejectReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RejectReasonRepository extends JpaRepository<RejectReason, Integer> {

    RejectReason findByIdAndDeleted(Long parseLong, boolean deleted);

    List<RejectReason> findByDeletedAndDescriptionStartingWith(boolean deleted, String description);

    List<RejectReason> findByDescriptionAndDeleted(String desc, boolean deleted);

    List<RejectReason> findByDeleted(boolean deleted);
}
