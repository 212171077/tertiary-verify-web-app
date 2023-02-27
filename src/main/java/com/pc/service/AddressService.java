package com.pc.service;

import com.pc.entities.Address;
import com.pc.entities.lookup.City;
import com.pc.entities.lookup.Province;
import com.pc.framework.AbstractService;
import com.pc.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AddressService extends AbstractService {
    @Autowired
    AddressRepository repository;

    public void saveAddress(Address address) throws Exception {
        if (address.getId() == null) {
            address.setCreateDate(new Date());
        } else {

            if (getCurrentUser() != null) {
                address.setLastUpdateUser(getCurrentUser());
            }
            address.setLastUpdateDate(new Date());
        }
        repository.save(address);
    }

    public void deleteAddress(Address address) throws Exception {
        repository.delete(address);
    }

    public void deleteAddressByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<Address> findAllAddress() throws Exception {
        return repository.findAll();
    }

    public Page<Address> findAllAddress(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<Address> findAllAddress(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public Address findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

    public long countByCity(City city) throws Exception {
        return repository.countByCity(city);
    }


    public long countByProvince(Province province) {
        return repository.countByProvince(province);
    }
}
