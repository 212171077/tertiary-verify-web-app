package com.pc.repositories;

import com.pc.entities.ReportedInstitutionCourse;
import com.pc.entities.ReportedInstitutions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportedInstitutionCourseRepository extends JpaRepository<ReportedInstitutionCourse, Integer> {

    List<ReportedInstitutionCourse> findByReportedInstitution(ReportedInstitutions reportedInstitutions);
}
