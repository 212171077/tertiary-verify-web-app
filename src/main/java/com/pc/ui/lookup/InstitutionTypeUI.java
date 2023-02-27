package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.lookup.InstitutionType;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.InstitutionTypeService;
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

@Component("institutionTypeUI")
@ViewScoped
public class InstitutionTypeUI extends AbstractUI {

    @Autowired
    InstitutionTypeService institutionTypeService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<InstitutionType> institutionTypeList;
    private InstitutionType institutionType;
    private LazyDataModel<InstitutionType> dataModel;

    @PostConstruct
    public void init() {

        try {
            super.prepareCurrentUser();
            institutionType = new InstitutionType();
            loadInstitutionTypeInfo();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveInstitutionType() {
        try {
            institutionTypeService.saveInstitutionType(institutionType);
            displayInfoMssg("Institution type added successful...!!");
            institutionTypeList = (ArrayList<InstitutionType>) findAllInstitutionType();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteInstitutionType() {
        try {
            institutionTypeService.deleteInstitutionType(institutionType);
            displayWarningMssg("Institution type deleted successful...!!");
            institutionTypeList = (ArrayList<InstitutionType>) findAllInstitutionType();
            reset();
        } catch (Exception e) {
            reset();
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<InstitutionType> findAllInstitutionType() {
        List<InstitutionType> list = new ArrayList<>();
        try {
            list = institutionTypeService.findAllInstitutionType();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public void reset() {
        institutionType = new InstitutionType();
    }


    public InstitutionType getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(InstitutionType institutionType) {
        this.institutionType = institutionType;
    }

    public ArrayList<InstitutionType> getInstitutionTypeList() {
        return institutionTypeList;
    }

    public void setInstitutionTypeList(ArrayList<InstitutionType> institutionTypeList) {
        this.institutionTypeList = institutionTypeList;
    }

    public void loadInstitutionTypeInfo() {
        dataModel = new LazyDataModel<InstitutionType>() {

            private static final long serialVersionUID = 1L;
            private List<InstitutionType> list = new ArrayList<InstitutionType>();

            @Override
            public List<InstitutionType> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<InstitutionType>) entityDAOFacade.getResultList(InstitutionType.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, InstitutionType.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(InstitutionType obj) {
                return obj.getId();
            }

            @Override
            public InstitutionType getRowData(String rowKey) {
                for (InstitutionType obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public LazyDataModel<InstitutionType> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<InstitutionType> dataModel) {
        this.dataModel = dataModel;
    }


}
