package com.pc.ui;

import com.pc.constants.AppConstants;
import com.pc.framework.AbstractUI;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

@Component("commonUI")
@ViewScoped
public class CommonUI extends AbstractUI {

    /**
     * The  telephone format.
     */
    public String CELLPHONE_FORMAT = "099 999 9999";
    /***/
    public String PASSWORD_FORMAT = "[a-zA-Z]{1}\\d{8}";
    /**
     * The maximum RSA ID number
     */
    public Long MAX_RSA_ID_NUMBER = Long.parseLong("13");
    /**
     * The  telephone format.
     */
    public String STUDENT_NUMBER_FORMAT = "299 999 999";
    /**
     * The  telephone format.
     */
    public String ID_NUMBER_FORMAT = "9999999999999";
    public String DATE_FORMAT = "MM/dd/yyyy";
    /**
     * The  name format.
     */
    public String NAME_FORMAT = "^[a-zA-Z_ ]*$";
    /**
     * Email Format
     */
    public String EMAIL_FORMAT = "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]";
    public String CODE_FORMAT = "9999";
    private String path;
    private AppConstants appConstants = new AppConstants();
    /**
     * Template theme
     * Available themes:
     * skin-blue
     * skin-black
     * skin-purple
     * skin-green
     * skin-red
     * skin-yellow
     * skin-blue-light
     * skin-black-light
     * skin-purple-light
     * skin-green-light
     * skin-red-light
     * skin-yellow-light
     */
    private String theme;
    private String primaryColour;

    @PostConstruct
    public void init() {
        path = appConstants.realpath;

        theme = "skin-purple";
        primaryColour = "gray";
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPrimaryColour() {
        return primaryColour;
    }

    public void setPrimaryColour(String primaryColour) {
        this.primaryColour = primaryColour;
    }

    public String getCELLPHONE_FORMAT() {
        return CELLPHONE_FORMAT;
    }

    public void setCELLPHONE_FORMAT(String cELLPHONE_FORMAT) {
        CELLPHONE_FORMAT = cELLPHONE_FORMAT;
    }

    public String getPASSWORD_FORMAT() {
        return PASSWORD_FORMAT;
    }

    public void setPASSWORD_FORMAT(String pASSWORD_FORMAT) {
        PASSWORD_FORMAT = pASSWORD_FORMAT;
    }

    public Long getMAX_RSA_ID_NUMBER() {
        return MAX_RSA_ID_NUMBER;
    }

    public void setMAX_RSA_ID_NUMBER(Long mAX_RSA_ID_NUMBER) {
        MAX_RSA_ID_NUMBER = mAX_RSA_ID_NUMBER;
    }

    public String getSTUDENT_NUMBER_FORMAT() {
        return STUDENT_NUMBER_FORMAT;
    }

    public void setSTUDENT_NUMBER_FORMAT(String sTUDENT_NUMBER_FORMAT) {
        STUDENT_NUMBER_FORMAT = sTUDENT_NUMBER_FORMAT;
    }

    public String getID_NUMBER_FORMAT() {
        return ID_NUMBER_FORMAT;
    }

    public void setID_NUMBER_FORMAT(String iD_NUMBER_FORMAT) {
        ID_NUMBER_FORMAT = iD_NUMBER_FORMAT;
    }

    public String getNAME_FORMAT() {
        return NAME_FORMAT;
    }

    public void setNAME_FORMAT(String nAME_FORMAT) {
        NAME_FORMAT = nAME_FORMAT;
    }

    public String getEMAIL_FORMAT() {
        return EMAIL_FORMAT;
    }

    public void setEMAIL_FORMAT(String eMAIL_FORMAT) {
        EMAIL_FORMAT = eMAIL_FORMAT;
    }

    public String getCODE_FORMAT() {
        return CODE_FORMAT;
    }

    public void setCODE_FORMAT(String cODE_FORMAT) {
        CODE_FORMAT = cODE_FORMAT;
    }

    public String getDATE_FORMAT() {
        return DATE_FORMAT;
    }

    public void setDATE_FORMAT(String DATE_FORMAT) {
        this.DATE_FORMAT = DATE_FORMAT;
    }
}
