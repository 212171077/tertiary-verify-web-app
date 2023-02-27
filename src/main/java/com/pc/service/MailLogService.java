package com.pc.service;

import com.pc.entities.MailLog;
import com.pc.framework.AbstractService;
import com.pc.repositories.MailLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MailLogService extends AbstractService {
    @Autowired
    MailLogRepository repository;

    public void saveMailLog(MailLog mailLog) throws Exception {
        if (mailLog.getId() == null) {
            mailLog.setCreateDate(new Date());
            mailLog.setDeleted(false);
        } else {
            if (getCurrentUser() != null) {
                mailLog.setLastUpdateUser(getCurrentUser());
            }
            mailLog.setLastUpdateDate(new Date());
        }

        repository.save(mailLog);
    }

    public void deleteMailLog(MailLog mailLog) throws Exception {
        repository.delete(mailLog);
    }

    public void deleteMailLogByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<MailLog> findAllMailLog() throws Exception {
        return repository.findAll();
    }

    public Page<MailLog> findAllMailLog(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<MailLog> findAllMailLog(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public MailLog findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

}
