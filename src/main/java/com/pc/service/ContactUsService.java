package com.pc.service;

import com.pc.beans.Mail;
import com.pc.constants.AppConstants;
import com.pc.entities.ContactUs;
import com.pc.entities.lookup.AppConfig;
import com.pc.framework.AbstractService;
import com.pc.mail.MailSender;
import com.pc.repositories.ContactUsRepository;
import com.pc.service.lookup.AppConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContactUsService extends AbstractService {
    @Autowired
    ContactUsRepository repository;
    @Autowired
    MailSender mailSender;
    @Autowired
    AppConfigService appConfigService;

    public void saveContactUs(ContactUs contactUs) throws Exception {
        if (contactUs.getId() == null) {
            contactUs.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                contactUs.setLastUpdateUser(getCurrentUser());
            }
            contactUs.setLastUpdateDate(new Date());
        }
        sendContactUsMessage(contactUs);
    }

    public void deleteContactUs(ContactUs contactUs) throws Exception {
        repository.delete(contactUs);
    }

    public void deleteContactUsByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<ContactUs> findAllContactUs() throws Exception {
        return repository.findAll();
    }

    public Page<ContactUs> findAllContactUs(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<ContactUs> findAllContactUs(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public ContactUs findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }

    public ContactUs sendContactUsMessage(ContactUs contactUs) throws Exception {
        contactUs.setPhoneNumber("0" + contactUs.getPhoneNumber());
        repository.save(contactUs);
        sendEmailToAdmin(contactUs);
        sendConfirmationEmail(contactUs);
        return contactUs;
    }

    private void sendConfirmationEmail(ContactUs contactUs) throws Exception {
        String welcome = "<p>Dear " + contactUs.getFullName() + "</p>"
                + "<br/>"
                + "<p>Thank you for being in contact. "
                + "Your message has been submitted successfully</p>"
                + "<p>Regards</p>"
                + "<p>Tertiary Verify Team</p>"
                + "<br/>";
        Mail mail = new Mail();
        mail.setContent(welcome);
        mail.setFrom(AppConstants.FROM_EMAIL);
        String[] to = {contactUs.getEmail()};
        mail.setTo(to);
        mail.setSubject("Tertiary Verify Contact us");
        mail.setCc(to);
        mailSender.saveEmail(mail);
    }

    private void sendEmailToAdmin(ContactUs contactUs) throws Exception {
        List<AppConfig> contactUsList = appConfigService.findByCode("CONTACT_US");
        String welcome = "<p>Dear Admin</p>"
                + "<br/>"
                + "<p><b>From:</b> " + contactUs.getFullName() + " "
                + "<p><b>Contact Details:</b> " + contactUs.getPhoneNumber() + ", " + contactUs.getEmail() + " </p>"
                + "<p><b>Message: </b> " + contactUs.getMessage() + "</p>"
                + "<p>Regards</p>"
                + "<p>Tertiary Verify Team</p>"
                + "<br/>";
        Mail mail = new Mail();
        mail.setContent(welcome);
        mail.setFrom(AppConstants.FROM_EMAIL);
        List<String> toList = new ArrayList<>();
        if (contactUsList != null && contactUsList.size() > 0) {
            for (AppConfig appConfig : contactUsList) {
                toList.add(appConfig.getValue());
            }
        } else {
            toList.add("venussibiya@gmail.com");
        }
        String[] to = new String[toList.size()];
        to = toList.toArray(to);
        mail.setTo(to);
        mail.setSubject("Tertiary Verify Contact us");
        mail.setCc(to);
        mailSender.saveEmail(mail);
    }

}
