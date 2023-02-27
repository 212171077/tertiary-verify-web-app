package com.pc.service;

import com.pc.beans.InstitutionCourseBean;
import com.pc.entities.Address;
import com.pc.entities.Institution;
import com.pc.entities.InstitutionCourse;
import com.pc.entities.enums.ApprovalStatusEnum;
import com.pc.entities.lookup.Course;
import com.pc.entities.lookup.Province;
import com.pc.framework.AbstractService;
import com.pc.repositories.InstitutionCourseRepository;
import com.pc.service.lookup.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InstitutionCourseService extends AbstractService {
    @Autowired
    InstitutionCourseRepository repository;

    @Autowired
    CourseService courseService;

    public void saveInstitutionCourse(InstitutionCourse institutionCourse) throws Exception {
        if (institutionCourse.getId() == null) {
            institutionCourse.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                institutionCourse.setLastUpdateUser(getCurrentUser());
            }
            institutionCourse.setLastUpdateDate(new Date());
        }
        repository.save(institutionCourse);
    }

    public void deleteInstitutionCourse(InstitutionCourse institutionCourse) throws Exception {
        repository.delete(institutionCourse);
    }

    public void deleteInstitutionCourseByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<InstitutionCourse> findAllInstitutionCourse() throws Exception {
        return repository.findAll();
    }

    public Page<InstitutionCourse> findAllInstitutionCourse(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<InstitutionCourse> findAllInstitutionCourse(Sort s) throws Exception {
        return repository.findAll(s);
    }


    public Optional<InstitutionCourse> findInstitutionCourseById(Integer arg0) throws Exception {
        return repository.findById(arg0);
    }

    public List<InstitutionCourse> findByInstitution(Institution institution) throws Exception {
        return repository.findByInstitution(institution);
    }

    public void saveAll(ArrayList<InstitutionCourse> courseList) throws Exception {
        repository.saveAll(courseList);
    }

    public void deleteAll(List<InstitutionCourse> institutionCourseList) throws Exception {
        repository.deleteAll(institutionCourseList);
    }

    public List<InstitutionCourse> findByCourse(Course course) throws Exception {
        return repository.findByCourse(course);
    }

    public List<InstitutionCourse> searchByCourse(String search, Province province) throws Exception {
        List<InstitutionCourse> institutionCourseList = new ArrayList<InstitutionCourse>();
        List<Course> courseList = courseService.findByDescriptionStartingWith(search);
        if (courseList.size() > 0) {
            for (Course c : courseList) {
                List<InstitutionCourse> list = findByCourse(c);
                if (list != null && list.size() > 0) {
                    institutionCourseList.addAll(list);
                }

            }
        }

        List<InstitutionCourse> returnList = new ArrayList<InstitutionCourse>();
        if (institutionCourseList != null && institutionCourseList.size() > 0) {
            for (InstitutionCourse ic : institutionCourseList) {
                if (ic.getInstitution().getAddressList() != null) {
                    for (Address address : ic.getInstitution().getAddressList()) {
                        if (address.getProvince() == province) {
                            returnList.add(ic);
                            break;
                        }
                    }
                }

            }
        }
        return returnList;
    }

    public List<InstitutionCourseBean> getInstitutionCourseBean() throws Exception {

        return repository.getInstitutionCourseBean();
    }

    public List<InstitutionCourse> findCourseByCourseName(Long id, String name) throws Exception {
        return repository.findCourseByCourseName(id, name);
    }

    public List<InstitutionCourse> getCourseByDescription(String searchText, ApprovalStatusEnum status) {
        return repository.getCourseByDescription(searchText, status);
    }

    public List<InstitutionCourse> getCouseByDescriptionNoProvince(String searchText, ApprovalStatusEnum status) {
        return repository.getCourseByDescriptionNoProvince(searchText, status);
    }

    public long countAll() {
        return repository.count();
    }

    public List<InstitutionCourse> getCourseByDescriptionAndProvince(String searchText, Long provinceId, ApprovalStatusEnum status) {
        return repository.getCourseByDescriptionAndProvince(searchText, provinceId, status);
    }


    public long countByCourse(Course course) {
        return repository.countByCourse(course);
    }
}
