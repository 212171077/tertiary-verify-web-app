package com.pc.ui;

import com.pc.common.AES;
import com.pc.entities.lookup.News;
import com.pc.framework.AbstractUI;
import com.pc.service.ImagesService;
import com.pc.service.lookup.NewsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Map;

@Component("newsDetailsUI")
@ViewScoped
public class NewsDetailsUI extends AbstractUI {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private News news;

    @Autowired
    private NewsService newsService;
    @Autowired
    private ImagesService imagesService;

    @PostConstruct
    public void init() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();

            String ref = AES.decrypt(paramMap.get("ref"));
            logger.info("Decrypted ref: " + ref);
            if (ref == null) {
                logger.error("News ID is null");
                ajaxRedirect("/newsandevents.xhtml");
            } else {
                String[] elements = ref.split("_");
                news = newsService.findById(Long.valueOf(elements[0]));
                news.setImage(imagesService.getById(news.getImage().getId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

}
