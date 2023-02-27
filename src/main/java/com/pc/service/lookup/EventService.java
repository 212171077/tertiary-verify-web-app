package com.pc.service.lookup;

import com.pc.entities.ImageModel;
import com.pc.entities.lookup.Event;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.EventRepository;
import com.pc.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService extends AbstractService {
    @Autowired
    private EventRepository repository;
    @Autowired
    private ImagesService imagesService;

    public void saveEvent(Event event, ImageModel imageModel) throws Exception {
        if (event.getId() == null) {
            event.setDeleted(false);
            event.setCreateDate(new Date());
        }

        if (getCurrentUser() != null) {
            event.setLastUpdateUser(getCurrentUser());
        }
        event.setLastUpdateDate(new Date());

        if (event.getImage() == null && imageModel != null) {
            imageModel = imagesService.save(imageModel);
            event.setImage(imageModel);
        } else if (imageModel != null) {
            ImageModel newImg = event.getImage();
            newImg.setName(imageModel.getName());
            newImg.setType(imageModel.getType());
            newImg.setPic(imageModel.getPic());
            imagesService.save(newImg);
        }

        repository.save(event);
    }

    public void deleteEvent(Event event) throws Exception {
        event.setDeleted(true);
        repository.save(event);
    }

    public List<Event> findAllEvent() throws Exception {
        return repository.findByDeleted(false);
    }

    public Event findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong, false);
    }

    public List<Event> findByDescriptionStartingWith(String description) throws Exception {
        return repository.findByDeletedAndDescriptionStartingWith(false, description);
    }


    public ArrayList<Event> findByActive(boolean active) throws Exception {
        return repository.findByActiveAndDeleted(active, false);
    }
}
