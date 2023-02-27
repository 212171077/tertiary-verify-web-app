package com.pc.model;

import java.util.List;

public class InstitutionListDTO {
    private List<InstitutionDTO> institutions;
    private String errorMessage;

    public List<InstitutionDTO> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<InstitutionDTO> institutions) {
        this.institutions = institutions;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
