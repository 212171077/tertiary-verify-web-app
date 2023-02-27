package com.pc.ui;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.Address;
import com.pc.entities.Institution;
import com.pc.entities.ReportedInstitutionCourse;
import com.pc.entities.ReportedInstitutions;
import com.pc.entities.enums.ReportTypeEnum;
import com.pc.entities.enums.ReportedInstitutionsStatus;
import com.pc.entities.lookup.City;
import com.pc.entities.lookup.Course;
import com.pc.framework.AbstractUI;
import com.pc.service.ReportedInstitutionCourseService;
import com.pc.service.ReportedInstitutionsService;
import com.pc.service.lookup.CityService;
import com.pc.service.lookup.CourseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
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

@Component("reportedInstitutionsUI")
@ViewScoped
public class ReportedInstitutionsUI extends AbstractUI {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private ReportedInstitutionsService reportedInstitutionsService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ReportedInstitutionCourseService reportedInstitutionCourseService;

    @Autowired
    private EntityDAOFacade entityDAOFacade;

    @Autowired
    private CourseService courseService;

    private ReportedInstitutions reportedInstitutions;
    private Address address;
    private String mssg;
    private ArrayList<ReportedInstitutionCourse> reportedInstitutionCourseList = new ArrayList<>();
    private ReportedInstitutionCourse reportedInstitutionCourse = new ReportedInstitutionCourse();
    private MapModel circleModel;
    private boolean showMap;
    private String refNumber;
    private boolean showAddCourse;
    private boolean specifyCourseDetails;
    private Institution selectedInstitution;
    private LazyDataModel<ReportedInstitutions> dataModel;

    @PostConstruct
    public void init() {
        try {
            prepareCurrentUser();
            reportedInstitutions = new ReportedInstitutions();
            address = new Address();
            showMap = false;
            showAddCourse = false;

            if (currentUser != null) {
                allReportedInstitutions();
            }

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void allReportedInstitutions() {
        dataModel = new LazyDataModel<ReportedInstitutions>() {

            private static final long serialVersionUID = 1L;
            private List<ReportedInstitutions> list = new ArrayList<ReportedInstitutions>();

            @Override
            public List<ReportedInstitutions> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                                   Map<String, Object> filters) {

                try {

                    if (!hasRole("ADMIN")) {
                        filters.put("investigator", currentUser);
                    }
                    list = (List<ReportedInstitutions>) entityDAOFacade.getResultList(ReportedInstitutions.class, first,
                            pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, ReportedInstitutions.class));
                } catch (Exception e) {
                    logger.error(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(ReportedInstitutions obj) {
                return obj.getId();
            }

            @Override
            public ReportedInstitutions getRowData(String rowKey) {
                for (ReportedInstitutions obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public void prepareInstitution() {
        reportedInstitutions.setInstitutionName(selectedInstitution.getInstitutionName());
        reportedInstitutions.setKnownAs(selectedInstitution.getKnownAs());
        reportedInstitutions.setInstitution(selectedInstitution);
    }

    public void clearInstitution() {
        selectedInstitution = null;
        reportedInstitutions.setAddress(null);
        reportedInstitutions.setInstitutionName(null);
        reportedInstitutions.setKnownAs(null);
        reportedInstitutions.setInstitution(null);
    }

    public void prepareAddCouse() {
        clearInstitution();
        showAddCourse = reportedInstitutions.getReportType() == ReportTypeEnum.BogusCourses;

        getLog(this.getClass()).info("Preparing Add Couse, showAddCourse: " + showAddCourse);
    }

    public void addReportedInstitutionCourse() {
        try {
            addReportedInstitutionCourseValidation();
            reportedInstitutionCourseList.add(reportedInstitutionCourse);
            reportedInstitutionCourse = new ReportedInstitutionCourse();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void addReportedInstitutionCourseValidation() throws Exception {
        if (!specifyCourseDetails) {
            if (reportedInstitutionCourse.getCourse() == null
                    || reportedInstitutionCourse.getCourse().getId() == null) {
                throw new Exception("Please enter course name");
            }
            for (ReportedInstitutionCourse c : reportedInstitutionCourseList) {
                if (c.getCourse() != null && reportedInstitutionCourse.getCourse() != null) {
                    if (c.getCourse().equals(reportedInstitutionCourse.getCourse())) {
                        throw new Exception("The course has been added already");
                    }
                } else if (reportedInstitutionCourse.getCourse() == null && c.getCourse() == null) {
                    if (reportedInstitutionCourse.getCourseDesc().equalsIgnoreCase(c.getCourseDesc()) &&
                            reportedInstitutionCourse.getCourseLevel().equalsIgnoreCase(c.getCourseLevel()) &&
                            reportedInstitutionCourse.getCourseType().equalsIgnoreCase(c.getCourseType())) {
                        throw new Exception("The course has been added already");
                    }
                }
            }
        } else {
            if (reportedInstitutionCourse.getCourseDesc().isEmpty()
                    || reportedInstitutionCourse.getCourseDesc().equalsIgnoreCase("")) {
                throw new Exception("Please enter course name");
            }
            if (reportedInstitutionCourse.getCourseLevel().isEmpty()
                    || reportedInstitutionCourse.getCourseLevel().equalsIgnoreCase("")) {
                throw new Exception("Please enter course level");
            }
            if (reportedInstitutionCourse.getCourseType().isEmpty()
                    || reportedInstitutionCourse.getCourseType().equalsIgnoreCase("")) {
                throw new Exception("Please enter course type");
            }

            List<Course> courseList = courseService.findByDescriptionAndCourseTypeAndCourseLevel(reportedInstitutionCourse.getCourseDesc(), reportedInstitutionCourse.getCourseType(), reportedInstitutionCourse.getCourseLevel());
            if(courseList !=null && courseList.size()>0){
                throw new Exception("The course already in our database, please use the search course option to add the course.");
            }

            for (ReportedInstitutionCourse c : reportedInstitutionCourseList) {
                if (c.getCourseDesc() != null && c.getCourseLevel() != null && c.getCourseType() != null) {
                    if (c.getCourseDesc().equalsIgnoreCase(reportedInstitutionCourse.getCourseDesc())
                            && c.getCourseLevel().equalsIgnoreCase(reportedInstitutionCourse.getCourseLevel())
                            && c.getCourseType().equalsIgnoreCase(reportedInstitutionCourse.getCourseType())) {
                        throw new Exception("The course has been added already");
                    }

                }
            }
        }
    }

    public void removeReportedInstitutionCourse() {
        reportedInstitutionCourseList.remove(reportedInstitutionCourse);
        reportedInstitutionCourse = new ReportedInstitutionCourse();
    }

    public void prepareMapModel() {
        try {

            circleModel = new DefaultMapModel();
            showMap = false;
            if (reportedInstitutions.getAddress() != null && reportedInstitutions.getAddress().getLatitude() != null
                    && reportedInstitutions.getAddress().getLongitude() != null) {
                showMap = true;
                LatLng coord1 = new LatLng(reportedInstitutions.getAddress().getLatitude(),
                        reportedInstitutions.getAddress().getLongitude());
                // Circle
                Circle circle1 = new Circle(coord1, 500);
                circle1.setStrokeColor("#d93c3c");
                circle1.setFillColor("#d93c3c");
                circle1.setFillOpacity(0.5);
                circleModel.addOverlay(circle1);
            }

            reportedInstitutionCourseList = (ArrayList<ReportedInstitutionCourse>) reportedInstitutionCourseService
                    .findByReportedInstitution(reportedInstitutions);
            //Scrolling to the details
            scrollTo("investigationForm");

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void findByRefNumber() {
        try {
            reportedInstitutions = reportedInstitutionsService.findByRefNumber(refNumber);
            if (reportedInstitutions == null || reportedInstitutions.getId() == null) {
                displayWarningMssg("Invalid reference number");
            } else {
                RequestContext.getCurrentInstance().execute("PF('statusDialog').show()");
            }

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();
        address.setLatitude(latlng.getLat());
        address.setLongitude(latlng.getLng());
        displayInfoMssg("location Selected");
    }

    public MapModel getCircleModel() {
        return circleModel;
    }

    /**
     * @param circleModel the circleModel to set
     */
    public void setCircleModel(MapModel circleModel) {
        this.circleModel = circleModel;
    }

    public void onCircleSelect(OverlaySelectEvent event) {
        displayInfoMssg("Institution Location");
    }

    public void saveReportedInstitutions() {
        try {
            reportedInstitutionsService.saveReportedInstitutions(reportedInstitutions);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void submitReportedInstitutions() {
        try {
            if (reportedInstitutions.getReportType() == null) {
                throw new Exception("Please select type of report");
            }
            if (reportedInstitutions.getReportType() == ReportTypeEnum.BogusCourses
                    && reportedInstitutionCourseList.size() <= 0) {
                throw new Exception("Please provide at least one bogus course");
            }
            if (reportedInstitutions.getReportType() == ReportTypeEnum.BogusCourses) {
                address = null;
            } else {
                address.setAddressLine4("");
            }
            reportedInstitutionsService.submitReportedInstitutions(reportedInstitutions, address, reportedInstitutionCourseList);
            mssg = "Institution report submited. Report Reference Number: " + reportedInstitutions.getRefNumber()
                    + ". Please keep this reference number because it will be required when checking the status of the report";
            displayInfoMssg(mssg);
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateStatus() {
        try {
            reportedInstitutionsService.saveReportedInstitutions(reportedInstitutions);
            displayInfoMssg("Status Updated");
            if (reportedInstitutions.getStatus() == ReportedInstitutionsStatus.Close) {
                reset();

            }
            allReportedInstitutions();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteReportedInstitutions() {
        try {
            reportedInstitutionsService.deleteReportedInstitutions(reportedInstitutions);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<ReportedInstitutions> findAllReportedInstitutions() {
        List<ReportedInstitutions> list = new ArrayList<>();
        try {
            list = reportedInstitutionsService.findAllReportedInstitutions();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<ReportedInstitutions> findAllReportedInstitutionsPageable() {
        Pageable p = null;
        try {
            return reportedInstitutionsService.findAllReportedInstitutions(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<ReportedInstitutions> findAllReportedInstitutionsSort() {
        Sort s = null;
        List<ReportedInstitutions> list = new ArrayList<>();
        try {
            list = reportedInstitutionsService.findAllReportedInstitutions(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public List<City> getSelectItemsCity() {

        List<City> l = null;
        try {

            if (address != null && address.getProvince() != null) {
                l = cityService.findByProvince(address.getProvince());
            }

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public void reset() {
        reportedInstitutions = new ReportedInstitutions();
        address = new Address();
        reportedInstitutionCourseList = new ArrayList<>();
        showAddCourse = false;
    }

    public ReportedInstitutions getReportedInstitutions() {
        return reportedInstitutions;
    }

    public void setReportedInstitutions(ReportedInstitutions reportedInstitutions) {
        this.reportedInstitutions = reportedInstitutions;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return the mssg
     */
    public String getMssg() {
        return mssg;
    }

    /**
     * @return the reportedInstitutionsList
     */
    /*
     * public ArrayList<ReportedInstitutions> getReportedInstitutionsList() { return
     * reportedInstitutionsList; }
     */

    /**
     * @param reportedInstitutionsList the reportedInstitutionsList to set
     */
    /*
     * public void setReportedInstitutionsList(ArrayList<ReportedInstitutions>
     * reportedInstitutionsList) { this.reportedInstitutionsList =
     * reportedInstitutionsList; }
     */

    /**
     * @param mssg the mssg to set
     */
    public void setMssg(String mssg) {
        this.mssg = mssg;
    }

    /**
     * @return the showMap
     */
    public boolean isShowMap() {
        return showMap;
    }

    /**
     * @param showMap the showMap to set
     */
    public void setShowMap(boolean showMap) {
        this.showMap = showMap;
    }

    /**
     * @return the refNumber
     */
    public String getRefNumber() {
        return refNumber;
    }

    /**
     * @param refNumber the refNumber to set
     */
    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    /**
     * @return the reportedInstitutionCourseList
     */
    public ArrayList<ReportedInstitutionCourse> getReportedInstitutionCourseList() {
        return reportedInstitutionCourseList;
    }

    /**
     * @param reportedInstitutionCourseList the reportedInstitutionCourseList to set
     */
    public void setReportedInstitutionCourseList(ArrayList<ReportedInstitutionCourse> reportedInstitutionCourseList) {
        this.reportedInstitutionCourseList = reportedInstitutionCourseList;
    }

    /**
     * @return the reportedInstitutionCourse
     */
    public ReportedInstitutionCourse getReportedInstitutionCourse() {
        return reportedInstitutionCourse;
    }

    /**
     * @param reportedInstitutionCourse the reportedInstitutionCourse to set
     */
    public void setReportedInstitutionCourse(ReportedInstitutionCourse reportedInstitutionCourse) {
        this.reportedInstitutionCourse = reportedInstitutionCourse;
    }

    /**
     * @return the showAddCourse
     */
    public boolean isShowAddCourse() {
        return showAddCourse;
    }

    /**
     * @param showAddCourse the showAddCourse to set
     */
    public void setShowAddCourse(boolean showAddCourse) {
        this.showAddCourse = showAddCourse;
    }

    public boolean isSpecifyCourseDetails() {
        return specifyCourseDetails;
    }

    public void setSpecifyCourseDetails(boolean specifyCourseDetails) {
        this.specifyCourseDetails = specifyCourseDetails;
    }

    public Institution getSelectedInstitution() {
        return selectedInstitution;
    }

    public void setSelectedInstitution(Institution selectedInstitution) {
        this.selectedInstitution = selectedInstitution;
    }

    public LazyDataModel<ReportedInstitutions> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<ReportedInstitutions> dataModel) {
        this.dataModel = dataModel;
    }

}
