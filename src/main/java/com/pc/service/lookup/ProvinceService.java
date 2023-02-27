package com.pc.service.lookup;

import com.pc.entities.lookup.Province;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.ProvinceRepository;
import com.pc.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProvinceService extends AbstractService {
    @Autowired
    private ProvinceRepository repository;

    @Autowired
    private CityService cityService;

    @Autowired
    private AddressService addressService;

    public void saveProvince(Province province) throws Exception {

        if (province.getId() == null) {
            checkIfExist(province.getDescription());
            province.setDeleted(false);
            province.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                province.setLastUpdateUser(getCurrentUser());
            }
            province.setLastUpdateDate(new Date());
        }
        repository.save(province);
    }

    public void deleteProvince(Province province) throws Exception {
        checkIfInUse(province);
        province.setDeleted(true);
        repository.save(province);
    }

    public List<Province> findAllProvince() throws Exception {
        return repository.findByDeleted(false);
    }

    public Province findById(long id) throws Exception {
        return repository.findByIdAndDeleted(id,false);
    }

    public Province findByCode(String code) throws Exception {
        return repository.findByCodeAndDeleted(code,false);
    }

    private void checkIfExist(String desc) throws Exception {
        List<Province> list = repository.findByDescriptionAndDeleted(desc,false);
        if (list != null && list.size() > 0) {
            throw new Exception("Province already exist");
        }
    }

    private void checkIfInUse(Province province) throws Exception {
        long count = cityService.countByProvince(province);
        if (count > 0) {
            throw new Exception("This province cannot be deleted because it's being used");
        } else {
            count = addressService.countByProvince(province);
            if (count > 0) {
                throw new Exception("This province cannot be deleted because it's being used");
            }
        }
    }


}
