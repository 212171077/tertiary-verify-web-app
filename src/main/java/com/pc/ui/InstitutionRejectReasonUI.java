package com.pc.ui;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.InstitutionRejectReason;
import com.pc.framework.AbstractUI;
import com.pc.service.InstitutionRejectReasonService;
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

@Component("institutionRejectReasonUI")
@ViewScoped
public class InstitutionRejectReasonUI extends AbstractUI {

    @Autowired
    InstitutionRejectReasonService institutionRejectReasonService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<InstitutionRejectReason> institutionRejectReasonList;
    private InstitutionRejectReason institutionRejectReason;
    private LazyDataModel<InstitutionRejectReason> dataModel;

    @PostConstruct
    public void init() {
        institutionRejectReason = new InstitutionRejectReason();
        loadInstitutionRejectReasonInfo();
    }

    public void saveInstitutionRejectReason() {
        try {
            institutionRejectReasonService.saveInstitutionRejectReason(institutionRejectReason);
            displayInfoMssg("Institution reject reason added successful...!!");
            loadInstitutionRejectReasonInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteInstitutionRejectReason() {
        try {
            institutionRejectReasonService.deleteInstitutionRejectReason(institutionRejectReason);
            displayWarningMssg("Institution reject reason deleted Successful...!!");
            loadInstitutionRejectReasonInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<InstitutionRejectReason> findAllInstitutionRejectReason() {
        List<InstitutionRejectReason> list = new ArrayList<>();
        try {
            list = institutionRejectReasonService.findAllInstitutionRejectReason();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<InstitutionRejectReason> findAllInstitutionRejectReasonPageable() {
        Pageable p = null;
        try {
            return institutionRejectReasonService.findAllInstitutionRejectReason(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<InstitutionRejectReason> findAllInstitutionRejectReasonSort() {
        Sort s = null;
        List<InstitutionRejectReason> list = new ArrayList<>();
        try {
            list = institutionRejectReasonService.findAllInstitutionRejectReason(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        institutionRejectReason = new InstitutionRejectReason();
    }

    public void loadInstitutionRejectReasonInfo() {
        dataModel = new LazyDataModel<InstitutionRejectReason>() {

            private static final long serialVersionUID = 1L;
            private List<InstitutionRejectReason> list = new ArrayList<InstitutionRejectReason>();

            @Override
            public List<InstitutionRejectReason> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<InstitutionRejectReason>) entityDAOFacade.getResultList(InstitutionRejectReason.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, InstitutionRejectReason.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(InstitutionRejectReason obj) {
                return obj.getId();
            }

            @Override
            public InstitutionRejectReason getRowData(String rowKey) {
                for (InstitutionRejectReason obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public InstitutionRejectReason getInstitutionRejectReason() {
        return institutionRejectReason;
    }

    public void setInstitutionRejectReason(InstitutionRejectReason institutionRejectReason) {
        this.institutionRejectReason = institutionRejectReason;
    }

    public ArrayList<InstitutionRejectReason> getInstitutionRejectReasonList() {
        return institutionRejectReasonList;
    }

    public void setInstitutionRejectReasonList(ArrayList<InstitutionRejectReason> institutionRejectReasonList) {
        this.institutionRejectReasonList = institutionRejectReasonList;
    }

    public LazyDataModel<InstitutionRejectReason> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<InstitutionRejectReason> dataModel) {
        this.dataModel = dataModel;
    }


}
