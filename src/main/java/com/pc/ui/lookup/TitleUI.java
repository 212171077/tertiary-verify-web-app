package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.lookup.Title;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.TitleService;
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

@Component("titleUI")
@ViewScoped
public class TitleUI extends AbstractUI {

    @Autowired
    TitleService titleService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<Title> titleList;
    private Title title;
    private LazyDataModel<Title> dataModel;

    @PostConstruct
    public void init() {

        try {
            super.prepareCurrentUser();
            title = new Title();
            loadTitleInfo();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveTitle() {
        try {
            titleService.saveTitle(title);
            displayInfoMssg("Title added successful...!!");
            titleList = (ArrayList<Title>) findAllTitle();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteTitle() {
        try {
            titleService.deleteTitle(title);
            displayWarningMssg("Title deleted successful...!!");
            titleList = (ArrayList<Title>) findAllTitle();
            reset();
        } catch (Exception e) {
            reset();
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Title> findAllTitle() {
        List<Title> list = new ArrayList<>();
        try {
            list = titleService.findAllTitle();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public void reset() {
        title = new Title();
    }


    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public ArrayList<Title> getTitleList() {
        return titleList;
    }

    public void setTitleList(ArrayList<Title> titleList) {
        this.titleList = titleList;
    }

    public void loadTitleInfo() {
        dataModel = new LazyDataModel<Title>() {

            private static final long serialVersionUID = 1L;
            private List<Title> list = new ArrayList<Title>();

            @Override
            public List<Title> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<Title>) entityDAOFacade.getResultList(Title.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, Title.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(Title obj) {
                return obj.getId();
            }

            @Override
            public Title getRowData(String rowKey) {
                for (Title obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public LazyDataModel<Title> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<Title> dataModel) {
        this.dataModel = dataModel;
    }
}
