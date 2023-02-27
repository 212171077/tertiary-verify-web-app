package com.pc.service.lookup;

import com.pc.entities.lookup.City;
import com.pc.entities.lookup.Province;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.CityRepository;
import com.pc.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CityService extends AbstractService {
    @Autowired
    private CityRepository repository;

    @Autowired
    private AddressService addressService;

    public void saveCity(City city) throws Exception {

        if (city.getId() == null) {
            checkIfExist(city.getDescription());
            city.setDeleted(false);
            city.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                city.setLastUpdateUser(getCurrentUser());
            }
            city.setLastUpdateDate(new Date());
        }
        repository.save(city);
    }

    public void deleteCity(City city) throws Exception {
        checkIfInUse(city);
        city.setDeleted(true);
        repository.save(city);
    }


    public List<City> findAllCity() throws Exception {
        return repository.findByDeleted(false);
    }

    public City findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong, false);
    }

    public List<City> findByProvince(Province province) {
        return repository.findByProvinceAndDeleted(province, false);
    }

    public List<City> findByProvinceId(Long provinceId) {
        return repository.findByProvinceIdAndDeleted(provinceId, false);
    }

    private void checkIfExist(String desc) throws Exception {
        List<City> list = repository.findByDescriptionAndDeleted(desc, false);
        if (list != null && list.size() > 0) {
            throw new Exception("City already exist");
        }
    }

    private void checkIfInUse(City city) throws Exception {
        long count = addressService.countByCity(city);
        if (count > 0) {
            throw new Exception("This city cannot be deleted because it's being used");
        }
    }
    public long countByProvince(Province province) {
        return repository.countByProvinceAndDeleted(province, false);
    }
}
