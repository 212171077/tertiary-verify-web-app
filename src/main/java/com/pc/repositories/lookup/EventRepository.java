package com.pc.repositories.lookup;


import com.pc.entities.lookup.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    Event findByIdAndDeleted(Long parseLong, boolean deleted);

    List<Event> findByDeletedAndDescriptionStartingWith(boolean deleted, String description);

    ArrayList<Event> findByActiveAndDeleted(boolean active, boolean deleted);

    ArrayList<Event> findByDeleted(boolean deleted);
}
