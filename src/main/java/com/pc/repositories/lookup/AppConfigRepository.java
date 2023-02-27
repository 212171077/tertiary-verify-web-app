package com.pc.repositories.lookup;


import com.pc.entities.lookup.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppConfigRepository extends JpaRepository<AppConfig, Integer> {

    AppConfig findByIdAndDeleted(Long parseLong, boolean deleted);

    List<AppConfig> findByCodeAndDeleted(String code, boolean deleted);

    List<AppConfig> findByDeleted(boolean deleted);


}
