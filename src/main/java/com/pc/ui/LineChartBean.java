package com.pc.ui;

import com.pc.model.Births;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ManagedBean
@RequestScoped
public class LineChartBean {

    protected List<Births> boys;
    protected List<Births> girls;

    public LineChartBean() {
        reload();
    }

    public List<Births> getBoys() {
        return boys;
    }

    public List<Births> getGirls() {
        return girls;
    }

    private void reload() {
        boys = new ArrayList<>();
        girls = new ArrayList<>();
        Random r = new Random();
        for (int i = 2000; i < 2010; i++) {
            boys.add(new Births(Integer.toString(i), r.nextInt(500) + 800));
            girls.add(new Births(Integer.toString(i), r.nextInt(500) + 800));
        }
    }
}



