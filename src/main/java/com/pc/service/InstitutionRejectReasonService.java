package com.pc.service;

import com.pc.entities.Institution;
import com.pc.entities.InstitutionRejectReason;
import com.pc.entities.lookup.RejectReason;
import com.pc.framework.AbstractService;
import com.pc.repositories.InstitutionRejectReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InstitutionRejectReasonService extends AbstractService {
    @Autowired
    InstitutionRejectReasonRepository repository;

    public void saveInstitutionRejectReason(InstitutionRejectReason institutionRejectReason) throws Exception {
        if (institutionRejectReason.getId() == null) {
            institutionRejectReason.setCreateDate(new Date());
        }
        repository.save(institutionRejectReason);
    }

    public void deleteInstitutionRejectReason(InstitutionRejectReason institutionRejectReason) throws Exception {
        repository.delete(institutionRejectReason);
    }

    public void deleteInstitutionRejectReasonByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<InstitutionRejectReason> findAllInstitutionRejectReason() throws Exception {
        return repository.findAll();
    }

    public Page<InstitutionRejectReason> findAllInstitutionRejectReason(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<InstitutionRejectReason> findAllInstitutionRejectReason(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public InstitutionRejectReason findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

    public List<InstitutionRejectReason> findByInstitution(Institution institution) {
        return repository.findByInstitution(institution);
    }


    public long countByRejectReason(RejectReason rejectReason) {
        return repository.countByRejectReason(rejectReason);
    }
}
