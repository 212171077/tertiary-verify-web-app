package com.pc.service;

import com.pc.entities.DocConfigDetailActionHist;
import com.pc.framework.AbstractService;
import com.pc.repositories.DocConfigDetailActionHistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DocConfigDetailActionHistService extends AbstractService {
    @Autowired
    DocConfigDetailActionHistRepository repository;

    public void saveDocConfigDetailActionHist(DocConfigDetailActionHist docConfigDetailActionHist) throws Exception {
        if (docConfigDetailActionHist.getId() == null) {
            docConfigDetailActionHist.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                docConfigDetailActionHist.setLastUpdateUser(getCurrentUser());
            }
            docConfigDetailActionHist.setLastUpdateDate(new Date());
        }
        repository.save(docConfigDetailActionHist);
    }

    public void deleteDocConfigDetailActionHist(DocConfigDetailActionHist docConfigDetailActionHist) throws Exception {
        repository.delete(docConfigDetailActionHist);
    }

    public void deleteDocConfigDetailActionHistByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<DocConfigDetailActionHist> findAllDocConfigDetailActionHist() throws Exception {
        return repository.findAll();
    }

    public Page<DocConfigDetailActionHist> findAllDocConfigDetailActionHist(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<DocConfigDetailActionHist> findAllDocConfigDetailActionHist(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public DocConfigDetailActionHist findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

}
