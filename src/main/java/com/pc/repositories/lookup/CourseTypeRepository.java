package com.pc.repositories.lookup;


import com.pc.entities.lookup.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseTypeRepository extends JpaRepository<CourseType, Integer> {

    CourseType findByIdAndDeleted(Long parseLong, boolean deleted);

    List<CourseType> findByDescriptionAndDeleted(String desc, boolean deleted);

    List<CourseType> findByDeleted(boolean deleted);
}
