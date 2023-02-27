package com.pc.repositories.lookup;


import com.pc.entities.lookup.CourseLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseLevelRepository extends JpaRepository<CourseLevel, Integer> {

    CourseLevel findByIdAndDeleted(Long parseLong, boolean deleted);

    List<CourseLevel> findByDescriptionAndDeleted(String desc, boolean deleted);

    List<CourseLevel> findByDeleted(boolean deleted);

}
