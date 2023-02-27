package com.pc.service.lookup;

import com.pc.entities.lookup.Course;
import com.pc.entities.lookup.CourseLevel;
import com.pc.entities.lookup.CourseType;
import com.pc.framework.AbstractService;
import com.pc.model.AllCourseDTO;
import com.pc.repositories.lookup.CourseLevelRepository;
import com.pc.repositories.lookup.CourseRepository;
import com.pc.repositories.lookup.CourseTypeRepository;
import com.pc.service.InstitutionCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseService extends AbstractService {
    @Autowired
    private CourseRepository repository;

    @Autowired
    private CourseLevelRepository courseLevelRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private InstitutionCourseService institutionCourseService;

    @Autowired
    private FacultyCourseService facultyCourseService;

    public void saveCourse(Course course) throws Exception {

        if (course.getId() == null) {
            checkIfExist(course);
            course.setDeleted(false);
            course.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                course.setLastUpdateUser(getCurrentUser());
            }
            course.setLastUpdateDate(new Date());
        }
        repository.save(course);
    }

    public void deleteCourse(Course course) throws Exception {
        checkIfInUse(course);
        course.setDeleted(true);
        repository.save(course);
    }


    public List<Course> findAllCourse() throws Exception {
        return repository.findByDeleted(false);
    }

    public List<AllCourseDTO> findAllCourseDTO() throws Exception {
        return repository.findAllCourseDTO();
    }

    public Course findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong, false);
    }

    public List<Course> findByDescriptionStartingWith(String description) throws Exception {
        return repository.findByDeletedAndDescriptionStartingWith(false, description);
    }

    public List<Course> searchByNameTypeAndLevel(String description, CourseType courseType, CourseLevel courseLevel) {
        return repository.searchByNameTypeAndLevelAndDeleted(description, courseType, courseLevel, false);
    }


    public List<Course> findByDescriptionAndCourseTypeAndCourseLevel(String description, String courseType, String courseLevel) {
        List<CourseLevel> courseLevels = courseLevelRepository.findByDescriptionAndDeleted(courseLevel, false);
        List<CourseType> courseTypes = courseTypeRepository.findByDescriptionAndDeleted(courseType, false);
        if (courseLevels != null && courseLevels.size() == 1 && courseTypes != null && courseTypes.size() == 1) {
            return repository.findByDescriptionAndCourseTypeAndCourseLevelAndDeleted(description, courseTypes.get(0), courseLevels.get(0), false);
        } else {
            return null;
        }


    }

    public long countByCourseLevel(CourseLevel courseLevel) {
        return repository.countByCourseLevelAndDeleted(courseLevel, false);
    }

    public long countByCourseType(CourseType courseType) {
        return repository.countByCourseTypeAndDeleted(courseType, false);
    }

    private void checkIfExist(Course course) throws Exception {
        List<Course> list = repository.findByDescriptionAndCourseTypeAndCourseLevelAndDeleted(course.getDescription(), course.getCourseType(), course.getCourseLevel(), false);
        if (list != null && list.size() > 0) {
            throw new Exception("Course already exist");
        }
    }

    private void checkIfInUse(Course course) throws Exception {
        long count = institutionCourseService.countByCourse(course);
        if (count > 0) {
            throw new Exception("This course cannot be deleted because it's being used");
        } else {
            count = facultyCourseService.countByCourse(course);
            if (count > 0) {
                throw new Exception("This course cannot be deleted because it's being used");
            }
        }
    }
}
