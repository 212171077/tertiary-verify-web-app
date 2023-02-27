package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.lookup.Province;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.ProvinceService;
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

@Component("provinceUI")
@ViewScoped
public class ProvinceUI extends AbstractUI {

    @Autowired
    ProvinceService provinceService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<Province> provinceList;
    private Province province;
    private LazyDataModel<Province> dataModel;

    @PostConstruct
    public void init() {

        try {
            super.prepareCurrentUser();
            province = new Province();
            loadProvinceInfo();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveProvince() {
        try {
            provinceService.saveProvince(province);
            displayInfoMssg("Province added successful...!!");
            provinceList = (ArrayList<Province>) findAllProvince();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteProvince() {
        try {
            provinceService.deleteProvince(province);
            displayWarningMssg("Province deleted successful...!!");
            provinceList = (ArrayList<Province>) findAllProvince();
            reset();
        } catch (Exception e) {
            reset();
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Province> findAllProvince() {
        List<Province> list = new ArrayList<>();
        try {
            list = provinceService.findAllProvince();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }


    public void reset() {
        province = new Province();
    }


    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public ArrayList<Province> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(ArrayList<Province> provinceList) {
        this.provinceList = provinceList;
    }

    public void loadProvinceInfo() {
        dataModel = new LazyDataModel<Province>() {

            private static final long serialVersionUID = 1L;
            private List<Province> list = new ArrayList<Province>();

            @Override
            public List<Province> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<Province>) entityDAOFacade.getResultList(Province.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, Province.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(Province obj) {
                return obj.getId();
            }

            @Override
            public Province getRowData(String rowKey) {
                for (Province obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public LazyDataModel<Province> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<Province> dataModel) {
        this.dataModel = dataModel;
    }
}
