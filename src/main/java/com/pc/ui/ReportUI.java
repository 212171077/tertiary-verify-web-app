package com.pc.ui;

import com.pc.entities.enums.ReportedInstitutionsStatus;
import com.pc.framework.AbstractUI;
import com.pc.service.InstitutionCourseService;
import com.pc.service.InstitutionService;
import com.pc.service.ReportedInstitutionsService;
import com.pc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Component
@ManagedBean(name = "reportUI")
@ViewScoped
public class ReportUI extends AbstractUI {


    @Autowired
    InstitutionService institutionService;

    @Autowired
    UserService userService;

    @Autowired
    InstitutionCourseService istitutionCourseService;

    @Autowired
    ReportedInstitutionsService reportedInstitutionsService;


    private long totalCourse;

    private long totalInstitution;

    private long totalInstitutionReported;

    private long totalInstitutionUndetInvestigation;

    @PostConstruct
    public void init() {
        try {
            preparReports();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void preparReports() throws Exception {
        totalInstitutionUndetInvestigation = reportedInstitutionsService.countByStatus(ReportedInstitutionsStatus.UnderInvestigation);
        totalInstitutionReported = reportedInstitutionsService.countAll();
        totalCourse = istitutionCourseService.countAll();
        totalInstitution = institutionService.countAll();
    }

    public long getTotalInstitution() {
        return totalInstitution;
    }

    public void setTotalInstitution(long totalInstitution) {
        this.totalInstitution = totalInstitution;
    }

    public long getTotalCourse() {
        return totalCourse;
    }

    public void setTotalCourse(long totalCourse) {
        this.totalCourse = totalCourse;
    }

    public ReportedInstitutionsService getReportedInstitutionsService() {
        return reportedInstitutionsService;
    }

    public void setReportedInstitutionsService(ReportedInstitutionsService reportedInstitutionsService) {
        this.reportedInstitutionsService = reportedInstitutionsService;
    }

    public long getTotalInstitutionReported() {
        return totalInstitutionReported;
    }

    public void setTotalInstitutionReported(long totalInstitutionReported) {
        this.totalInstitutionReported = totalInstitutionReported;
    }

    public long getTotalInstitutionUndetInvestigation() {
        return totalInstitutionUndetInvestigation;
    }

    public void setTotalInstitutionUndetInvestigation(long totalInstitutionUndetInvestigation) {
        this.totalInstitutionUndetInvestigation = totalInstitutionUndetInvestigation;
    }

}
