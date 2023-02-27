package com.pc.ui;

import com.pc.dao.InstitutionDAO;
import com.pc.entities.Institution;
import com.pc.entities.InstitutionCourse;
import com.pc.entities.enums.ApprovalStatusEnum;
import com.pc.entities.enums.SearchTypeEnum;
import com.pc.entities.lookup.Course;
import com.pc.entities.lookup.Province;
import com.pc.framework.AbstractUI;
import com.pc.service.InstitutionCourseService;
import com.pc.service.InstitutionService;
import com.pc.service.JasperService;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.map.MapModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component("searchUI")
@ViewScoped
public class SearchUI extends AbstractUI {

    public Institution institution;
    public List<Institution> institutionList = new ArrayList<>();
    private Logger log = Logger.getGlobal();
    private SearchTypeEnum searchType;
    private String searchText;
    private String errorMssg;
    private boolean searchByInstitution;
    private boolean searchByCourse;
    private boolean searchByAccNum;
    private MapModel circleModel;

    private Province province;

    private boolean showProvince;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private InstitutionCourseService institutionCourseService;
    private List<InstitutionCourse> institutionCourseList = new ArrayList<>();
    private List<Course> courseList;
    private InstitutionCourse institutionCourse;
    private LazyDataModel<Institution> searchByInstituionDataModel;

    @Autowired
    private InstitutionDAO institutionDAO;

    private boolean showMap;

    @PostConstruct
    public void init() {
        institution = new Institution();
    }

    public void search() {
        try {

            if (searchType == null) {
                throw new Exception("Please select search type");
            }
            if (searchType == SearchTypeEnum.SearchByInstituion) {
                searchByInstitution = true;
                searchByCourse = false;
                searchByAccNum = false;
                long count = institutionService.countByInstitutionName(searchText, ApprovalStatusEnum.Expired);
                if (count <= 0) {
                    errorMssg = searchText + " is NOT registered with the Department of Higher Education and Training.";
                } else {
                    searchByInstituionInfo();
                    errorMssg = null;
                }
            } else if (searchType == SearchTypeEnum.SearchByAccreditationNumber) {
                searchByInstitution = false;
                searchByCourse = false;
                searchByAccNum = true;
                searchByInstituionInfo();
                institutionList = institutionService.findByAccreditationNumber(searchText);
                if (institutionList == null || institutionList.size() <= 0) {
                    errorMssg = "Accreditation number " + searchText
                            + " is NOT registered with the Department of Higher Education and Training";
                } else {
                    errorMssg = null;
                }

            } else if (searchType == SearchTypeEnum.SearhcByCourse) {
                searchByInstitution = false;
                searchByCourse = true;
                searchByAccNum = false;

                logger.info("province: " + province.getId());
                institutionCourseList = institutionCourseService
                        .getCourseByDescriptionAndProvince(searchText, province.getId(), ApprovalStatusEnum.Approved);

                if (institutionCourseList == null || institutionCourseList.size() <= 0) {
                    errorMssg = "Course not Found";
                } else {
                    errorMssg = null;
                }

            }

            if (errorMssg != null) {
                displayWarningMssg(errorMssg);
            } else {
                RequestContext.getCurrentInstance().execute("PF('searchDialog').show()");
            }

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void searchByInstituionInfo() {
        searchByInstituionDataModel = new LazyDataModel<Institution>() {
            private static final long serialVersionUID = 1L;
            private List<Institution> list = new ArrayList<Institution>();

            @Override
            public List<Institution> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                          Map<String, Object> filters) {

                try {

                    if (filters.isEmpty()) {
                        if (searchType == SearchTypeEnum.SearchByInstituion) {
                            filters.put("institutionName", searchText);
                        } else {
                            filters.put("accreditationNumber", searchText);
                        }
                    }
                    list = (List<Institution>) institutionDAO.getActiveInstitutionsResultList(Institution.class, first,
                            pageSize, sortField, sortOrder, filters);

                    searchByInstituionDataModel
                            .setRowCount(institutionDAO.countActiveInstitution(filters, Institution.class));
                } catch (Exception e) {
                    logger.fatal(e);
                    e.printStackTrace();
                }
                return list;
            }

            @Override
            public Object getRowKey(Institution obj) {
                return obj.getId();
            }

            @Override
            public Institution getRowData(String rowKey) {
                for (Institution obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public void clearSearch() {
        institution = null;
        institutionCourseList = new ArrayList<>();
        institutionList = new ArrayList<>();
        searchType = null;
        searchText = null;
        errorMssg = null;
        showProvince = false;
        province = null;
        institutionCourse = null;
        searchByInstitution = false;
        searchByCourse = false;
        searchByAccNum = false;
        // course=null;
    }

    public void searchTypeClear() {
        institution = null;
        institutionCourseList = new ArrayList<>();
        institutionList = new ArrayList<>();
        searchText = null;
        errorMssg = null;
        showProvince = false;
        province = null;
        searchByInstitution = false;
        searchByCourse = false;
        searchByAccNum = false;
        showProvince = searchType == SearchTypeEnum.SearhcByCourse;
        // course=null;
    }

    public void prepareInstitutionData() {
        try {
            courseList = new ArrayList<>();
            institutionCourseList = institutionCourseService
                    .findByInstitution(institution);
            institutionCourseList.forEach(ic -> {
                courseList.add(ic.getCourse());
            });
            logger.info("Total Courses: " + institutionCourseList.size());
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void downloadQRCode() {
        try {
            JasperService.instance().printQRCode(institution.getInstitutionName(), institution.getQrCode());
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    /**
     * @return the searchType
     */
    public SearchTypeEnum getSearchType() {
        return searchType;
    }

    /**
     * @param searchType the searchType to set
     */
    public void setSearchType(SearchTypeEnum searchType) {
        this.searchType = searchType;
    }

    /**
     * @return the searchText
     */
    public String getSearchText() {
        return searchText;
    }

    /**
     * @param searchText the searchText to set
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    /**
     * @return the errorMssg
     */
    public String getErrorMssg() {
        return errorMssg;
    }

    /**
     * @param errorMssg the errorMssg to set
     */
    public void setErrorMssg(String errorMssg) {
        this.errorMssg = errorMssg;
    }

    /**
     * @return the searchByInstitution
     */
    public boolean isSearchByInstitution() {
        return searchByInstitution;
    }

    /**
     * @param searchByInstitution the searchByInstitution to set
     */
    public void setSearchByInstitution(boolean searchByInstitution) {
        this.searchByInstitution = searchByInstitution;
    }

    /**
     * @return the searchByCourse
     */
    public boolean isSearchByCourse() {
        return searchByCourse;
    }

    public void setSearchByCourse(boolean searchByCourse) {
        this.searchByCourse = searchByCourse;
    }

    public MapModel getCircleModel() {
        return circleModel;
    }

    public void setCircleModel(MapModel circleModel) {
        this.circleModel = circleModel;
    }

    public boolean isShowMap() {
        return showMap;
    }

    public void setShowMap(boolean showMap) {
        this.showMap = showMap;
    }

    public boolean isSearchByAccNum() {
        return searchByAccNum;
    }

    public void setSearchByAccNum(boolean searchByAccNum) {
        this.searchByAccNum = searchByAccNum;
    }

    public InstitutionCourse getInstitutionCourse() {
        return institutionCourse;
    }

    public void setInstitutionCourse(InstitutionCourse institutionCourse) {
        this.institutionCourse = institutionCourse;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public boolean isShowProvince() {
        return showProvince;
    }

    public void setShowProvince(boolean showProvince) {
        this.showProvince = showProvince;
    }

    public LazyDataModel<Institution> getSearchByInstituionDataModel() {
        return searchByInstituionDataModel;
    }

    public void setSearchByInstituionDataModel(LazyDataModel<Institution> searchByInstituionDataModel) {
        this.searchByInstituionDataModel = searchByInstituionDataModel;
    }

    public List<Institution> getInstitutionList() {
        return institutionList;
    }

    /**
     * @param institutionList the institutionList to set
     */
    public void setInstitutionList(ArrayList<Institution> institutionList) {
        this.institutionList = institutionList;
    }

    public void setInstitutionList(List<Institution> institutionList) {
        this.institutionList = institutionList;
    }

    public List<InstitutionCourse> getInstitutionCourseList() {
        return institutionCourseList;
    }

    public void setInstitutionCourseList(ArrayList<InstitutionCourse> institutionCourseList) {
        this.institutionCourseList = institutionCourseList;
    }

    public void setInstitutionCourseList(List<InstitutionCourse> institutionCourseList) {
        this.institutionCourseList = institutionCourseList;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}
