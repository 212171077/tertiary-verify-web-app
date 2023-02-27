package com.pc.ui;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.*;
import com.pc.entities.enums.ApprovalStatusEnum;
import com.pc.entities.lookup.City;
import com.pc.entities.lookup.Course;
import com.pc.entities.lookup.RejectReason;
import com.pc.framework.AbstractUI;
import com.pc.service.*;
import com.pc.service.lookup.CityService;
import com.pc.utils.AppUtility;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.map.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.*;

@Component("institutionUI")
@ViewScoped
public class InstitutionUI extends AbstractUI {

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private InstitutionCourseService institutionCourseService;

    @Autowired
    private CityService cityService;

    @Autowired
    private InstitutionRejectReasonService institutionRejectReasonService;

    @Autowired
    private AddressService addressService;

    private Institution institution;

    private boolean skip;

    private boolean done;

    private boolean registrationSuccessful;

    private List<Address> addressList;
    private List<Address> courseAddressList = new ArrayList<>();

    private Address address;

    private ArrayList<Course> courseList = new ArrayList<>();

    private List<RejectReason> rejectReasonList;

    private Course course;

    @Autowired
    private EntityDAOFacade entityDAOFacade;

    private LazyDataModel<Institution> dataModel;

    private LazyDataModel<Institution> pendingApprovalDataModel;

    private List<InstitutionRejectReason> institutionRejectReasonList;

    @PostConstruct
    public void init() {
        try {
            prepareCurrentUser();
            institution = new Institution();
            addressList = new ArrayList<>();
            address = new Address();
            course = new Course();
            allInstitution();
            pendingApprovalInstitution();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }


    public void pendingApprovalInstitution() {
        pendingApprovalDataModel = new LazyDataModel<Institution>() {
            private static final long serialVersionUID = 1L;
            private List<Institution> list = new ArrayList<Institution>();

            @Override
            public List<Institution> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                try {
                    filters.put("supervisor", currentUser);
                    filters.put("status", ApprovalStatusEnum.PendingApproval);
                    list = (List<Institution>) entityDAOFacade.getResultList(Institution.class, first, pageSize, sortField, sortOrder, filters);
                    pendingApprovalDataModel.setRowCount(entityDAOFacade.count(filters, Institution.class));
                } catch (Exception e) {
                    logger.fatal(e);
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

    public void allInstitution() {
        dataModel = new LazyDataModel<Institution>() {
            private static final long serialVersionUID = 1L;
            private List<Institution> list = new ArrayList<Institution>();

            @Override
            public List<Institution> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                try {
                    filters.put("deleted", false);
                    list = (List<Institution>) entityDAOFacade.getResultList(Institution.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, Institution.class));
                } catch (Exception e) {
                    logger.error(e);
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

    public void addAddress() {
        try {
            if (address.getAddressLine1() == null || address.getAddressLine1().isEmpty()) {
                throw new Exception("Please enter residential address line 1");
            } else if (address.getProvince() == null) {
                throw new Exception("Select province");
            } else if (address.getCity() == null) {
                throw new Exception("Select city/town");
            }
            addressList.add(address);
            address = new Address();
            displayInfoMssg("Address added successful");
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeAddress() {
        try {
            addressList.remove(address);
            address = new Address();
            displayWarningMssg("Address deleted successful");
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void prepareAddressUpdate() {
        try {
            addressList.remove(address);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveInstitution() {
        try {
            institutionService.saveInstitution(institution);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void prepareRejectReasons(Institution inst) {
        try {
            rejectReasonList = new ArrayList<>();
            institutionRejectReasonList = institutionRejectReasonService.findByInstitution(inst);
            logger.info("Reject Reason: " + institutionRejectReasonList.size());
            if (institutionRejectReasonList != null) {
                institutionRejectReasonList.forEach(institutionRejectReason -> {
                    rejectReasonList.add(institutionRejectReason.getRejectReason());
                });
            }
            logger.info("Reject Reason 2: " + rejectReasonList.size());
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void approveInstitution() {
        try {
            institution.setAccreditationStartDate(new Date());
            prepareEndDateAndUpdateStatus();
            institution.setStatus(ApprovalStatusEnum.Approved);
            institutionService.saveInstitution(institution);
            institutionService.sendApprovedInstitutionEmail(institution.getCreatorUser(), institution.getInstitutionName());
            displayInfoMssg("Institution approved");
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void rejectInstitution() {
        try {
            institution.setStatus(ApprovalStatusEnum.Rejected);
            institutionService.saveInstitution(institution);
            institutionService.sendRejectedInstitutionEmail(institution.getCreatorUser(), institution.getInstitutionName(), rejectReasonList);
            rejectReasonList.forEach(rejectReason -> {
                InstitutionRejectReason institutionRejectReason = new InstitutionRejectReason();
                institutionRejectReason.setInstitution(institution);
                institutionRejectReason.setRejectReason(rejectReason);
                try {
                    institutionRejectReasonService.saveInstitutionRejectReason(institutionRejectReason);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            RequestContext.getCurrentInstance().execute("PF('rejectDialog').hide()");
            displayWarningMssg("Institution Rejected");
            reset();
        } catch (Exception e) {
            RequestContext.getCurrentInstance().execute("PF('rejectDialog').hide()");
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }


    public void updateExpiredInst() {
        try {
            institution.setStatus(ApprovalStatusEnum.Expired);
            institutionService.saveInstitution(institution);
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


    public void deleteInstitution() {
        try {
            institution.setDeleted(true);
            institutionService.saveInstitution(institution);
            allInstitution();
            displayWarningMssg("Institution deleted");
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Institution> findAllInstitution() {
        List<Institution> list = new ArrayList<>();
        try {
            list = institutionService.findAllInstitution();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }


    public void reset() {
        institution = new Institution();
        address = new Address();
        addressList = new ArrayList<>();
        done = false;
        courseList = new ArrayList<Course>();
    }

    public void prepareUpdate() {
        try {
            courseList.clear();
            List<InstitutionCourse> institutionCourseList = institutionCourseService.findByInstitution(institution);
            if (institutionCourseList != null && institutionCourseList.size() > 0) {
                for (InstitutionCourse ic : institutionCourseList) {
                    List<Address>instCourseAddressList=new ArrayList<>();
                    for (InstCourseAddress instCourseAddress : ic.getInstCourseAddressList()) {
                        instCourseAddressList.add(instCourseAddress.getAddress());
                    }
                    Course course=ic.getCourse();
                    course.setAddressList(instCourseAddressList);
                    courseList.add(course);
                }
            }
            addressList = institution.getAddressList();
            scrollTo("mainForm");
        } catch (Exception e) {
            displayWarningMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void prepareEndDateAndUpdateStatus() {
        Date endDate = AppUtility.addYearsToDate(institution.getAccreditationStartDate(), 5);

        if(endDate.after(new Date()) || endDate.equals( new Date())){
            institution.setStatus(ApprovalStatusEnum.Approved);
        }else {
            institution.setStatus(ApprovalStatusEnum.EndDateExpired);
            displayWarningMssg("Note: based on the date selected, the status of the institution will still be End Date Expired");
        }
        institution.setAccreditationEndDate(endDate);
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public void addCourse() {
        try {
            validateCourse();
            course.setAddressList(courseAddressList);
            courseList.add(course);
            resetCourse();
            displayInfoMssg("Course added successful");
        } catch (Exception e) {
            displayWarningMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void doneAddingCourse() {
        done = true;
    }

    public void removeCourse() {
        courseList.remove(course);
        resetCourse();
    }

    public void resetCourse() {
        course = new Course();
        courseAddressList = new ArrayList<>();
    }

    public void validateCourse() throws Exception {
        if (course == null || course.getId() == null) {
            throw new Exception("Select Course...!!");
        } else if (courseAddressList.isEmpty()) {
            throw new Exception("Select address(s)...!!");
        } else if (courseList.contains(course)) {
            throw new
                    Exception("The course you are trying to add is already in the list");
        }

    }

    public void submitIntDetails() {
        try {
            RequestContext.getCurrentInstance().execute("PF('loading').show()");
            institutionService.submitIntDetails(institution, addressList, currentUser, courseList);
            registrationSuccessful = true;
            reset();
            allInstitution();
            RequestContext.getCurrentInstance().execute("PF('loading').hide()");
        } catch (Exception e) {
            displayWarningMssg(e.getMessage());
            e.printStackTrace();
            RequestContext.getCurrentInstance().execute("PF('loading').hide()");
        }
    }

    public String onFlowProcess(FlowEvent event) {
        registrationSuccessful = false;
        String tab = event.getNewStep();
        if (event.getNewStep().equals("confirmTab")) {
            createQRCode();
        } else if (event.getNewStep().equals("CourseDetails")) {
            if (addressList.size() == 0) {
                displayWarningMssg("Please provide address details");
                tab = "addrDetails";
            }
        }
        if (skip) {
            //reset in case user goes back
            skip = false;
            tab = "QRCode";
        }
        return tab;
    }

    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();
        address.setLatitude(latlng.getLat());
        address.setLongitude(latlng.getLng());
        displayInfoMssg("location Selected");
    }

    public void createQRCode() {
        if (institution.getId() == null) {
            String code = institution.getAccreditationNumber() + "" + getUniqueCode();
            institution.setQrCode(code);
        }
    }

    public String getUniqueCode() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        long millis = System.currentTimeMillis();
        String generatedString = buffer.toString().toUpperCase() + "" + millis;
        return generatedString;
    }

    public void addCourseAddress(Address address) {
        logger.info("Adding address..., AddressList Size: " + courseAddressList.size());
        courseAddressList.add(address);
        logger.info("Address added..., AddressList Size: " + courseAddressList.size());

    }

    public void removeCourseAddress(Address address) {
        logger.info("Removing address..., AddressList Size: " + courseAddressList.size());
        courseAddressList.remove(address);
        logger.info("Address Removed..., AddressList Size: " + courseAddressList.size());
    }

    public boolean courseAddressInTheList(Address address) {
        boolean inTheList = false;
        if (courseAddressList.contains(address)) {
            inTheList = true;
        }
        logger.info("inTheList: " + inTheList);
        return inTheList;
    }


    /**
     * @return the skip
     */
    public boolean isSkip() {
        return skip;
    }

    /**
     * @param skip the skip to set
     */
    public void setSkip(boolean skip) {
        this.skip = skip;
    }


    /**
     * @return the done
     */
    public boolean isDone() {
        return done;
    }

    /**
     * @param done the done to set
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * @return the registrationSuccessful
     */
    public boolean isRegistrationSuccessful() {
        return registrationSuccessful;
    }

    /**
     * @param registrationSuccessful the registrationSuccessful to set
     */
    public void setRegistrationSuccessful(boolean registrationSuccessful) {
        this.registrationSuccessful = registrationSuccessful;
    }


    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LazyDataModel<Institution> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<Institution> dataModel) {
        this.dataModel = dataModel;
    }

    public List<RejectReason> getRejectReasonList() {
        return rejectReasonList;
    }

    public void setRejectReasonList(List<RejectReason> rejectReasonList) {
        this.rejectReasonList = rejectReasonList;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LazyDataModel<Institution> getPendingApprovalDataModel() {
        return pendingApprovalDataModel;
    }

    public void setPendingApprovalDataModel(LazyDataModel<Institution> pendingApprovalDataModel) {
        this.pendingApprovalDataModel = pendingApprovalDataModel;
    }

    public List<InstitutionRejectReason> getInstitutionRejectReasonList() {
        return institutionRejectReasonList;
    }

    public void setInstitutionRejectReasonList(List<InstitutionRejectReason> institutionRejectReasonList) {
        this.institutionRejectReasonList = institutionRejectReasonList;
    }
}
