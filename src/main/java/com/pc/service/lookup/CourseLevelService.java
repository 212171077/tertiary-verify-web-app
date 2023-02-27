package com.pc.service.lookup;

import com.pc.entities.lookup.CourseLevel;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.CourseLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseLevelService extends AbstractService {
    @Autowired
    private CourseLevelRepository repository;

    @Autowired
    private CourseService courseService;

    public void saveCourseLevel(CourseLevel courseLevel) throws Exception {

        if (courseLevel.getId() == null) {
            checkIfExist(courseLevel.getDescription());
            courseLevel.setDeleted(false);
            courseLevel.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                courseLevel.setLastUpdateUser(getCurrentUser());
            }
            courseLevel.setLastUpdateDate(new Date());
        }
        repository.save(courseLevel);
    }

    public void deleteCourseLevel(CourseLevel courseLevel) throws Exception {
        checkIfInUse(courseLevel);
        courseLevel.setDeleted(true);
        repository.save(courseLevel);
    }

    public List<CourseLevel> findAllCourseLevel() throws Exception {
        return repository.findByDeleted(false);
    }

    public CourseLevel findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong, false);
    }

    private void checkIfExist(String desc) throws Exception {
        List<CourseLevel> list = repository.findByDescriptionAndDeleted(desc, false);
        if (list != null && list.size() > 0) {
            throw new Exception("Course Level already exist");
        }
    }

    private void checkIfInUse(CourseLevel courseLevel) throws Exception {
        long count = courseService.countByCourseLevel(courseLevel);
        if (count > 0) {
            throw new Exception("This course level cannot be deleted because it's being used");
        }
    }

}
