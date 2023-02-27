package com.pc.ui;

import com.pc.framework.AbstractUI;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Component
@ManagedBean(name = "guestPreferences")
@ViewScoped
public class GuestPreferences extends AbstractUI {

    private Boolean darkMenu;
    private Boolean overlayMenu;
    private Boolean orientationRTL;
    private String theme;
    private String layout;
    private String primaryColor;

    @PostConstruct
    public void init() {
        preparPref();
    }

    public void preparPref() {
        darkMenu = true;
        overlayMenu = false;
        orientationRTL = false;
        theme = "tertiary-verify";
        layout = "tertiary-verify";
        primaryColor = "#b0976e";//Blue
    }

    public Boolean getDarkMenu() {
        return darkMenu;
    }

    public void setDarkMenu(Boolean darkMenu) {
        this.darkMenu = darkMenu;
    }

    public Boolean getOverlayMenu() {
        return overlayMenu;
    }

    public void setOverlayMenu(Boolean overlayMenu) {
        this.overlayMenu = overlayMenu;
    }

    public Boolean getOrientationRTL() {
        return orientationRTL;
    }

    public void setOrientationRTL(Boolean orientationRTL) {
        this.orientationRTL = orientationRTL;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }


}
