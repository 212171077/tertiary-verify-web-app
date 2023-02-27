package com.pc.service;

import com.pc.beans.Mail;
import com.pc.beans.ReportedInstitutionBean;
import com.pc.entities.*;
import com.pc.entities.enums.ReportedInstitutionsStatus;
import com.pc.entities.lookup.Province;
import com.pc.entities.lookup.Role;
import com.pc.framework.AbstractService;
import com.pc.mail.MailSender;
import com.pc.repositories.ReportedInstitutionsRepository;
import com.pc.service.lookup.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportedInstitutionsService extends AbstractService {
    @Autowired
    ReportedInstitutionsRepository repository;

    @Autowired
    ReportedInstitutionCourseService reportedInstitutionCourseService;

    @Autowired
    AddressService addressService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    MailSender mailSender;


    public void saveReportedInstitutions(ReportedInstitutions reportedInstitutions) throws Exception {
        if (reportedInstitutions.getId() == null) {
            reportedInstitutions.setDeleted(false);
            reportedInstitutions.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                reportedInstitutions.setLastUpdateUser(getCurrentUser());
            }
            reportedInstitutions.setLastUpdateDate(new Date());
        }
        repository.save(reportedInstitutions);
    }

    public List<ReportedInstitutionBean> getReportedInstitutionBean(Province province, Integer year, Integer mon) throws Exception {
        if (province != null && year != null && mon != null) {
            return repository.getReportedInstitutionBean(province, year, mon);
        } else if (province != null && year != null) {
            return repository.getReportedInstitutionBean(province, year);
        } else if (province != null && mon != null) {
            return repository.getReportedInstitutionBeanByMonAndProvince(province, mon);
        } else if (year != null && mon != null) {
            return repository.getReportedInstitutionBean(year, mon);
        } else if (year != null) {
            return repository.getReportedInstitutionBean(year);
        } else if (mon != null) {
            return repository.getReportedInstitutionBean(mon);
        } else {
            return repository.getReportedInstitutionBean();
        }

    }

    public void submitReportedInstitutions(ReportedInstitutions reportedInstitutions, Address address, ArrayList<ReportedInstitutionCourse> reportedInstitutionCourseList) throws Exception {
        if (reportedInstitutions.getId() == null) {
            reportedInstitutions.setStatus(ReportedInstitutionsStatus.Submitted);
            reportedInstitutions.setCreateDate(new Date());
            User investigator = getInvestigator();
            reportedInstitutions.setInvestigator(investigator);
            reportedInstitutions.setDeleted(false);
            if (investigator != null) {
                sendEmailNotificationToInvestigator(investigator);
            }

        }
        if (address != null) {
            addressService.saveAddress(address);
            reportedInstitutions.setAddress(address);
        }
        repository.save(reportedInstitutions);
        reportedInstitutions.setRefNumber(getRefNumber() + "" + reportedInstitutions.getId());
        //repository.save(reportedInstitutions);
        if (reportedInstitutionCourseList != null && reportedInstitutionCourseList.size() > 0) {
            for (ReportedInstitutionCourse c : reportedInstitutionCourseList) {
                c.setReportedInstitution(reportedInstitutions);
                reportedInstitutionCourseService.saveReportedInstitutionCourse(c);
            }
        }

        sendRefeNumberToReport(reportedInstitutions);
    }

    private String getRefNumber() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        long millis = System.currentTimeMillis();
        String generatedString = buffer.toString().toUpperCase() + "" + String.valueOf(millis).substring(0, 2);
        return generatedString;
    }

    public void deleteReportedInstitutions(ReportedInstitutions reportedInstitutions) throws Exception {
        repository.delete(reportedInstitutions);
    }

    public void deleteReportedInstitutionsByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<ReportedInstitutions> findAllReportedInstitutions() throws Exception {
        return repository.findAll();
    }

    public Page<ReportedInstitutions> findAllReportedInstitutions(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<ReportedInstitutions> findAllReportedInstitutions(Sort s) throws Exception {
        return repository.findAll(s);
    }


    public Optional<ReportedInstitutions> findReportedInstitutionsById(Integer arg0) throws Exception {
        return repository.findById(arg0);
    }

    public ReportedInstitutions findByRefNumber(String refNumber) throws Exception {
        return repository.findByRefNumber(refNumber);
    }

    public List<ReportedInstitutions> findByInvestigatorAndStatusNot(User investigator, ReportedInstitutionsStatus status) throws Exception {
        return repository.findByInvestigatorAndStatusNot(investigator, status);
    }

    public User getInvestigator() throws Exception {
        User user = null;
        Role investigatoRole = roleService.findByCode("INV");
        if (investigatoRole != null) {
            List<UserRole> userRoleList = userRoleService.findByRole(investigatoRole);

            if (userRoleList != null && userRoleList.size() > 0) {
                Long count = repository.countByInvestigator(userRoleList.get(0).getUser());
                user = userRoleList.get(0).getUser();
                userRoleList.remove(userRoleList.get(0));
                for (UserRole ur : userRoleList) {
                    Long tempCount = repository.countByInvestigator(ur.getUser());
                    if (tempCount < count) {
                        count = tempCount;
                        user = ur.getUser();
                    }
                }
            }

        }
        return user;
    }

    private void sendEmailNotificationToInvestigator(User u) {

        try {
            String welcome = "<p>Dear #NAME#,</p>" + "<br/>" +
                    "<p>There is a new institution report that has been assign to you, please login and action the report <b>"
                    + "<p>Regards</p>"
                    + "<p>Tertiary Verify Team</p>"
                    + "<br/>";
            welcome = welcome.replaceAll("#NAME#", u.getName() + " " + u.getSurname());
            Mail mail = new Mail();
            mail.setContent(welcome);
            mail.setFrom("info@tertiaryverify.co.za");
            String[] to = {u.getEmail()};
            mail.setTo(to);
            mail.setSubject("Institution Report");
            mail.setCc(to);
            mailSender.saveEmail(mail);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void sendRefeNumberToReport(ReportedInstitutions reportedInstitution) {

        try {
            String welcome = "<p>Dear #NAME#,</p>" + "<br/>" +
                    "<p>An Investigator will be processing your report</p>"
                    + "<p>Your Reference number is :" + reportedInstitution.getRefNumber() + "<p>"
                    + "<p>Regards</p>"
                    + "<p>Tertiary Verify Team</p>"
                    + "<br/>";
            welcome = welcome.replaceAll("#NAME#", reportedInstitution.getReporterName() + " " + reportedInstitution.getReporterSurname());
            Mail mail = new Mail();
            mail.setContent(welcome);
            mail.setFrom("info@tertiaryverify.co.za");
            String[] to = {reportedInstitution.getReporterEmail()};
            mail.setTo(to);
            mail.setSubject("Institution Report");
            mail.setCc(to);
            mailSender.saveEmail(mail);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public long countAll() throws Exception {
        return repository.count();
    }

    public long countByStatus(ReportedInstitutionsStatus status) {
        return repository.countByStatus(status);
    }


}
