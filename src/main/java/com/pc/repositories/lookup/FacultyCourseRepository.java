package com.pc.repositories.lookup;


import com.pc.entities.lookup.Course;
import com.pc.entities.lookup.Faculty;
import com.pc.entities.lookup.FacultyCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyCourseRepository extends JpaRepository<FacultyCourse, Integer> {

    FacultyCourse findByIdAndDeleted(Long parseLong, boolean deleted);

    List<FacultyCourse> findByFacultyAndDeleted(Faculty faculty, boolean deleted);

    long countByCourseAndDeleted(Course course, boolean deleted);
}
