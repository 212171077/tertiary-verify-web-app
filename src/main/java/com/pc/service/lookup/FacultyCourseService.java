package com.pc.service.lookup;

import com.pc.entities.lookup.Course;
import com.pc.entities.lookup.Faculty;
import com.pc.entities.lookup.FacultyCourse;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.FacultyCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FacultyCourseService extends AbstractService {
    @Autowired
    FacultyCourseRepository repository;

    public void saveFacultyCourse(FacultyCourse facultyCourse) throws Exception {
        if (facultyCourse.getId() == null) {
            facultyCourse.setDeleted(false);
            facultyCourse.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                facultyCourse.setLastUpdateUser(getCurrentUser());
            }
            facultyCourse.setLastUpdateDate(new Date());
        }
        repository.save(facultyCourse);
    }

    public void deleteFacultyCourse(FacultyCourse facultyCourse) throws Exception {
        facultyCourse.setDeleted(false);
        repository.save(facultyCourse);
    }

    public FacultyCourse findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong, false);
    }


    public List<FacultyCourse> findByFaculty(Faculty faculty) throws Exception {
        return repository.findByFacultyAndDeleted(faculty, false);
    }

    public void deleteAll(ArrayList<FacultyCourse> list) throws Exception {
        list.forEach(facultyCourse -> facultyCourse.setDeleted(false));
        repository.saveAll(list);
    }

    public long countByCourse(Course course) {
        return repository.countByCourseAndDeleted(course, false);
    }
}
