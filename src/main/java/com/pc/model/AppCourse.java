package com.pc.model;

public class AppCourse {

    private Long id;
    private String courseName;
    private Long institutionId;
    private String courseType;
    private String courseLevel;

    public AppCourse() {
    }

    public AppCourse(Long id, String courseName, Long institutionId, String courseType, String courseLevel) {
        super();
        this.id = id;
        this.courseName = courseName;
        this.institutionId = institutionId;
        this.courseType = courseType;
        this.courseLevel = courseLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
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
