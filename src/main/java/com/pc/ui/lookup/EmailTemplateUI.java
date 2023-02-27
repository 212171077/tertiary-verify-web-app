package com.pc.ui.lookup;

import com.pc.constants.AppConstants;
import com.pc.entities.lookup.EmailTemplate;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("emailTemplateUI")
@ViewScoped
public class EmailTemplateUI extends AbstractUI {

    @Autowired
    EmailTemplateService emailTemplateService;
    private ArrayList<EmailTemplate> emailTemplateList;
    private EmailTemplate emailTemplate;
    private String tags = AppConstants.POSSIBLE_TAGS;

    @PostConstruct
    public void init() {
        emailTemplate = new EmailTemplate();
        emailTemplateList = (ArrayList<EmailTemplate>) findAllEmailTemplate();
    }

    public void saveEmailTemplate() {
        try {
            emailTemplateService.saveEmailTemplate(emailTemplate);
            displayInfoMssg("Update Successful...!!");
            emailTemplateList = (ArrayList<EmailTemplate>) findAllEmailTemplate();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteEmailTemplate() {
        try {
            emailTemplateService.deleteEmailTemplate(emailTemplate);
            displayWarningMssg("Update Successful...!!");
            emailTemplateList = (ArrayList<EmailTemplate>) findAllEmailTemplate();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<EmailTemplate> findAllEmailTemplate() {
        List<EmailTemplate> list = new ArrayList<>();
        try {
            list = emailTemplateService.findAllEmailTemplate();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<EmailTemplate> findAllEmailTemplatePageable() {
        Pageable p = null;
        try {
            return emailTemplateService.findAllEmailTemplate(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public void createDescription() {
        if (emailTemplate.getWorkflow() != null && emailTemplate.getEmailTemplateType() != null) {
            emailTemplate.setDescription(emailTemplate.getWorkflow().getDescription() + ": " + emailTemplate.getEmailTemplateType().getFriendlyName() + "");
            emailTemplate.setSubject(emailTemplate.getWorkflow().getDescription() + " " + emailTemplate.getEmailTemplateType().getFriendlyName() + "");
        }
    }

    public List<EmailTemplate> findAllEmailTemplateSort() {
        Sort s = null;
        List<EmailTemplate> list = new ArrayList<>();
        try {
            list = emailTemplateService.findAllEmailTemplate(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        emailTemplate = new EmailTemplate();
    }


    public EmailTemplate getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public ArrayList<EmailTemplate> getEmailTemplateList() {
        return emailTemplateList;
    }

    public void setEmailTemplateList(ArrayList<EmailTemplate> emailTemplateList) {
        this.emailTemplateList = emailTemplateList;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}
