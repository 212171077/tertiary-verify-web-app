package com.pc.service;

import com.pc.entities.DocConfigDetail;
import com.pc.framework.AbstractService;
import com.pc.repositories.DocConfigDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DocConfigDetailService extends AbstractService {
    @Autowired
    DocConfigDetailRepository repository;

    public void saveDocConfigDetail(DocConfigDetail docConfigDetail) throws Exception {
        if (docConfigDetail.getId() == null) {
            docConfigDetail.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                docConfigDetail.setLastUpdateUser(getCurrentUser());
            }
            docConfigDetail.setLastUpdateDate(new Date());
        }
        repository.save(docConfigDetail);
    }

    public void deleteDocConfigDetail(DocConfigDetail docConfigDetail) throws Exception {
        repository.delete(docConfigDetail);
    }

    public void deleteDocConfigDetailByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<DocConfigDetail> findAllDocConfigDetail() throws Exception {
        return repository.findAll();
    }

    public Page<DocConfigDetail> findAllDocConfigDetail(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<DocConfigDetail> findAllDocConfigDetail(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public DocConfigDetail findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

}
