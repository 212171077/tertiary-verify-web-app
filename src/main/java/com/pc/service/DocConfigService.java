package com.pc.service;

import com.pc.entities.DocConfig;
import com.pc.entities.lookup.DocumentLevel;
import com.pc.entities.lookup.Workflow;
import com.pc.framework.AbstractService;
import com.pc.repositories.DocConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DocConfigService extends AbstractService {
    @Autowired
    DocConfigRepository repository;

    public void saveDocConfig(DocConfig docConfig) throws Exception {
        if (docConfig.getId() == null) {
            docConfig.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                docConfig.setLastUpdateUser(getCurrentUser());
            }
            docConfig.setLastUpdateDate(new Date());
        }
        repository.save(docConfig);
    }

    public void deleteDocConfig(DocConfig docConfig) throws Exception {
        repository.delete(docConfig);
    }

    public void deleteDocConfigByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<DocConfig> findAllDocConfig() throws Exception {
        return repository.findAll();
    }

    public Page<DocConfig> findAllDocConfig(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<DocConfig> findAllDocConfig(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public DocConfig findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

    public List<DocConfig> findByWorkflowAndDocumentLevelOrderByDescriptionAsc(Workflow workflow, DocumentLevel documentLevel) throws Exception {
        return repository.findByWorkflowAndDocumentLevelOrderByDescriptionAsc(workflow, documentLevel);
    }


}
