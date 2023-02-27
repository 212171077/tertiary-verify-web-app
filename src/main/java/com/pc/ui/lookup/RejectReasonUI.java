package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.lookup.RejectReason;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.RejectReasonService;
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

@Component("rejectReasonUI")
@ViewScoped
public class RejectReasonUI extends AbstractUI {

    @Autowired
    RejectReasonService rejectReasonService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private List<RejectReason> rejectReasonList;
    private RejectReason rejectReason;
    private LazyDataModel<RejectReason> dataModel;

    @PostConstruct
    public void init() {
        rejectReason = new RejectReason();
        loadRejectReasonInfo();
    }

    public void prepareRejectReasonList() {
        try {
            rejectReasonList = rejectReasonService.findAllRejectReason();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRejectReason() {
        try {
            rejectReasonService.saveRejectReason(rejectReason);
            displayInfoMssg("Reject reason added successful...!!");
            loadRejectReasonInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteRejectReason() {
        try {
            rejectReasonService.deleteRejectReason(rejectReason);
            displayWarningMssg("Reject reason deleted successful...!!");
            loadRejectReasonInfo();
            reset();
        } catch (Exception e) {
            reset();
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<RejectReason> findAllRejectReason() {
        List<RejectReason> list = new ArrayList<>();
        try {
            list = rejectReasonService.findAllRejectReason();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public void reset() {
        rejectReason = new RejectReason();
    }

    public void loadRejectReasonInfo() {
        dataModel = new LazyDataModel<RejectReason>() {

            private static final long serialVersionUID = 1L;
            private List<RejectReason> list = new ArrayList<RejectReason>();

            @Override
            public List<RejectReason> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<RejectReason>) entityDAOFacade.getResultList(RejectReason.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, RejectReason.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(RejectReason obj) {
                return obj.getId();
            }

            @Override
            public RejectReason getRowData(String rowKey) {
                for (RejectReason obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }


    public RejectReason getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(RejectReason rejectReason) {
        this.rejectReason = rejectReason;
    }

    public LazyDataModel<RejectReason> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<RejectReason> dataModel) {
        this.dataModel = dataModel;
    }

    public List<RejectReason> getRejectReasonList() {
        return rejectReasonList;
    }

    public void setRejectReasonList(List<RejectReason> rejectReasonList) {
        this.rejectReasonList = rejectReasonList;
    }
}
