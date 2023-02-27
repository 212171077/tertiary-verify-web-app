package com.pc.model;

public class ReportedInstitutionDTO {

    private String institutionName;
    private String reportType;
    private String repordedDate;
    private String refNumber;
    private String investigatorFeedback;
    private String status;
    private String reporterFullName;
    private String lastUpdatedDate;
    private String errorMessage;

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getRepordedDate() {
        return repordedDate;
    }

    public void setRepordedDate(String repordedDate) {
        this.repordedDate = repordedDate;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getInvestigatorFeedback() {
        return investigatorFeedback;
    }

    public void setInvestigatorFeedback(String investigatorFeedback) {
        if (investigatorFeedback == null) {
            investigatorFeedback = "No feedback available";
        }
        this.investigatorFeedback = investigatorFeedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReporterFullName() {
        return reporterFullName;
    }

    public void setReporterFullName(String reporterFullName) {
        this.reporterFullName = reporterFullName;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ReportedInstitutionDTO [institutionName=" + institutionName + ", reportType=" + reportType
                + ", repordedDate=" + repordedDate + ", refNumber=" + refNumber + ", investigatorFeedback="
                + investigatorFeedback + ", status=" + status + ", reporterFullName=" + reporterFullName
                + ", lastUpdatedDate=" + lastUpdatedDate + ", errorMessage=" + errorMessage + "]";
    }


}
