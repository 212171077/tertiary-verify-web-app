package com.pc.model;

import java.util.List;

public class BogusCourseDetails {
    private BogusCourses bogusCourses;
    private List<CouseDTO> courseList;

    public BogusCourses getBogusCourses() {
        return bogusCourses;
    }

    public void setBogusCourses(BogusCourses bogusCourses) {
        this.bogusCourses = bogusCourses;
    }

    public List<CouseDTO> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CouseDTO> courseList) {
        this.courseList = courseList;
    }

    @Override
    public String toString() {
        return "BogusCourseDetails{" +
                "bogusCourses=" + bogusCourses +
                ", courseList=" + courseList +
                '}';
    }
}
