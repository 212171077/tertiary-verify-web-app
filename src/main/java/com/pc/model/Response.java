package com.pc.model;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String message;
    private Boolean success;

    public Response() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Response(Long id, String message) {
        super();
        this.id = id;
        this.message = message;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
