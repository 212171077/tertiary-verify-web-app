package com.pc.model;

import java.io.Serializable;
import java.util.ArrayList;

public class BogusCourses implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long institutionID;
    private ArrayList<Long> courseList;
    private String reporterName;
    private String reporterSurname;
    private String reporterPhone;
    private String reporterEmail;

    public Long getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(Long institutionID) {
        this.institutionID = institutionID;
    }

    public ArrayList<Long> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<Long> courseList) {
        this.courseList = courseList;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterSurname() {
        return reporterSurname;
    }

    public void setReporterSurname(String reporterSurname) {
        this.reporterSurname = reporterSurname;
    }

    public String getReporterPhone() {
        return reporterPhone;
    }

    public void setReporterPhone(String reporterPhone) {
        this.reporterPhone = reporterPhone;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }

    @Override
    public String toString() {
        return "BogusCourses{" +
                "institutionID=" + institutionID +
                ", courseList=" + courseList +
                ", reporterName='" + reporterName + '\'' +
                ", reporterSurname='" + reporterSurname + '\'' +
                ", reporterPhone='" + reporterPhone + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                '}';
    }
}
