package com.pc.ui.lookup;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.ImageModel;
import com.pc.entities.lookup.Event;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.EventService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("eventUI")
@ViewScoped
public class EventUI extends AbstractUI {

    @Autowired
    EventService eventService;
    private ArrayList<Event> eventList;
    private Event event;
    private LazyDataModel<Event> dataModel;
    private Date minDateTime;
    private Date maxDateTime;
    @Autowired
    private EntityDAOFacade entityDAOFacade;
    private ImageModel imageModel;

    @PostConstruct
    public void init() {
        try {
            event = new Event();
            eventList = eventService.findByActive(true);
            Date today = new Date();
            long oneDay = 24 * 60 * 60 * 1000;
            minDateTime = new Date(today.getTime() - (7 * oneDay));
            maxDateTime = new Date(today.getTime() + (7 * oneDay));
            loadEventInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveEvent() {
        try {
            if (imageModel == null) {
                throw new Exception("Please upload event image");
            }
            eventService.saveEvent(event,imageModel);
            displayInfoMssg("Update Successful...!!");
            loadEventInfo();
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
            displayInfoMssg("Event image uploaded");
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
    }

    public void deleteEvent() {
        try {
            eventService.deleteEvent(event);
            displayWarningMssg("Update Successful...!!");
            loadEventInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Event> findAllEvent() {
        List<Event> list = new ArrayList<>();
        try {
            list = eventService.findAllEvent();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public void reset() {
        event = new Event();
    }

    public void loadEventInfo() {
        dataModel = new LazyDataModel<Event>() {

            private static final long serialVersionUID = 1L;
            private List<Event> list = new ArrayList<Event>();

            @Override
            public List<Event> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<Event>) entityDAOFacade.getResultList(Event.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, Event.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(Event obj) {
                return obj.getId();
            }

            @Override
            public Event getRowData(String rowKey) {
                for (Event obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public String formatDate(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
        return simpleDateFormat.format(date);
    }

    public String getDay(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd");
        return simpleDateFormat.format(date);
    }

    public String getMonth(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM");
        return simpleDateFormat.format(date);
    }

    public String getTime(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(date);
    }


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public LazyDataModel<Event> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<Event> dataModel) {
        this.dataModel = dataModel;
    }

    public Date getMinDateTime() {
        return minDateTime;
    }

    public void setMinDateTime(Date minDateTime) {
        this.minDateTime = minDateTime;
    }

    public Date getMaxDateTime() {
        return maxDateTime;
    }

    public void setMaxDateTime(Date maxDateTime) {
        this.maxDateTime = maxDateTime;
    }
}
