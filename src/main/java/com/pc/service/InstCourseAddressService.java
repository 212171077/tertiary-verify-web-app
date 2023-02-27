package com.pc.service;

import com.pc.entities.InstCourseAddress;
import com.pc.framework.AbstractService;
import com.pc.repositories.InstCourseAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InstCourseAddressService extends AbstractService {
    @Autowired
    InstCourseAddressRepository repository;

    public void saveInstCourseAddress(InstCourseAddress instCourseAddress) throws Exception {
        if (instCourseAddress.getId() == null) {
            instCourseAddress.setCreateDate(new Date());
        } else {

            if (getCurrentUser() != null) {
                instCourseAddress.setLastUpdateUser(getCurrentUser());
            }
            instCourseAddress.setLastUpdateDate(new Date());
        }
        repository.save(instCourseAddress);
    }

    public void deleteInstCourseAddress(InstCourseAddress instCourseAddress) throws Exception {
        repository.delete(instCourseAddress);
    }

    public void deleteInstCourseAddressByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<InstCourseAddress> findAllInstCourseAddress() throws Exception {
        return repository.findAll();
    }

    public Page<InstCourseAddress> findAllInstCourseAddress(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<InstCourseAddress> findAllInstCourseAddress(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public InstCourseAddress findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

    public void saveAll(ArrayList<InstCourseAddress> instCourseAddressList) throws Exception {
        repository.saveAll(instCourseAddressList);
    }

    public List<InstCourseAddress> findByIds(List<String> idList) throws Exception {
        return repository.findByIds(idList);
    }


}
