package com.pc.service.lookup;

import com.pc.entities.lookup.DocumentLevel;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.DocumentLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DocumentLevelService extends AbstractService {
    @Autowired
    DocumentLevelRepository repository;

    public void saveDocumentLevel(DocumentLevel documentLevel) throws Exception {
        if (documentLevel.getId() == null) {
            documentLevel.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                documentLevel.setLastUpdateUser(getCurrentUser());
            }
            documentLevel.setLastUpdateDate(new Date());
        }
        repository.save(documentLevel);
    }

    public void deleteDocumentLevel(DocumentLevel documentLevel) throws Exception {
        repository.delete(documentLevel);
    }

    public void deleteDocumentLevelByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<DocumentLevel> findAllDocumentLevel() throws Exception {
        return repository.findAll();
    }

    public Page<DocumentLevel> findAllDocumentLevel(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<DocumentLevel> findAllDocumentLevel(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public DocumentLevel findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

    public List<DocumentLevel> findByDescriptionStartingWith(String description) throws Exception {
        return repository.findByDescriptionStartingWith(description);
    }


}
