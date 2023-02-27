package com.pc.service;

import com.pc.entities.ImageModel;
import com.pc.framework.AbstractService;
import com.pc.repositories.ImageModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImagesService extends AbstractService {
    @Autowired
    ImageModelRepository imageModelRepository;

    public ImageModel save(ImageModel imageModel) throws Exception {
        if (imageModel.getId() == null) {
            imageModel.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                imageModel.setLastUpdateUser(getCurrentUser());
            }
            imageModel.setLastUpdateDate(new Date());
        }
        return imageModelRepository.save(imageModel);
    }

    public ImageModel getById(Long id) throws Exception {
        return imageModelRepository.getById(id);
    }

}
