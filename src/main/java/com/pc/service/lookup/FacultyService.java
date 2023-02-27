package com.pc.service.lookup;

import com.pc.entities.lookup.Course;
import com.pc.entities.lookup.Faculty;
import com.pc.entities.lookup.FacultyCourse;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FacultyService extends AbstractService {
    @Autowired
    FacultyRepository repository;

    @Autowired
    CourseService courseService;

    @Autowired
    FacultyCourseService facultyCourseServiceService;

    @Autowired
    private FacultyCourseService facultyCourseService;

    public void saveFaculty(Faculty faculty) throws Exception {
        checkIfExist(faculty.getDescription());
        if (faculty.getId() == null) {
            faculty.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                faculty.setLastUpdateUser(getCurrentUser());
            }
            faculty.setLastUpdateDate(new Date());
        }
        repository.save(faculty);
    }

    public void submitFaculty(Faculty faculty, List<Course> courseList) throws Exception {
        if (faculty.getId() == null) {
            faculty.setCreateDate(new Date());
            repository.save(faculty);
            if (courseList != null && courseList.size() > 0) {
                for (Course course : courseList) {
                    FacultyCourse fc = new FacultyCourse();
                    fc.setCourse(course);
                    fc.setFaculty(faculty);
                    facultyCourseServiceService.saveFacultyCourse(fc);
                }
            }
        } else {
            ArrayList<FacultyCourse> currentCouseList = (ArrayList<FacultyCourse>) facultyCourseService.findByFaculty(faculty);
            facultyCourseService.deleteAll(currentCouseList);
            repository.save(faculty);
            if (courseList != null && courseList.size() > 0) {
                for (Course course : courseList) {
                    FacultyCourse fc = new FacultyCourse();
                    fc.setCourse(course);
                    fc.setFaculty(faculty);
                    facultyCourseServiceService.saveFacultyCourse(fc);
                }
            }

        }

    }

    public void deleteFaculty(Faculty faculty) throws Exception {
        repository.delete(faculty);
    }

    public void deleteFacultyByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<Faculty> findAllFaculty() throws Exception {
        return repository.findAll();
    }

    public Page<Faculty> findAllFaculty(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<Faculty> findAllFaculty(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public Faculty findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong,false);
    }

    private void checkIfExist(String desc) throws Exception {
        List<Faculty> list = repository.findByDescriptionAndDeleted(desc,false);
        if (list != null && list.size() > 0) {
            throw new Exception("Faculty already exist");
        }
    }


}
