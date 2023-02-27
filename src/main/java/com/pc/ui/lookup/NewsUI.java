package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.ImageModel;
import com.pc.entities.lookup.News;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.NewsService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("newsUI")
@ViewScoped
public class NewsUI extends AbstractUI {

    @Autowired
    private NewsService newsService;
    @Autowired
    private EntityDAOFacade entityDAOFacade;
    private List<News> newsList;
    private News news;
    private LazyDataModel<News> dataModel;
    private ImageModel imageModel;

    @PostConstruct
    public void init() {
        try {
            news = new News();
            loadNewsInfo();
            newsList = newsService.findByActive(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveNews() {
        try {
            if (imageModel == null) {
                throw new Exception("Please upload news image");
            }
            newsService.saveNews(news, imageModel);
            displayInfoMssg("News added successful, please add new image");
            loadNewsInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleFileUpload(FileUploadEvent fileUploadEvent) {

        try {

            imageModel = new ImageModel();
            imageModel.setName(fileUploadEvent.getFile().getFileName().trim());
            imageModel.setType(fileUploadEvent.getFile().getContentType());
            imageModel.setPic(fileUploadEvent.getFile().getContents());
            displayInfoMssg("News image uploaded");
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
    }

    public void deleteNews() {
        try {
            newsService.deleteNews(news);
            displayWarningMssg("Update Successful...!!");
            loadNewsInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<News> findAllNews() {
        List<News> list = new ArrayList<>();
        try {
            list = newsService.findAllNews();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public void reset() {
        news = new News();
        imageModel = null;
    }

    public void loadNewsInfo() {
        dataModel = new LazyDataModel<News>() {

            private static final long serialVersionUID = 1L;
            private List<News> list = new ArrayList<News>();

            @Override
            public List<News> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<News>) entityDAOFacade.getResultList(News.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, News.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(News obj) {
                return obj.getId();
            }

            @Override
            public News getRowData(String rowKey) {
                for (News obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
                }
                return null;
            }

        };

    }


    public String createRef(News news) {
        return news.getId() + "_" + news.getImage();
    }


    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public LazyDataModel<News> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<News> dataModel) {
        this.dataModel = dataModel;
    }

}
