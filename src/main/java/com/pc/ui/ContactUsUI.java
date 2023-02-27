package com.pc.ui;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.ContactUs;
import com.pc.framework.AbstractUI;
import com.pc.service.ContactUsService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("contactUsUI")
@ViewScoped
public class ContactUsUI extends AbstractUI {

    @Autowired
    ContactUsService contactUsService;
    @Autowired
    EntityDAOFacade entityDAOFacade;
    private ArrayList<ContactUs> contactUsList;
    private ContactUs contactUs;
    private LazyDataModel<ContactUs> dataModel;

    @PostConstruct
    public void init() {
        contactUs = new ContactUs();
        loadContactUsInfo();
    }

    public void saveContactUs() {
        try {
            contactUsService.saveContactUs(contactUs);
            displayInfoMssg("Message was successfully sent");
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteContactUs() {
        try {
            contactUsService.deleteContactUs(contactUs);
            displayWarningMssg("Message deleted successful...!!");
            loadContactUsInfo();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<ContactUs> findAllContactUs() {
        List<ContactUs> list = new ArrayList<>();
        try {
            list = contactUsService.findAllContactUs();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<ContactUs> findAllContactUsPageable() {
        Pageable p = null;
        try {
            return contactUsService.findAllContactUs(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<ContactUs> findAllContactUsSort() {
        Sort s = null;
        List<ContactUs> list = new ArrayList<>();
        try {
            list = contactUsService.findAllContactUs(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        contactUs = new ContactUs();
    }

    public void loadContactUsInfo() {
        dataModel = new LazyDataModel<ContactUs>() {

            private static final long serialVersionUID = 1L;
            private List<ContactUs> list = new ArrayList<ContactUs>();

            @Override
            public List<ContactUs> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<ContactUs>) entityDAOFacade.getResultList(ContactUs.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(entityDAOFacade.count(filters, ContactUs.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(ContactUs obj) {
                return obj.getId();
            }

            @Override
            public ContactUs getRowData(String rowKey) {
                for (ContactUs obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public ContactUs getContactUs() {
        return contactUs;
    }

    public void setContactUs(ContactUs contactUs) {
        this.contactUs = contactUs;
    }

    public ArrayList<ContactUs> getContactUsList() {
        return contactUsList;
    }

    public void setContactUsList(ArrayList<ContactUs> contactUsList) {
        this.contactUsList = contactUsList;
    }

    public LazyDataModel<ContactUs> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<ContactUs> dataModel) {
        this.dataModel = dataModel;
    }


}
