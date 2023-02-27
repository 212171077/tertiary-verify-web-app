package com.pc.model;

import java.util.List;

public class CouseListDTO {
    private List<CouseDTO> couses;
    private String errorMessage;

    public CouseListDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CouseListDTO(List<CouseDTO> couses, String errorMessage) {
        super();
        this.couses = couses;
        this.errorMessage = errorMessage;
    }

    public List<CouseDTO> getCouses() {
        return couses;
    }

    public void setCouses(List<CouseDTO> couses) {
        this.couses = couses;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
