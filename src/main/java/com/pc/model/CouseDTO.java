package com.pc.model;

public class CouseDTO {
    private Long id;
    private String description;
    private String courseType;
    private String courseLevel;
    private String institution;

    public CouseDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CouseDTO(Long id, String description, String courseType, String courseLevel, String institution) {
        super();
        this.description = description;
        this.courseType = courseType;
        this.courseLevel = courseLevel;
        this.id = id;
        this.institution = institution;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @Override
    public String toString() {
        return "CouseDTO [id=" + id + ", description=" + description + ", courseType=" + courseType + ", courseLevel="
                + courseLevel + ", institution=" + institution + "]";
    }


}
