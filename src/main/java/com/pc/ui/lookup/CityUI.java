package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.lookup.City;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.CityService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("cityUI")
@ViewScoped
public class CityUI extends AbstractUI {

    @Autowired
    CityService cityService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<City> cityList;
    private City city;
    private LazyDataModel<City> dataModel;

    @PostConstruct
    public void init() {
        try {
            super.prepareCurrentUser();
            city = new City();
            //cityList=(ArrayList<City>) findAllCity();
            loadCityInfo();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadCityInfo() {
        dataModel = new LazyDataModel<City>() {

            private static final long serialVersionUID = 1L;
            private List<City> list = new ArrayList<City>();

            @Override
            public List<City> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<City>) entityDAOFacade.getResultList(City.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, City.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(City obj) {
                return obj.getId();
            }

            @Override
            public City getRowData(String rowKey) {
                for (City obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public void saveCity() {
        try {
            cityService.saveCity(city);
            displayInfoMssg("City added successful...!!");
            cityList = (ArrayList<City>) findAllCity();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteCity() {
        try {
            cityService.deleteCity(city);
            displayWarningMssg("City added successful...!!");
            cityList = (ArrayList<City>) findAllCity();
            reset();
        } catch (Exception e) {
            reset();
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<City> findAllCity() {
        List<City> list = new ArrayList<>();
        try {
            list = cityService.findAllCity();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public void reset() {
        city = new City();
    }


    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ArrayList<City> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<City> cityList) {
        this.cityList = cityList;
    }

    public LazyDataModel<City> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<City> dataModel) {
        this.dataModel = dataModel;
    }

}
