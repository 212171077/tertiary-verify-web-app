package com.pc.repositories.lookup;


import com.pc.entities.lookup.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    Province findByIdAndDeleted(long id, boolean deleted);

    Province findByCodeAndDeleted(String code, boolean deleted);

    List<Province> findByDescriptionAndDeleted(String desc, boolean deleted);

    List<Province> findByDeleted(boolean deleted);

}
