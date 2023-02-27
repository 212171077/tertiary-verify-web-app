package com.pc.repositories;

import com.pc.entities.User;
import com.pc.entities.lookup.Gender;
import com.pc.entities.lookup.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select c from User c where c.email = ?1 and c.deleted =?2")
    User getUserByEmailAndDeleted(String email, boolean deleted);

    @Query("select c from User c where c.rsaId = ?1 and c.deleted =?2")
    User getUserByRsaIdAndDeleted(String rsaId, boolean deleted);

    User findByEmailAndDeleted(String email, boolean deleted);

    User findByEmailAndDeletedAndRsaIdNot(String email,boolean deleted, String rsaId);

    long countByTitleAndDeleted(Title title, boolean deleted);

    long countByGenderAndDeleted(Gender gender, boolean deleted);

    List<User> findByDeleted(boolean deleted);

}
