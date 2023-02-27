package com.pc.service;

import com.pc.beans.Mail;
import com.pc.entities.*;
import com.pc.entities.enums.ApprovalStatusEnum;
import com.pc.entities.lookup.*;
import com.pc.framework.AbstractService;
import com.pc.mail.MailSender;
import com.pc.model.AllInstitutionDTO;
import com.pc.repositories.InstitutionRepository;
import com.pc.service.lookup.RoleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InstitutionService extends AbstractService {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private InstitutionRepository repository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private InstitutionCourseService institutionCourseService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private InstCourseAddressService instCourseAddressService;


    public void saveInstitution(Institution institution) throws Exception {
        if (institution.getId() == null) {
            institution.setDeleted(false);
            institution.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                institution.setLastUpdateUser(getCurrentUser());
            }
            institution.setLastUpdateDate(new Date());
        }
        repository.save(institution);
    }

    public void deleteInstitution(Institution institution) throws Exception {
        institution.setDeleted(true);
        repository.save(institution);
    }

    public List<Institution> findAllInstitution() throws Exception {
        return repository.findByDeleted(false);
    }


    public Institution findInstitutionById(Integer arg0) throws Exception {
        return repository.findByIdAndDeleted(Long.valueOf(arg0), false);
    }

    public List<AllInstitutionDTO> findAllInstitutionDTO() throws Exception {
        return repository.findAllInstitutionDTO(false);
    }

    public void submitIntDetails(Institution institution, List<Address> addressList, User user, ArrayList<Course> courseList) throws Exception {

        boolean newRegistration = institution.getId() == null;
        if (institution.getId() != null) {
            ArrayList<InstitutionCourse> institutionCourseList = (ArrayList<InstitutionCourse>) institutionCourseService.findByInstitution(institution);
            if (institutionCourseList != null && institutionCourseList.size() > 0) {
                institutionCourseService.deleteAll(institutionCourseList);
            }

        }
        User supervisor = null;
        boolean sendInstUpdatedEmail = false;
        if (newRegistration) {
            institution.setStatus(ApprovalStatusEnum.PendingApproval);
            institution.setCreatorUser(user);
            supervisor = getSupervisor();
            institution.setSupervisor(supervisor);
        } else {
            if ((institution.getStatus() == ApprovalStatusEnum.EndDateExpired ||
                    institution.getStatus() == ApprovalStatusEnum.Expired) &&
                    institution.getAccreditationEndDate().after(new Date())) {
                institution.setStatus(ApprovalStatusEnum.Approved);
            } else if (institution.getStatus() == ApprovalStatusEnum.Rejected) {
                institution.setStatus(ApprovalStatusEnum.PendingApproval);
                sendInstUpdatedEmail = true;
            }
        }

        ArrayList<InstitutionCourse> institutionCourseList = new ArrayList<>();
        ArrayList<InstCourseAddress> instCourseAddressList = new ArrayList<>();
        for (Course course : courseList) {
            InstitutionCourse institutionCourse = new InstitutionCourse();
            institutionCourse.setCreatorUser(user);
            institutionCourse.setInstitution(institution);
            institutionCourse.setCourse(course);
            institutionCourse.setCreateDate(new Date());
            institutionCourseList.add(institutionCourse);

            if (course.getAddressList() != null && !course.getAddressList().isEmpty()) {
                for (Address address : course.getAddressList()) {
                    InstCourseAddress instCourseAddress = new InstCourseAddress();
                    instCourseAddress.setAddress(address);
                    instCourseAddress.setInstitutionCourse(institutionCourse);
                    instCourseAddress.setCreateDate(new Date());
                    instCourseAddress.setLastUpdateUser(user);
                    instCourseAddressList.add(instCourseAddress);
                }
            }
        }
        saveInstitution(institution);
        for (Address address : addressList) {
            address.setInstitution(institution);
            addressService.saveAddress(address);
        }

        institutionCourseService.saveAll(institutionCourseList);
        instCourseAddressService.saveAll(instCourseAddressList);

        if (supervisor != null) {
            String mssg = "There is a new institution registered that has been assign to you, please login and action the task.";
            sendEmailNotificationToSupervisor(supervisor, mssg);
        }

        if (sendInstUpdatedEmail) {
            String mssg = "There are new updates on " + institution.getInstitutionName() + ", please login and action the task.";
            sendEmailNotificationToSupervisor(supervisor, mssg);
        }

    }

    public List<Institution> searchInstitution(String searchTerm, Province province) throws Exception {
        List<Institution> list = repository.getByDeletedAndInstitutionNameContainingOrderByInstitutionNameDesc(false, searchTerm);
        List<Institution> returnList = new ArrayList<Institution>();
        if (list != null && list.size() > 0) {
            for (Institution i : list) {
                for (Address address : i.getAddressList()) {
                    if (address.getProvince() == province) {
                        returnList.add(i);
                        break;
                    }
                }
            }
        }
        return returnList;
    }

    public List<Institution> findByAccreditationNumber(String searchTerm) throws Exception {
        return repository.findByAccreditationNumberAndDeleted(searchTerm, false);
    }


    public User getSupervisor() throws Exception {
        User user = null;
        Role supervisorRole = roleService.findByCode("SPVS");
        if (supervisorRole != null) {
            List<UserRole> userRoleList = userRoleService.findByRole(supervisorRole);
            if (userRoleList != null && userRoleList.size() > 0) {
                Long count = repository.countBySupervisorAndDeleted(userRoleList.get(0).getUser(), false);
                user = userRoleList.get(0).getUser();
                userRoleList.remove(userRoleList.get(0));
                for (UserRole ur : userRoleList) {
                    Long tempCount = repository.countBySupervisorAndDeleted(ur.getUser(), false);
                    if (tempCount < count) {
                        count = tempCount;
                        user = ur.getUser();
                    }
                }
            }
        }
        if (user == null) {
            throw new Exception("No supervisor to approve this institution details found");
        }
        return user;
    }

    private void sendEmailNotificationToSupervisor(User u, String mssg) {

        try {
            String welcome = "<p>Dear #NAME#,</p>" + "<br/>" +
                    "<p>" + mssg + "<b>"
                    + "<p>Regards</p>"
                    + "<p>Tertiary Verify Team</p>"
                    + "<br/>";
            welcome = welcome.replaceAll("#NAME#", u.getName() + " " + u.getSurname());
            Mail mail = new Mail();
            mail.setContent(welcome);
            mail.setFrom("info@tertiaryverify.co.za");
            String[] to = {u.getEmail()};
            mail.setTo(to);
            mail.setSubject("Institution Registration");
            mail.setCc(to);
            mailSender.saveEmail(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendApprovedInstitutionEmail(User u, String instName) {

        try {
            String welcome = "<p>Dear #NAME#,</p>" + "<br/>" +
                    "<p>Be informed that that " + instName + " has been approved on Tertiary Verify<b>"
                    + "<p>Regards</p>"
                    + "<p>Tertiary Verify Team</p>"
                    + "<br/>";
            welcome = welcome.replaceAll("#NAME#", u.getName() + " " + u.getSurname());
            Mail mail = new Mail();
            mail.setContent(welcome);
            mail.setFrom("info@tertiaryverify.co.za");
            String[] to = {u.getEmail()};
            mail.setTo(to);
            mail.setSubject("Institution Approved");
            mail.setCc(to);
            mailSender.saveEmail(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRejectedInstitutionEmail(User u, String instName, List<RejectReason> rejectReasons) {
        try {
            String rejectReason = "<dl>";
            for (RejectReason reason : rejectReasons) {
                rejectReason += "<dt><b>" + reason.getDescription() + "</b></dt>" +
                        "<dt>" + reason.getDetailInfo() + "</dt>";
            }
            rejectReason += "</dl>";

            String welcome = "<p>Dear #NAME#,</p>" + "<br/>" +
                    "<p>Be informed that " + instName + " has been rejected on Tertiary Verify <b>"
                    + "<p><b>Reject Reason(s)</b></p>"
                    + rejectReason
                    + "<p>Regards</p>"
                    + "<p>Tertiary Verify Team</p>"
                    + "<br/>";
            welcome = welcome.replaceAll("#NAME#", u.getName() + " " + u.getSurname());
            Mail mail = new Mail();
            mail.setContent(welcome);
            mail.setFrom("info@tertiaryverify.co.za");
            String[] to = {u.getEmail()};
            mail.setTo(to);
            mail.setSubject("Institution Rejected");
            mail.setCc(to);
            mailSender.saveEmail(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * should the date of the Accreditation
     * end date pass without a renewal...
     * the Institution must lose its
     * accreditation. Meaning if accreditation
     * end date is 17 January 2019, by midnight
     * of that end date...it not be accredited.
     * It must not be found on 18 January 2019
     */
    public void updateExpiredIntitution() {
        try {
            List<Institution> list = repository.findByDeletedAndAccreditationEndDateLessThan(false, new Date());
            if (list != null && list.size() > 0) {
                for (Institution institution : list) {
                    institution.setStatusUpdatedByScheduler(true);
                    institution.setStatus(ApprovalStatusEnum.EndDateExpired);
                    saveInstitution(institution);
                }
            }
        } catch (Exception e) {
            logger.error("Error", e);
        }
    }

    public Institution findInstitutionByQRCode(String searchTerm) throws Exception {
        return repository.findInstitutionByQRCodeAndDeleted(searchTerm, false);
    }

    public Institution findInstitutionById(Long id) throws Exception {
        return repository.findInstitutionByIdAndDeleted(id, false);
    }

    public List<Institution> findInstitutionByName(String name) throws Exception {
        return repository.findInstitutionByNameAndDeleted(name, false);
    }

    public List<Institution> searchByInstitutionName(String institutionName, ApprovalStatusEnum status) throws Exception {
        return repository.searchByInstitutionNameAndDeleted(institutionName, status, false);
    }

    public long countByInstitutionName(String institutionName, ApprovalStatusEnum status) throws Exception {
        return repository.countByInstitutionNameAndDeleted(institutionName, status, false);
    }

    public long countAll() throws Exception {
        return repository.count();
    }

    public Institution findById(Long id) {
        return repository.findByIdAndDeleted(id, false);
    }


    public long countByInstitutionType(InstitutionType institutionType) {
        return repository.countByInstitutionTypeAndDeleted(institutionType, false);
    }
}
