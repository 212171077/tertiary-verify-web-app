package com.pc.repositories;

import com.pc.beans.InstitutionCourseBean;
import com.pc.entities.Institution;
import com.pc.entities.InstitutionCourse;
import com.pc.entities.enums.ApprovalStatusEnum;
import com.pc.entities.lookup.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionCourseRepository extends JpaRepository<InstitutionCourse, Integer> {
     String FIND_COURSE_BY_COURSE_NAME = "SELECT * from institution_course ic, course c where ic.course_id = c.id and ic.institution_id = ?1 and (c.course_name = ?2 or c.description = ?2)";
    String FIND_BY_COURSE_DESC = "SELECT o FROM InstitutionCourse o WHERE UPPER(o.course.description) LIKE CONCAT(?1, '%') AND o.institution.status <> ?2";
    String FIND_BY_COURSE_DESC_NO_PROV = "SELECT o FROM InstitutionCourse o WHERE UPPER(o.course.description) LIKE CONCAT(?1, '%') AND o.institution.status <> ?2";
    String FIND_BY_COURSE_DESC_AND_PROV = "select * from institution_course ic " +

            "inner join course c " +
            "on ic.course_id = c.id " +

            "inner join address addr " +
            "on addr.institution_id = ic.institution_id " +

            "inner join institution inst " +
            "on ic.institution_id = inst.id " +

            "inner join province prov " +
            "on prov.id = addr.province_id " +

            "where prov.id = ?2 and inst.status = ?3 and c.course_name like %?1% or c.description like %?1%";

    List<InstitutionCourse> findByInstitution(Institution institution);

    List<InstitutionCourse> findByCourse(Course course);

    @Query("SELECT new com.pc.beans.InstitutionCourseBean(o.institution.institutionName, COUNT(o)) "
            + "FROM InstitutionCourse o GROUP BY o.institution")
    List<InstitutionCourseBean> getInstitutionCourseBean();

    @Query(value = FIND_COURSE_BY_COURSE_NAME, nativeQuery = true)
    List<InstitutionCourse> findCourseByCourseName(Long id, String name);

    @Query(FIND_BY_COURSE_DESC)
    List<InstitutionCourse> getCourseByDescription(String searchText, ApprovalStatusEnum status);

    @Query(FIND_BY_COURSE_DESC_NO_PROV)
    List<InstitutionCourse> getCourseByDescriptionNoProvince(String searchText, ApprovalStatusEnum status);

    @Query(value = FIND_BY_COURSE_DESC_AND_PROV, nativeQuery = true)
    List<InstitutionCourse> getCourseByDescriptionAndProvince(String searchText, Long provinceId, ApprovalStatusEnum status);

    long count();

    long countByCourse(Course course);
}
