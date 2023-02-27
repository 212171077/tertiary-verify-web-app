package com.pc.beans;

public class CourseBean {

    private String description;
    private String courseType;
    private String courseLevel;


    public CourseBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CourseBean(String description, String courseType, String courseLevel) {
        super();
        this.description = description;
        this.courseType = courseType;
        this.courseLevel = courseLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
