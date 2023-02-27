package com.pc.model;

public class InstitutionIDDTO {
    private Long id;
    private String errorMessage;

    public InstitutionIDDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public InstitutionIDDTO(Long id, String errorMessage) {
        super();
        this.id = id;
        this.errorMessage = errorMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
