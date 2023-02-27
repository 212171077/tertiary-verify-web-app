package com.pc.model;

public class InstitituionDetailsDTO {
    private String name;
    private String knownAs;
    private String contactNumber;
    private String alternativeContactNumber;
    private String email;
    private String accreditationNumber;
    private String addressLine1;
    private String addressLine2;
    private String province;
    private String errorMessage;
    private String startDate;
    private String endDate;

    public InstitituionDetailsDTO() {
        super();
        // TODO Auto-generated constructor stub
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKnownAs() {
        return knownAs;
    }

    public void setKnownAs(String knownAs) {
        this.knownAs = knownAs;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAlternativeContactNumber() {
        return alternativeContactNumber;
    }

    public void setAlternativeContactNumber(String alternativeContactNumber) {
        this.alternativeContactNumber = alternativeContactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccreditationNumber() {
        return accreditationNumber;
    }

    public void setAccreditationNumber(String accreditationNumber) {
        this.accreditationNumber = accreditationNumber;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public String getStartDate() {
        return startDate;
    }


    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getEndDate() {
        return endDate;
    }


    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    @Override
    public String toString() {
        return "InstitituionDetailsDTO [name=" + name + ", knownAs=" + knownAs + ", contactNumber=" + contactNumber
                + ", alternativeContactNumber=" + alternativeContactNumber + ", email=" + email
                + ", accreditationNumber=" + accreditationNumber + ", addressLine1=" + addressLine1 + ", addressLine2="
                + addressLine2 + ", province=" + province + ", errorMessage=" + errorMessage + ", startDate="
                + startDate + ", endDate=" + endDate + "]";
    }


}
