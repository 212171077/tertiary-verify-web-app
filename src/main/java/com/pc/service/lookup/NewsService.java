package com.pc.service.lookup;

import com.pc.entities.ImageModel;
import com.pc.entities.lookup.News;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.NewsRepository;
import com.pc.service.ImagesService;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NewsService extends AbstractService {
    @Autowired
    private NewsRepository repository;
    @Autowired
    private ImagesService imagesService;

    public void saveNews(News news, ImageModel imageModel) throws Exception {
        if (news.getId() == null) {
            news.setDeleted(false);
            news.setCreateDate(new Date());
        }

        if (getCurrentUser() != null) {
            news.setLastUpdateUser(getCurrentUser());
        }
        news.setLastUpdateDate(new Date());

        if (news.getImage() == null && imageModel != null) {
            imageModel = imagesService.save(imageModel);
            news.setImage(imageModel);
        } else if (imageModel != null) {
            ImageModel newImg = news.getImage();
            newImg.setName(imageModel.getName());
            newImg.setType(imageModel.getType());
            newImg.setPic(imageModel.getPic());
            imagesService.save(newImg);
        }

        if (news.getImage() == null) {
            news.setActive(false);
        }

        repository.save(news);

    }

    public void deleteNews(News news) throws Exception {
        news.setDeleted(true);
        repository.save(news);
    }

    public List<News> findAllNews() throws Exception {
        return repository.findByDeleted(false);
    }

    public News findById(Long parseLong) throws Exception {
        return repository.findByIdAndDeleted(parseLong, false);
    }

    public List<News> findByDescriptionStartingWith(String description) throws Exception {
        return repository.findByDeletedAndDescriptionStartingWith(false, description);
    }

    public List<News> findByActive(boolean active) throws Exception {
        return repository.findByActiveAndDeleted(active, false);
    }
}
