package com.pc.ui.lookup;

import com.pc.entities.ImageModel;
import com.pc.entities.User;
import com.pc.entities.UserRole;
import com.pc.entities.lookup.News;
import com.pc.framework.AbstractUI;
import com.pc.service.ImagesService;
import com.pc.service.UserRoleService;
import com.pc.service.UserService;
import com.pc.service.lookup.NewsService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
@ManagedBean(name = "newsImageUI")
@ViewScoped
@RequestScoped
public class NewsImageUI extends AbstractUI {

    @Autowired
    private ImagesService imagesService;
    @Autowired
    private UserRoleService roleService;
    @Autowired
    private NewsService newsService;
    private News news;


    public void handleFileUpload(FileUploadEvent event) {

        try {
            ImageModel imageModel = new ImageModel();
            imageModel.setName(event.getFile().getFileName().trim());
            imageModel.setType(event.getFile().getContentType());
            imageModel.setPic(event.getFile().getContents());
            newsService.saveNews(news, imageModel);
            prepareCurrentUser();
            displayInfoMssg("Profile image uploaded successfully");
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
