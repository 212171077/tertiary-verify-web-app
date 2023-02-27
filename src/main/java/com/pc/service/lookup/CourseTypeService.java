package com.pc.service.lookup;

import com.pc.entities.lookup.CourseType;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.CourseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseTypeService extends AbstractService {
    @Autowired
    private CourseTypeRepository repository;

    @Autowired
    private CourseService courseService;

    public void saveCourseType(CourseType courseType) throws Exception {
        if (courseType.getId() == null) {
            checkIfExist(courseType.getDescription());
            courseType.setDeleted(false);
            courseType.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                courseType.setLastUpdateUser(getCurrentUser());
            }
            courseType.setLastUpdateDate(new Date());
        }
        repository.save(courseType);
    }

    public void deleteCourseType(CourseType courseType) throws Exception {
        checkIfInUse(courseType);
        courseType.setDeleted(true);
        repository.save(courseType);
    }

    public List<CourseType> findAllCourseType() throws Exception {
        return repository.findByDeleted(false);
    }

    public CourseType findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong, false);
    }

    private void checkIfExist(String desc) throws Exception {
        List<CourseType> list = repository.findByDescriptionAndDeleted(desc, false);
        if (list != null && list.size() > 0) {
            throw new Exception("Course Type already exist");
        }
    }

    private void checkIfInUse(CourseType courseType) throws Exception {
        long count = courseService.countByCourseType(courseType);
        if (count > 0) {
            throw new Exception("This course type cannot be deleted because it's being used");
        }
    }

}
