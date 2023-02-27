package com.pc.repositories.lookup;

import com.pc.entities.lookup.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<Title, Integer> {

    Title findByIdAndDeleted(Long parseLong, boolean deleted);

    List<Title> findByDescriptionAndDeleted(String desc, boolean deleted);

    List<Title> findByDeleted(boolean deleted);

}
