package com.pc.repositories.lookup;

import com.pc.entities.lookup.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByIdAndDeleted(Long id, boolean deleted);

    Role findByCodeAndDeleted(String code, boolean deleted);

    List<Role> findByDescriptionAndDeleted(String desc, boolean deleted);
    List<Role> findByDeleted( boolean deleted);

}
