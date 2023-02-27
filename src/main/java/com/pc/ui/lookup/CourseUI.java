package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.lookup.Course;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.CourseService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("courseUI")
@ViewScoped
public class CourseUI extends AbstractUI {

    @Autowired
    CourseService courseService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<Course> courseList;
    private Course course;
    private LazyDataModel<Course> dataModel;

    @PostConstruct
    public void init() {

        try {
            super.prepareCurrentUser();
            course = new Course();
            loadCourseInfo();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveCourse() {
        try {
            courseService.saveCourse(course);
            displayInfoMssg("Course added successful...!!");
            courseList = (ArrayList<Course>) findAllCourse();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }


    public void deleteCourse() {
        try {
            courseService.deleteCourse(course);
            displayWarningMssg("Course deleted successful...!!");
            courseList = (ArrayList<Course>) findAllCourse();
            reset();
        } catch (Exception e) {
            reset();
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Course> findAllCourse() {
        List<Course> list = new ArrayList<>();
        try {
            list = courseService.findAllCourse();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public void reset() {
        course = new Course();
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    public LazyDataModel<Course> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<Course> dataModel) {
        this.dataModel = dataModel;
    }

    public void loadCourseInfo() {
        dataModel = new LazyDataModel<Course>() {

            private static final long serialVersionUID = 1L;
            private List<Course> list = new ArrayList<Course>();

            @Override
            public List<Course> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<Course>) entityDAOFacade.getResultList(Course.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, Course.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(Course obj) {
                return obj.getId();
            }

            @Override
            public Course getRowData(String rowKey) {
                for (Course obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

}
