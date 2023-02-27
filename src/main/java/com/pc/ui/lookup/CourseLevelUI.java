package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.lookup.CourseLevel;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.CourseLevelService;
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

@Component("courseLevelUI")
@ViewScoped
public class CourseLevelUI extends AbstractUI {

    @Autowired
    CourseLevelService courseService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<CourseLevel> courseLevelList;
    private CourseLevel courseLevel;
    private LazyDataModel<CourseLevel> dataModel;

    @PostConstruct
    public void init() {

        try {
            super.prepareCurrentUser();
            courseLevel = new CourseLevel();
            loadCourseLevelInfo();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveCourseLevel() {
        try {
            courseService.saveCourseLevel(courseLevel);
            displayInfoMssg("Course level added successful...!!");
            courseLevelList = (ArrayList<CourseLevel>) findAllCourseLevel();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteCourseLevel() {
        try {
            courseService.deleteCourseLevel(courseLevel);
            displayWarningMssg("Course level deleted successful...!!");
            courseLevelList = (ArrayList<CourseLevel>) findAllCourseLevel();
            reset();
        } catch (Exception e) {
            reset();
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<CourseLevel> findAllCourseLevel() {
        List<CourseLevel> list = new ArrayList<>();
        try {
            list = courseService.findAllCourseLevel();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }


    public void reset() {
        courseLevel = new CourseLevel();
    }


    public CourseLevel getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(CourseLevel courseLevel) {
        this.courseLevel = courseLevel;
    }

    public ArrayList<CourseLevel> getCourseLevelList() {
        return courseLevelList;
    }

    public void setCourseLevelList(ArrayList<CourseLevel> courseLevelList) {
        this.courseLevelList = courseLevelList;
    }

    public void loadCourseLevelInfo() {
        dataModel = new LazyDataModel<CourseLevel>() {

            private static final long serialVersionUID = 1L;
            private List<CourseLevel> list = new ArrayList<CourseLevel>();

            @Override
            public List<CourseLevel> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<CourseLevel>) entityDAOFacade.getResultList(CourseLevel.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, CourseLevel.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(CourseLevel obj) {
                return obj.getId();
            }

            @Override
            public CourseLevel getRowData(String rowKey) {
                for (CourseLevel obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public LazyDataModel<CourseLevel> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<CourseLevel> dataModel) {
        this.dataModel = dataModel;
    }

}
