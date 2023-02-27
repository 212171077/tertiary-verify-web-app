package com.pc.beans;

public class ReportedInstitutionBean {

    public String label;
    public Long total;

    public ReportedInstitutionBean() {
        super();
        // TODO Auto-generated constructor stub
    }


    public ReportedInstitutionBean(String label, Long total) {
        super();
        if (label == null) {
            label = "No Label";
        }
        if (total == null) {
            total = 0L;
        }
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