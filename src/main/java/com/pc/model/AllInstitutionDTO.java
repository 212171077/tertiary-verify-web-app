package com.pc.model;

import java.util.List;

public class AllInstitutionDTO {
    private Long id;
    private String institutionName;
    private List<String> province;


    public AllInstitutionDTO() {
    }


    public AllInstitutionDTO(Long id, String institutionName) {
        this.id = id;
        this.institutionName = institutionName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public List<String> getProvince() {
        return province;
    }

    public void setProvince(List<String> province) {
        this.province = province;
    }
}
