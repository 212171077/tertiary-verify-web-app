package com.pc.model;

public class CourseDetailDTO {

    private String description;
    private String courseType;
    private String courseLevel;
    private String errorMessage;


    public CourseDetailDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CourseDetailDTO(String description, String courseType, String courseLevel) {
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "CourseDetailDTO [description=" + description + ", courseType=" + courseType + ", courseLevel="
                + courseLevel + ", errorMessage=" + errorMessage + "]";
    }


}
