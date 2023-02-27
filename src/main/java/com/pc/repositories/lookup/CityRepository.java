package com.pc.repositories.lookup;


import com.pc.entities.lookup.City;
import com.pc.entities.lookup.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    City findByIdAndDeleted(Long parseLong, boolean deleted);

    List<City> findByProvinceAndDeleted(Province province, boolean deleted);

    List<City> findByProvinceIdAndDeleted(Long provinceId, boolean deleted);

    List<City> findByDescriptionAndDeleted(String desc, boolean deleted);

    long countByProvinceAndDeleted(Province province, boolean deleted);

    List<City> findByDeleted(boolean deleted);
}
