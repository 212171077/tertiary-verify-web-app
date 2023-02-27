package com.pc.repositories;


import com.pc.entities.InstCourseAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstCourseAddressRepository extends JpaRepository<InstCourseAddress, Integer> {
    String FIND_BY_ID_LIST = "SELECT o FROM InstCourseAddress o WHERE id IN ?1";

    InstCourseAddress findById(Long parseLong);

    @Query(FIND_BY_ID_LIST)
    List<InstCourseAddress> findByIds(List<String> idList);
}
