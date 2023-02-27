package com.pc.repositories;


import com.pc.entities.Address;
import com.pc.entities.lookup.City;
import com.pc.entities.lookup.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    Address findById(Long parseLong);

    long countByCity(City city);

    long countByProvince(Province province);

}
