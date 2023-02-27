package com.pc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

import com.pc.entities.EmailContent;
import com.pc.repositories.EmailContentRepository;
import com.pc.framework.AbstractService;

@Service
public class EmailContentService extends AbstractService {
    @Autowired
    EmailContentRepository repository;

    public void saveEmailContent(EmailContent emailContent) throws Exception {
        if (emailContent.getId() == null) {
            emailContent.setCreateDate(new Date());
            emailContent.setDeleted(false);
        } else {

            if (getCurrentUser() != null) {
                emailContent.setLastUpdateUser(getCurrentUser());
            }
            emailContent.setLastUpdateDate(new Date());
        }
        repository.save(emailContent);
    }

    public void deleteEmailContent(EmailContent emailContent) throws Exception {
        repository.delete(emailContent);
    }

    public void deleteEmailContentByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<EmailContent> findAllEmailContent() throws Exception {
        return repository.findAll();
    }

    public Page<EmailContent> findAllEmailContent(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<EmailContent> findAllEmailContent(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public EmailContent findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

    public void deleteAllEmailContent(List<EmailContent> emailContents) throws Exception {
        repository.deleteAll(emailContents);
    }

    public List<EmailContent> findTop100OrderByIdDesc() throws Exception {
        return repository.findTop100ByOrderByIdDesc();
    }

}
