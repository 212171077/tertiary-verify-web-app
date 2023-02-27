package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.lookup.Gender;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.GenderService;
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

@Component("genderUI")
@ViewScoped
public class GenderUI extends AbstractUI {

    @Autowired
    GenderService service;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private Gender gender;
    private ArrayList<Gender> genderList = new ArrayList<>();
    private LazyDataModel<Gender> dataModel;

    @PostConstruct
    public void init() {

        try {
            super.prepareCurrentUser();
            gender = new Gender();
            loadGenderInfo();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveGender() {
        try {

            service.saveGender(gender);
            displaySuccessMssg("Gender added successful...!!");
            reset();
            genderList = (ArrayList<Gender>) findAllGender();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteGender() {
        try {
            service.deleteGender(gender);
            reset();
            genderList = (ArrayList<Gender>) findAllGender();
            displayWarningMssg("Gender deleted successful...!!");
        } catch (Exception e) {
            reset();
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Gender> findAllGender() {
        List<Gender> list = new ArrayList<>();
        try {
            list = service.findAllGender();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public void reset() {
        gender = new Gender();
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ArrayList<Gender> getGenderList() {
        return genderList;
    }

    public void setGenderList(ArrayList<Gender> genderList) {
        this.genderList = genderList;
    }

    public void loadGenderInfo() {
        dataModel = new LazyDataModel<Gender>() {

            private static final long serialVersionUID = 1L;
            private List<Gender> list = new ArrayList<Gender>();

            @Override
            public List<Gender> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<Gender>) entityDAOFacade.getResultList(Gender.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, Gender.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(Gender obj) {
                return obj.getId();
            }

            @Override
            public Gender getRowData(String rowKey) {
                for (Gender obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public LazyDataModel<Gender> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<Gender> dataModel) {
        this.dataModel = dataModel;
    }

}
