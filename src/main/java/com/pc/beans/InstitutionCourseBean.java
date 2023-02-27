package com.pc.beans;

public class InstitutionCourseBean {

    public String label;
    public Long total;

    public InstitutionCourseBean() {
        super();
        // TODO Auto-generated constructor stub
    }


    public InstitutionCourseBean(String label, Long total) {
        super();
        this.label = label;
        this.total = total;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public Long getTotal() {
        return total;
    }


    public void setTotal(Long total) {
        this.total = total;
    }


}