package com.pc.mail;

import com.pc.beans.AttachmentBean;
import com.pc.beans.Mail;
import com.pc.entities.DocByte;
import com.pc.entities.EmailContent;
import com.pc.entities.MailLog;
import com.pc.service.DocByteService;
import com.pc.service.EmailContentService;
import com.pc.service.MailLogService;
import com.pc.service.lookup.AppConfigService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class MailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private MailLogService mailLogService;

    @Autowired
    private EmailContentService emailContentService;

    @Autowired
    private DocByteService docByteService;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private AppConfigService appConfigService;

    protected final Log logger = LogFactory.getLog(this.getClass());

    public void sendMail(Mail mail_info) {


        try {
            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setFrom(mail_info.getFrom());
            mail.setTo(mail_info.getTo());
            mail.setSubject(mail_info.getSubject());
            mail.setText(mail_info.getContent());
            javaMailSender.send(mail);

            MailLog mailLog = new MailLog(mail_info.getFrom(), getToEmails(mail_info), getCCEmails(mail_info), mail_info.getSubject(), mail_info.getContent(), false, "");
            mailLogService.saveMailLog(mailLog);
            createDocByte(mail_info, mailLog.getClass().getName(), mailLog.getId());
        } catch (Exception e) {
            try {
                MailLog mailLog = new MailLog(mail_info.getFrom(), getToEmails(mail_info), getCCEmails(mail_info), mail_info.getSubject(), mail_info.getContent(), true, e.getMessage());
                mailLogService.saveMailLog(mailLog);
                createDocByte(mail_info, mailLog.getClass().getName(), mailLog.getId());
                e.printStackTrace();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }


    }

    public void sendSimpleMessageWithAttachment(Mail mail) {

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject(mail.getSubject());
            helper.setText(mail.getContent());
            helper.setTo(mail.getTo());
            helper.setFrom(mail.getFrom());

            for (FileSystemResource attachment : mail.getAttachments()) {
                File file = attachment.getFile();
                helper.addAttachment(file.getName(), file);
            }

			/*FileSystemResource file = new FileSystemResource(new File("C:/Users/Patrick/Documents/springbootwebapptemplate/SpringBootWebappTemplate/src/main/webapp/images/b1.jpg"));
			helper.addAttachment("CoolImage.jpg", file);*/
            emailSender.send(message);
            MailLog mailLog = new MailLog(mail.getFrom(), getToEmails(mail), getCCEmails(mail), mail.getSubject(), mail.getContent(), false, "");
            mailLogService.saveMailLog(mailLog);
            createDocByte(mail, mailLog.getClass().getName(), mailLog.getId());
        } catch (Exception e) {
            try {
                MailLog mailLog = new MailLog(mail.getFrom(), getToEmails(mail), getCCEmails(mail), mail.getSubject(), mail.getContent(), true, e.getMessage());
                mailLogService.saveMailLog(mailLog);
                createDocByte(mail, mailLog.getClass().getName(), mailLog.getId());
                e.printStackTrace();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }


    public void saveEmail(Mail mail) {

        logger.info("***************SAVING EMAIL CONTENT*****************");
        try {

            EmailContent emailContent = new EmailContent(mail.getFrom(), getToEmails(mail), getCCEmails(mail), mail.getSubject(), mail.getContent(), "");

            emailContentService.saveEmailContent(emailContent);

            createDocByte(mail, emailContent.getClass().getName(), emailContent.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void sendHtmlEmil(Mail mail) throws Exception {

        logger.info("***************SEND HTML EMIL*****************");
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject(mail.getSubject());
            helper.setText(generateMailHtml(mail.getContent()), true);
            helper.setTo(mail.getTo());
            helper.setFrom(mail.getFrom());
            helper.setBcc(mail.getCc());
            if (mail.getAttachments() != null && mail.getAttachments().size() > 0) {
                for (FileSystemResource attachment : mail.getAttachments()) {
                    File file = attachment.getFile();
                    helper.addAttachment(file.getName(), file);
                }
            }

            emailSender.send(message);
            MailLog mailLog = new MailLog(mail.getFrom(), getToEmails(mail), getCCEmails(mail), mail.getSubject(), mail.getContent(), false, "");
            mailLogService.saveMailLog(mailLog);
            createDocByte(mail, mailLog.getClass().getName(), mailLog.getId());
        } catch (Exception e) {
            try {
                MailLog mailLog = new MailLog(mail.getFrom(), getToEmails(mail), getCCEmails(mail), mail.getSubject(), mail.getContent(), true, e.getMessage());
                mailLogService.saveMailLog(mailLog);
                createDocByte(mail, mailLog.getClass().getName(), mailLog.getId());
                e.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new Exception("We are unable to send email at the moment. Error Message: ".concat(e.getMessage()));

        }

    }


    public void sendEmailContent() {

        try {
            if (Boolean.valueOf(appConfigService.findByCode("ENABLE_EMAIL_SCHEDULER").get(0).getValue())) {
                logger.info("***************SENDING EMAILS*****************");
                List<EmailContent> emailContentList = emailContentService.findTop100OrderByIdDesc();

                List<EmailContent> sentEmailContentList = new ArrayList<>();
                logger.info("TOTAL EMAILS: ".concat(String.valueOf(emailContentList.size())));

                if (emailContentList != null && !emailContentList.isEmpty()) {
                    for (EmailContent emailContent : emailContentList) {
                        try {
                            sendHtmlEmil(buildMail(emailContent));
                            sentEmailContentList.add(emailContent);
                        } catch (Exception e) {
                            logger.info("Error when sending email with ID " + emailContent.getId() + ": ".concat(e.getMessage()));
                            emailContent.setErrorMessage("Error Message: ".concat(e.getMessage()));
                            emailContentService.saveEmailContent(emailContent);
                            e.printStackTrace();
                        }
                    }

                    emailContentService.deleteAllEmailContent(sentEmailContentList);

                } else {
                    logger.info("***************NO PENDING EMAILS AVAILABLE*****************");
                }
            } else {
                logger.info("***************EMAIL SCHEDULER NOT ENABLE*****************");
            }
        } catch (Exception e) {
            logger.info("Error when sending emails: ".concat(e.getMessage()));
            e.printStackTrace();
        }

    }

    private Mail buildMail(EmailContent emailContent) {
        Mail mail = new Mail();
        mail.setContent(emailContent.getContentMssg());
        mail.setFrom(emailContent.getFromEmail());
        mail.setTo(buildStringArray(emailContent.getToEmail()));
        mail.setSubject(emailContent.getSubject());
        mail.setCc(buildStringArray(emailContent.getCcEmails()));
        return mail;
    }

    private String[] buildStringArray(String value) {
        if (value != null) {
            return value.split(" ");
        } else {
            return null;
        }
    }


    public void sendWithBeanAttachement(Mail mail) {

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject(mail.getSubject());
            helper.setText(generateMailHtml(mail.getContent()), true);
            helper.setTo(mail.getTo());
            helper.setFrom(mail.getFrom());
            if (mail.getCc() != null) {
                helper.setBcc(mail.getCc());
            }


            if (mail.getAttachmentBeanList() != null && mail.getAttachmentBeanList().size() > 0) {
                for (AttachmentBean attachment : mail.getAttachmentBeanList()) {

                    helper.addAttachment(attachment.getFileName(), attachment.getInputStreamSource(), attachment.getContentType());
                }
            }

            emailSender.send(message);
            MailLog mailLog = new MailLog(mail.getFrom(), getToEmails(mail), getCCEmails(mail), mail.getSubject(), mail.getContent(), false, "");
            mailLogService.saveMailLog(mailLog);
            createDocByte(mail, mailLog.getClass().getName(), mailLog.getId());
        } catch (Exception e) {
            try {
                MailLog mailLog = new MailLog(mail.getFrom(), getToEmails(mail), getCCEmails(mail), mail.getSubject(), mail.getContent(), true, e.getMessage());
                mailLogService.saveMailLog(mailLog);
                createDocByte(mail, mailLog.getClass().getName(), mailLog.getId());
                e.printStackTrace();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }


    public void createDocByte(Mail mail, String targetClass, Long targetKey) throws Exception {
        if (mail != null && mail.getAttachmentBeanList() != null && mail.getAttachmentBeanList().size() > 0) {
            for (AttachmentBean doc : mail.getAttachmentBeanList()) {

                DocByte docByte = new DocByte();
                byte[] bocBytes = new byte[doc.getInputStreamSource().getInputStream().available()];
                docByte.setByteData(bocBytes);
                docByte.setContentType(doc.getContentType());
                docByte.setFileName(doc.getFileName());
                docByte.setTargetClass(targetClass);
                docByte.setTargetKey(targetKey);
                docByteService.saveDocByte(docByte);

            }
        }

        if (mail != null && mail.getAttachments() != null && mail.getAttachments().size() > 0) {
            for (FileSystemResource doc : mail.getAttachments()) {

                DocByte docByte = new DocByte();
                byte[] bocBytes = new byte[doc.getInputStream().available()];
                docByte.setByteData(bocBytes);
                docByte.setContentType("");
                docByte.setFileName(doc.getFilename());
                docByte.setTargetClass(targetClass);
                docByte.setTargetKey(targetKey);
                docByteService.saveDocByte(docByte);

            }
        }

    }

    public String generateMailHtml(String body) {
        final String templateFileName = "content";//Name of the HTML template file without extension

        String output = this.templateEngine.process(templateFileName, new Context(Locale.getDefault(), null));

        output = output.replace("#BODY#", body);
        return output;
    }

    public String getCCEmails(Mail mail) {
        String emails = "";
        if (mail != null && mail.getCc() != null && mail.getCc().length > 0) {
            for (String em : mail.getCc()) {
                emails += em + " ";
            }
        }
        return emails;
    }

    public String getToEmails(Mail mail) {
        String emails = "";
        if (mail != null && mail.getTo() != null && mail.getTo().length > 0) {
            for (String em : mail.getTo()) {
                emails += em + " ";
            }
        }
        return emails;
    }
}
