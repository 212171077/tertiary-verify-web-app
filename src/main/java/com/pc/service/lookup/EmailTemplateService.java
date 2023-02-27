package com.pc.service.lookup;

import com.pc.entities.lookup.EmailTemplate;
import com.pc.entities.lookup.Workflow;
import com.pc.framework.AbstractService;
import com.pc.repositories.lookup.EmailTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmailTemplateService extends AbstractService {
    @Autowired
    EmailTemplateRepository repository;

    public void saveEmailTemplate(EmailTemplate emailTemplate) throws Exception {
        if (emailTemplate.getId() == null) {
            emailTemplate.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                emailTemplate.setLastUpdateUser(getCurrentUser());
            }
            emailTemplate.setLastUpdateDate(new Date());
        }
        repository.save(emailTemplate);
    }

    public void deleteEmailTemplate(EmailTemplate emailTemplate) throws Exception {
        repository.delete(emailTemplate);
    }

    public void deleteEmailTemplateByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<EmailTemplate> findAllEmailTemplate() throws Exception {
        return repository.findAll();
    }

    public List<EmailTemplate> findByWorkflow(Workflow wf) throws Exception {
        return repository.findByWorkflow(wf);
    }

    public Page<EmailTemplate> findAllEmailTemplate(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<EmailTemplate> findAllEmailTemplate(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public EmailTemplate findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

    public List<EmailTemplate> findByDescriptionStartingWith(String description) throws Exception {
        return repository.findByDescriptionStartingWith(description);
    }


}
