package com.pc.service.lookup;

import com.pc.entities.lookup.InstitutionType;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.InstitutionTypeRepository;
import com.pc.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InstitutionTypeService extends AbstractService {
    @Autowired
    private InstitutionTypeRepository repository;

    @Autowired
    private InstitutionService institutionService;

    public void saveInstitutionType(InstitutionType institutionType) throws Exception {

        if (institutionType.getId() == null) {
            checkIfExist(institutionType.getDescription());
            institutionType.setDeleted(false);
            institutionType.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                institutionType.setLastUpdateUser(getCurrentUser());
            }
            institutionType.setLastUpdateDate(new Date());
        }
        repository.save(institutionType);
    }

    public void deleteInstitutionType(InstitutionType institutionType) throws Exception {
        checkIfInUse(institutionType);
        institutionType.setDeleted(true);
        repository.save(institutionType);
    }

    public List<InstitutionType> findAllInstitutionType() throws Exception {
        return repository.findByDeleted(false);
    }

    public InstitutionType findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong,false);
    }

    private void checkIfExist(String desc) throws Exception {
        List<InstitutionType> list = repository.findByDescriptionAndDeleted(desc,false);
        if (list != null && list.size() > 0) {
            throw new Exception("Institution type already exist");
        }
    }

    private void checkIfInUse(InstitutionType institutionType) throws Exception {
        long count = institutionService.countByInstitutionType(institutionType);
        if (count > 0) {
            throw new Exception("This institution type cannot be deleted because it's being used");
        }
    }

}
