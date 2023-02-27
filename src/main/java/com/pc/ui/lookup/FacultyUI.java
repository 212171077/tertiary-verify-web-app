package com.pc.ui.lookup;

import com.pc.entities.lookup.Course;
import com.pc.entities.lookup.Faculty;
import com.pc.entities.lookup.FacultyCourse;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.FacultyCourseService;
import com.pc.service.lookup.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("facultyUI")
@ViewScoped
public class FacultyUI extends AbstractUI {

    @Autowired
    FacultyService facultyService;
    private ArrayList<Faculty> facultyList;

    @Autowired
    private FacultyCourseService facultyCourseService;

    private Faculty faculty;

    private Course course;

    private List<Course> courseList = new ArrayList<>();

    @PostConstruct
    public void init() {

        try {
            super.prepareCurrentUser();
            faculty = new Faculty();
            facultyList = (ArrayList<Faculty>) findAllFaculty();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveFaculty() {
        try {
            facultyService.saveFaculty(faculty);
            displayInfoMssg("Faculty added successful...!!");
            facultyList = (ArrayList<Faculty>) findAllFaculty();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteAndCourseFaculty() {
        try {
            List<FacultyCourse> list = facultyCourseService.findByFaculty(faculty);
            if (list != null) {
                for (FacultyCourse fc : list) {
                    facultyCourseService.deleteFacultyCourse(fc);
                }
            }
            facultyService.deleteFaculty(faculty);
            displayWarningMssg("Faculty deleted..!!");
            facultyList = (ArrayList<Faculty>) findAllFaculty();
            reset();
        } catch (Exception e) {
            reset();
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }


    public void prepareUpdate() {
        try {

            List<FacultyCourse> list = facultyCourseService.findByFaculty(faculty);
            if (list != null) {
                for (FacultyCourse fc : list) {
                    courseList.add(fc.getCourse());
                }
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void submitFaculty() {
        try {
            facultyService.submitFaculty(faculty, courseList);
            displayInfoMssg("Update Successful...!!");
            facultyList = (ArrayList<Faculty>) findAllFaculty();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }


    public void addCourse() {
        try {
            validateAddCourse();
            courseList.add(course);
            course = new Course();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeCourse() {
        courseList.remove(course);
    }

    public void validateAddCourse() throws Exception {
        if (course == null || course.getId() == null) {
            throw new Exception("Please select course...!!");
        } else if (courseList.contains(course)) {
            throw new Exception("The course you selected is already in the list");
        }
    }

    public void deleteFaculty() {
        try {
            facultyService.deleteFaculty(faculty);
            displayWarningMssg("Update Successful...!!");
            facultyList = (ArrayList<Faculty>) findAllFaculty();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Faculty> findAllFaculty() {
        List<Faculty> list = new ArrayList<>();
        try {
            list = facultyService.findAllFaculty();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<Faculty> findAllFacultyPageable() {
        Pageable p = null;
        try {
            return facultyService.findAllFaculty(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Faculty> findAllFacultySort() {
        Sort s = null;
        List<Faculty> list = new ArrayList<>();
        try {
            list = facultyService.findAllFaculty(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        faculty = new Faculty();
        courseList = new ArrayList<Course>();
    }


    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public ArrayList<Faculty> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(ArrayList<Faculty> facultyList) {
        this.facultyList = facultyList;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

}
