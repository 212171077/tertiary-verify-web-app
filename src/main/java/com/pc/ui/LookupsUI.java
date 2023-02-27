package com.pc.ui;

import com.pc.beans.LookupMenuBeans;
import com.pc.framework.AbstractUI;
import com.pc.service.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.List;

@Component("lookupsUI")
@ViewScoped
public class LookupsUI extends AbstractUI {

    List<LookupMenuBeans> lookupMenuBeansList;

    @Autowired
    LookupService lookupService;

    @PostConstruct
    public void init() {
        try {
            lookupMenuBeansList = lookupService.getlookupsMenuBean();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            displayErrorMssg(e.getMessage());
        }
    }

    public List<LookupMenuBeans> getLookupMenuBeansList() {
        return lookupMenuBeansList;
    }

    public void setLookupMenuBeansList(List<LookupMenuBeans> lookupMenuBeansList) {
        this.lookupMenuBeansList = lookupMenuBeansList;
    }


}
