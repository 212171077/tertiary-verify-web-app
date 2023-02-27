package com.pc.repositories.lookup;


import com.pc.entities.lookup.Course;
import com.pc.entities.lookup.CourseLevel;
import com.pc.entities.lookup.CourseType;
import com.pc.model.AllCourseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    String FIND_BY_NAME_TYPE_AND_LEVEL = "SELECT o FROM Course o WHERE UPPER(o.description) LIKE CONCAT(?1, '%') AND o.courseType = ?2 AND o.courseLevel = ?3 AND o.deleted = ?4";

    Course findByIdAndDeleted(Long parseLong, boolean deleted);

    List<Course> findByDeletedAndDescriptionStartingWith(boolean deleted, String description);

    @Query(FIND_BY_NAME_TYPE_AND_LEVEL)
    List<Course> searchByNameTypeAndLevelAndDeleted(String description, CourseType courseType, CourseLevel courseLevel, boolean deleted);

    @Query("SELECT new com.pc.model.AllCourseDTO(o.id,o.description) FROM Course o WHERE o.deleted = false GROUP BY o.description")
    List<AllCourseDTO> findAllCourseDTO();

    List<Course> findByDescriptionAndCourseTypeAndCourseLevelAndDeleted(String desc, CourseType courseType, CourseLevel courseLevel, boolean deleted);

    long countByCourseLevelAndDeleted(CourseLevel courseLevel, boolean deleted);

    long countByCourseTypeAndDeleted(CourseType courseType, boolean deleted);

    List<Course> findByDeleted(boolean deleted);
}
