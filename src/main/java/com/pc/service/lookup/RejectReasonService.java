package com.pc.service.lookup;

import com.pc.entities.lookup.RejectReason;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.RejectReasonRepository;
import com.pc.service.InstitutionRejectReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RejectReasonService extends AbstractService {
    @Autowired
    private RejectReasonRepository repository;

    @Autowired
    private InstitutionRejectReasonService institutionRejectReasonService;

    public void saveRejectReason(RejectReason rejectReason) throws Exception {

        if (rejectReason.getId() == null) {
            checkIfExist(rejectReason.getDescription());
            rejectReason.setDeleted(false);
            rejectReason.setCreateDate(new Date());
        } else {

            if (getCurrentUser() != null) {
                rejectReason.setLastUpdateUser(getCurrentUser());
            }
            rejectReason.setLastUpdateDate(new Date());
        }
        repository.save(rejectReason);
    }

    public void deleteRejectReason(RejectReason rejectReason) throws Exception {
        checkIfInUse(rejectReason);
        rejectReason.setDeleted(true);
        repository.save(rejectReason);
    }

    public List<RejectReason> findAllRejectReason() throws Exception {
        return repository.findByDeleted(false);
    }

    public RejectReason findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong,false);
    }

    public List<RejectReason> findByDescriptionStartingWith(String description) throws Exception {
        return repository.findByDeletedAndDescriptionStartingWith(false,description);
    }

    private void checkIfExist(String desc) throws Exception {
        List<RejectReason> list = repository.findByDescriptionAndDeleted(desc,false);
        if (list != null && list.size() > 0) {
            throw new Exception("Reject reason already exist");
        }
    }


    private void checkIfInUse(RejectReason rejectReason) throws Exception {
        long count = institutionRejectReasonService.countByRejectReason(rejectReason);
        if (count > 0) {
            throw new Exception("This reject reason cannot be deleted because it's being used");
        }
    }


}
