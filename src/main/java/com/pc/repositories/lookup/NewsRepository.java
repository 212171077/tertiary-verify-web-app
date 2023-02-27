package com.pc.repositories.lookup;


import com.pc.entities.lookup.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    News findByIdAndDeleted(Long parseLong, boolean deleted);

    List<News> findByDeletedAndDescriptionStartingWith(boolean deleted, String description);

    List<News> findByActiveAndDeleted(boolean active, boolean deleted);

    List<News> findByDeleted(boolean deleted);
}
