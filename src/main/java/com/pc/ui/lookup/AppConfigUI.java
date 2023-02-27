package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.lookup.AppConfig;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.AppConfigService;
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

@Component("appConfigUI")
@ViewScoped
public class AppConfigUI extends AbstractUI {

    @Autowired
    AppConfigService appConfigService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<AppConfig> appConfigList;
    private AppConfig appConfig;
    private LazyDataModel<AppConfig> dataModel;

    @PostConstruct
    public void init() {
        appConfig = new AppConfig();
        loadAppConfigInfo();
    }

    public void saveAppConfig() {
        try {
            appConfigService.saveAppConfig(appConfig);
            displayInfoMssg("App config added successful...!!");
            loadAppConfigInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteAppConfig() {
        try {
            appConfigService.deleteAppConfig(appConfig);
            displayWarningMssg("App config deleted successful...!!");
            loadAppConfigInfo();
            reset();
        } catch (Exception e) {
            reset();
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<AppConfig> findAllAppConfig() {
        List<AppConfig> list = new ArrayList<>();
        try {
            list = appConfigService.findAllAppConfig();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }


    public void reset() {
        appConfig = new AppConfig();
    }

    public void loadAppConfigInfo() {
        dataModel = new LazyDataModel<AppConfig>() {

            private static final long serialVersionUID = 1L;
            private List<AppConfig> list = new ArrayList<AppConfig>();

            @Override
            public List<AppConfig> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<AppConfig>) entityDAOFacade.getResultList(AppConfig.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, AppConfig.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(AppConfig obj) {
                return obj.getId();
            }

            @Override
            public AppConfig getRowData(String rowKey) {
                for (AppConfig obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }


    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public ArrayList<AppConfig> getAppConfigList() {
        return appConfigList;
    }

    public void setAppConfigList(ArrayList<AppConfig> appConfigList) {
        this.appConfigList = appConfigList;
    }

    public LazyDataModel<AppConfig> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<AppConfig> dataModel) {
        this.dataModel = dataModel;
    }

}
