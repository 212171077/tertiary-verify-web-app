package com.pc.model;

public class AppInstitution {

    private Long id;
    private String intitutionType;
    private String name;
    private String knownAs;
    private String contactNumber;
    private String alternativeContactNumber;
    private String email;
    private String startDate;
    private String endDate;
    private String province;
    private String addessLine1;
    private String addessLine2;
    private String addessLine3;
    private String addessLine4;
    private String accreditationNumber;
    /**
     * Course Details
     */
    private String courseDesc;
    private String courseType;
    private String courseLevel;

    public AppInstitution() {
    }

    public AppInstitution(Long id, String intitutionType, String name, String knownAs, String contactNumber,
                          String alternativeContactNumber, String email, String startDate, String endDate, String province,
                          String addessLine1, String addessLine2, String addessLine3, String addessLine4, String accreditationNumber) {
        super();
        this.id = id;
        this.intitutionType = intitutionType;
        this.name = name;
        this.knownAs = knownAs;
        this.contactNumber = contactNumber;
        this.alternativeContactNumber = alternativeContactNumber;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.province = province;
        this.addessLine1 = addessLine1;
        this.addessLine2 = addessLine2;
        this.addessLine3 = addessLine3;
        this.addessLine4 = addessLine4;
        this.accreditationNumber = accreditationNumber;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddessLine1() {
        return addessLine1;
    }

    public void setAddessLine1(String addessLine1) {
        this.addessLine1 = addessLine1;
    }

    public String getAddessLine2() {
        return addessLine2;
    }

    public void setAddessLine2(String addessLine2) {
        this.addessLine2 = addessLine2;
    }

    public String getAddessLine3() {
        return addessLine3;
    }

    public void setAddessLine3(String addessLine3) {
        this.addessLine3 = addessLine3;
    }

    public String getAddessLine4() {
        return addessLine4;
    }

    public void setAddessLine4(String addessLine4) {
        this.addessLine4 = addessLine4;
    }


    public String getIntitutionType() {
        return intitutionType;
    }


    public void setIntitutionType(String intitutionType) {
        this.intitutionType = intitutionType;
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

    public String getAccreditationNumber() {
        return accreditationNumber;
    }

    public void setAccreditationNumber(String accreditationNumber) {
        this.accreditationNumber = accreditationNumber;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

}
