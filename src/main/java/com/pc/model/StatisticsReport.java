package com.pc.model;

import java.io.Serializable;

public class StatisticsReport implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long totalCourse;
    private Long totalInstitution;
    private Long totalInstitutionReported;
    private Long totalInstitutionUndetInvestigation;
    private String errorMessage;

    public StatisticsReport() {
        super();
        // TODO Auto-generated constructor stub
    }

    public StatisticsReport(Long totalCourse, Long totalInstitution, Long totalInstitutionReported,
                            Long totalInstitutionUndetInvestigation) {
        super();
        this.totalCourse = totalCourse;
        this.totalInstitution = totalInstitution;
        this.totalInstitutionReported = totalInstitutionReported;
        this.totalInstitutionUndetInvestigation = totalInstitutionUndetInvestigation;
    }

    public Long getTotalCourse() {
        return totalCourse;
    }

    public void setTotalCourse(Long totalCourse) {
        this.totalCourse = totalCourse;
    }

    public Long getTotalInstitution() {
        return totalInstitution;
    }

    public void setTotalInstitution(Long totalInstitution) {
        this.totalInstitution = totalInstitution;
    }

    public Long getTotalInstitutionReported() {
        return totalInstitutionReported;
    }

    public void setTotalInstitutionReported(Long totalInstitutionReported) {
        this.totalInstitutionReported = totalInstitutionReported;
    }

    public Long getTotalInstitutionUndetInvestigation() {
        return totalInstitutionUndetInvestigation;
    }

    public void setTotalInstitutionUndetInvestigation(Long totalInstitutionUndetInvestigation) {
        this.totalInstitutionUndetInvestigation = totalInstitutionUndetInvestigation;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
