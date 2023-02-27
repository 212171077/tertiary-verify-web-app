package com.pc.model;

import java.io.Serializable;

public class BogusInstitution implements Serializable {
    private static final long serialVersionUID = 1L;
    private String instName;
    private String knownAs;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String province;
    private String suburbArea;
    private String postalCode;
    private String reporterName;
    private String reporterSurname;
    private String reporterPhone;
    private String reporterEmail;

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getKnownAs() {
        return knownAs;
    }

    public void setKnownAs(String knownAs) {
        this.knownAs = knownAs;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSuburbArea() {
        return suburbArea;
    }

    public void setSuburbArea(String suburbArea) {
        this.suburbArea = suburbArea;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
        return "BogusInstitution{" +
                "instName='" + instName + '\'' +
                ", knownAs='" + knownAs + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", addressLine3='" + addressLine3 + '\'' +
                ", province='" + province + '\'' +
                ", suburbArea='" + suburbArea + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", reporterName='" + reporterName + '\'' +
                ", reporterSurname='" + reporterSurname + '\'' +
                ", reporterPhone='" + reporterPhone + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                '}';
    }
}
