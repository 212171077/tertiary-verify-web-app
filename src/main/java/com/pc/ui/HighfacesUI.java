package com.pc.ui;

import com.pc.beans.InstitutionCourseBean;
import com.pc.beans.ReportedInstitutionBean;
import com.pc.constants.AppConstants;
import com.pc.entities.lookup.Province;
import com.pc.framework.AbstractUI;
import com.pc.model.Births;
import com.pc.service.InstitutionCourseService;
import com.pc.service.ReportedInstitutionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.*;

@Component("highfacesUI")
@ViewScoped
public class HighfacesUI extends AbstractUI {

    @Autowired
    ReportedInstitutionsService reportedInstitutionsService;
    @Autowired
    InstitutionCourseService institutionCourseService;
    private List<ReportedInstitutionBean> reportedIns = new ArrayList<ReportedInstitutionBean>();
    private List<InstitutionCourseBean> institutionCourseList;
    private Map<String, List<Births>> mappedList;
    private ArrayList<Births> boys = new ArrayList<>();
    private ArrayList<Births> girls = new ArrayList<>();
    private Province province;
    private Integer mon;
    private Integer year;

    private ArrayList<Integer> yearList = new ArrayList<>();
    private ArrayList<Integer> monthList = new ArrayList<>();


    @PostConstruct
    public void init() {
        reloadInstitution();
        loadInstitutionCourse();
        loadUserTaskPerformance();
        populateYearsAndMon();
    }

    public void populateYearsAndMon() {
        for (int x = 1; x <= 12; x++) {
            monthList.add(x);
        }
        int currYear = Integer.parseInt(AppConstants.sdfYYYY.format(new Date()));
        year = currYear;
        currYear++;
        for (int x = 2017; x <= currYear; x++) {
            yearList.add(x);
        }

    }

    public void clearSearchCriteria() {
        int currYear = Integer.parseInt(AppConstants.sdfYYYY.format(new Date()));
        year = currYear;
        mon = null;
        province = null;
        reloadInstitution();
    }

    public void reloadInstitution() {
        try {
            reportedIns = reportedInstitutionsService.getReportedInstitutionBean(province, year, mon);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

    }

    private void loadUserTaskPerformance() {

        mappedList = new HashMap<>();
        Random r = new Random();
        for (int i = 2000; i < 2010; i++) {
            boys.add(new Births(Integer.toString(i), r.nextInt(500) + 800));
            girls.add(new Births(Integer.toString(i), r.nextInt(500) + 800));
        }
        mappedList.put("boys", boys);
        mappedList.put("girls", girls);
    }

    private void loadInstitutionCourse() {
        try {
            institutionCourseList = institutionCourseService.getInstitutionCourseBean();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

    }

    public List<ReportedInstitutionBean> getReportedIns() {
        return reportedIns;
    }

    public void setReportedIns(List<ReportedInstitutionBean> reportedIns) {
        this.reportedIns = reportedIns;
    }

    public List<InstitutionCourseBean> getInstitutionCourseList() {
        return institutionCourseList;
    }

    public void setInstitutionCourseList(List<InstitutionCourseBean> institutionCourseList) {
        this.institutionCourseList = institutionCourseList;
    }

    public Map<String, List<Births>> getMappedList() {
        return mappedList;
    }

    public void setMappedList(Map<String, List<Births>> mappedList) {
        this.mappedList = mappedList;
    }

    public ArrayList<Births> getBoys() {
        return boys;
    }

    public void setBoys(ArrayList<Births> boys) {
        this.boys = boys;
    }

    public ArrayList<Births> getGirls() {
        return girls;
    }

    public void setGirls(ArrayList<Births> girls) {
        this.girls = girls;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Integer getMon() {
        return mon;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public ArrayList<Integer> getYearList() {
        return yearList;
    }

    public void setYearList(ArrayList<Integer> yearList) {
        this.yearList = yearList;
    }

    public ArrayList<Integer> getMonthList() {
        return monthList;
    }

    public void setMonthList(ArrayList<Integer> monthList) {
        this.monthList = monthList;
    }


}