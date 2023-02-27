package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.lookup.CourseType;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.CourseTypeService;
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

@Component("courseTypeUI")
@ViewScoped
public class CourseTypeUI extends AbstractUI {

    @Autowired
    CourseTypeService courseTypeService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<CourseType> courseTypeList;
    private LazyDataModel<CourseType> dataModel;
    private CourseType courseType;

    @PostConstruct
    public void init() {

        try {
            super.prepareCurrentUser();
            courseType = new CourseType();
            loadCourseTypeInfo();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveCourseType() {
        try {
            courseTypeService.saveCourseType(courseType);
            displayInfoMssg("Course type added successful...!!");
            courseTypeList = (ArrayList<CourseType>) findAllCourseType();
            reset();
        } catch (Exception e) {
            reset();
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteCourseType() {
        try {
            courseTypeService.deleteCourseType(courseType);
            displayWarningMssg("Course type deleted successful...!!");
            courseTypeList = (ArrayList<CourseType>) findAllCourseType();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<CourseType> findAllCourseType() {
        List<CourseType> list = new ArrayList<>();
        try {
            list = courseTypeService.findAllCourseType();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }


    public void reset() {
        courseType = new CourseType();
    }


    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public ArrayList<CourseType> getCourseTypeList() {
        return courseTypeList;
    }

    public void setCourseTypeList(ArrayList<CourseType> courseTypeList) {
        this.courseTypeList = courseTypeList;
    }

    public void loadCourseTypeInfo() {
        dataModel = new LazyDataModel<CourseType>() {

            private static final long serialVersionUID = 1L;
            private List<CourseType> list = new ArrayList<CourseType>();

            @Override
            public List<CourseType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<CourseType>) entityDAOFacade.getResultList(CourseType.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, CourseType.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(CourseType obj) {
                return obj.getId();
            }

            @Override
            public CourseType getRowData(String rowKey) {
                for (CourseType obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public LazyDataModel<CourseType> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<CourseType> dataModel) {
        this.dataModel = dataModel;
    }

}
