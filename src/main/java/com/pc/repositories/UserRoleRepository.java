package com.pc.repositories;

import com.pc.entities.User;
import com.pc.entities.UserRole;
import com.pc.entities.lookup.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    List<UserRole> findByUser(User user);

    List<UserRole> findByRole(Role role);

    UserRole findById(long parseLong);

    long countByRole(Role role);


}
