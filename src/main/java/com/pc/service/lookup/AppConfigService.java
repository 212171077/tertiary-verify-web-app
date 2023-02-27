package com.pc.service.lookup;

import com.pc.entities.lookup.AppConfig;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.AppConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppConfigService extends AbstractService {
    @Autowired
    private AppConfigRepository repository;

    public void saveAppConfig(AppConfig appConfig) throws Exception {
        if (appConfig.getId() == null) {
            appConfig.setDeleted(false);
            appConfig.setCreateDate(new Date());
        } else {

            if (getCurrentUser() != null) {
                appConfig.setLastUpdateUser(getCurrentUser());
            }
            appConfig.setLastUpdateDate(new Date());
        }
        repository.save(appConfig);
    }

    public void deleteAppConfig(AppConfig appConfig) throws Exception {
        appConfig.setDeleted(true);
        repository.save(appConfig);
    }


    public List<AppConfig> findAllAppConfig() throws Exception {
        return repository.findByDeleted(false);
    }


    public AppConfig findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong, false);
    }

    public List<AppConfig> findByCode(String code) throws Exception {
        return repository.findByCodeAndDeleted(code, false);
    }


}
