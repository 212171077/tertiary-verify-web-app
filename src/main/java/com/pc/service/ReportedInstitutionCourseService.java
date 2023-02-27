package com.pc.service;

import com.pc.entities.ReportedInstitutionCourse;
import com.pc.entities.ReportedInstitutions;
import com.pc.framework.AbstractService;
import com.pc.repositories.ReportedInstitutionCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReportedInstitutionCourseService extends AbstractService {
    @Autowired
    ReportedInstitutionCourseRepository repository;

    public void saveReportedInstitutionCourse(ReportedInstitutionCourse reportedInstitutionCourse) throws Exception {
        if (reportedInstitutionCourse.getId() == null) {
            reportedInstitutionCourse.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                reportedInstitutionCourse.setLastUpdateUser(getCurrentUser());
            }
            reportedInstitutionCourse.setLastUpdateDate(new Date());
        }
        repository.save(reportedInstitutionCourse);
    }

    public void deleteReportedInstitutionCourse(ReportedInstitutionCourse reportedInstitutionCourse) throws Exception {
        repository.delete(reportedInstitutionCourse);
    }

    public void deleteReportedInstitutionCourseByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<ReportedInstitutionCourse> findAllReportedInstitutionCourse() throws Exception {
        return repository.findAll();
    }

    public Page<ReportedInstitutionCourse> findAllReportedInstitutionCourse(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<ReportedInstitutionCourse> findAllReportedInstitutionCourse(Sort s) throws Exception {
        return repository.findAll(s);
    }


    public Optional<ReportedInstitutionCourse> findReportedInstitutionCourseById(Integer arg0) throws Exception {
        return repository.findById(arg0);
    }

    public List<ReportedInstitutionCourse> findByReportedInstitution(ReportedInstitutions reportedInstitutions) throws Exception {
        return repository.findByReportedInstitution(reportedInstitutions);
    }

}
