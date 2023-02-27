package com.pc.service;

import com.pc.beans.Mail;
import com.pc.constants.AppConstants;
import com.pc.entities.ImageModel;
import com.pc.entities.User;
import com.pc.entities.UserRole;
import com.pc.entities.lookup.Gender;
import com.pc.entities.lookup.Title;
import com.pc.framework.AbstractService;
import com.pc.mail.MailSender;
import com.pc.repositories.UserRepository;
import com.pc.service.lookup.GenderService;
import com.pc.service.lookup.RoleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
public class UserService extends AbstractService {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private static final SimpleDateFormat sdfIdDate = new SimpleDateFormat("yyMMdd");
    @Autowired
    private UserRepository repository;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private ImagesService imagesService;
    @Autowired
    private GenderService genderService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveUser(User user) throws Exception {
        boolean isNew = false;
        if (user.getId() == null) {
            user.setDeleted(false);
            if (user.getPassword() == null) {
                user.setPassword(generatePassword());
            }
            isNew = true;
            String plainPass = user.getPassword();
            //Setting gender and date of bith
            user.setDob(sdfIdDate.parse(user.getRsaId().substring(0, 6)));
            if (Integer.parseInt(user.getRsaId().substring(6, 7)) > 4) {
                user.setGender(genderService.findByGenderName("Male"));
            } else {
                user.setGender(genderService.findByGenderName("Female"));
            }
            /*
             * Setting Default Image
             * The image with an ID of 0 must be added
             * in the ImageModel table
             */
            ImageModel defaultImg = imagesService.getById(0L);
            ImageModel img = new ImageModel();
            img.setName(defaultImg.getName());
            img.setPic(defaultImg.getPic());
            img.setType(defaultImg.getType());
            imagesService.save(img);
            user.setUsername(user.getEmail());
            user.setImage(img);
            if (user.getPassword() != null) {
                String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
            } else {
                String encodedPassword = bCryptPasswordEncoder.encode(user.getRsaId());
                user.setPassword(encodedPassword);
            }
            //Send login email notification
            try {
                registrationNotification(user, plainPass);
            } catch (Exception e) {
                logger.error("Sending Email Error: {}", e);
            }
            user.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                user.setLastUpdateUser(getCurrentUser());
            }
            user.setLastUpdateDate(new Date());
        }
        repository.save(user);
        if (isNew) {
            addGeneralUserRole(user);
        }
        user.setUsername(user.getEmail());


    }

    public void addGeneralUserRole(User user) throws Exception {
        UserRole userRole = new UserRole();
        userRole.setRole(roleService.findByCode("USER"));
        userRole.setUser(user);
        userRoleService.saveUserRole(userRole);
    }

    public void deleteUser(User user) throws Exception {
        user.setDeleted(true);
        repository.save(user);
    }

    public User changePassword(String email, String password, String newPassword) throws Exception {
        User user = repository.getUserByEmailAndDeleted(email, false);

        if (user == null) {
            throw new Exception("User with email address: " + email + " is not registered on the system! If you typed in the correct email please contact support.");
        } else {
            if (!bCryptPasswordEncoder.matches(password.trim(), user.getPassword().trim())) {
                throw new Exception("Invalid password for user id: " + email);
            }
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            repository.save(user);
        }
        return user;

    }

    public void resetPassword(User user, String newPassword) throws Exception {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        user.setChangePassword(false);
        repository.save(user);

    }

    public List<User> findAllUser() throws Exception {
        return repository.findByDeleted(false);
    }


    public User findByEmail(String email) throws Exception {
        return repository.findByEmailAndDeleted(email, false);
    }

    public User findByEmailAndRsaIdNot(String email, String rsaId) throws Exception {
        return repository.findByEmailAndDeletedAndRsaIdNot(email, false, rsaId);
    }

    public User getUserByRsaId(String idNumber) throws Exception {
        return repository.getUserByRsaIdAndDeleted(idNumber, false);
    }

    public User getUserByEmail(String email) throws Exception {
        return repository.getUserByEmailAndDeleted(email, false);
    }


    public void notifyUserNewPasswordEmail(String email) throws Exception {
        User ul = getUserByEmail(email);
        if (ul == null) {
            throw new Exception("User not registered");
        } else {
            notifyUserNewPassword(ul);
        }
    }


    /**
     * Notify a user of new password.
     *
     * @param u the u
     * @throws Exception the exception
     */
    public void notifyUserNewPassword(User u) throws Exception {
        //Generate password
        String pwd = generatePassword();
        u.setPassword(bCryptPasswordEncoder.encode(pwd));
        u.setChangePassword(true);
        repository.save(u);

        String welcome = "<p>Dear #NAME#,</p>" + "<br/>" + "<p>This is your new password: <b>" + pwd
                + "</b> for email: <b>" + u.getEmail() + "</b></p>"
                + "<p>You have to change it when you login.</p>"
                + "<p>Regards</p>"
                + "<p>Tertiary Verify Team</p>"
                + "<br/>";
        welcome = welcome.replaceAll("#NAME#", u.getName() + " " + u.getSurname());

        Mail mail = new Mail();

        mail.setContent(welcome);
        mail.setFrom(AppConstants.FROM_EMAIL);
        String[] to = {u.getEmail()};
        mail.setTo(to);
        mail.setSubject("Forgot Password");
        mail.setCc(to);
        mailSender.saveEmail(mail);
    }


    /**
     * Notify a user.
     */
    public void registrationNotification(User u, String plainPass) throws Exception {

        String welcome = "<p>Dear #NAME#,</p>"
                + "<p>Your Tertiary Verify account has been created <b>"
                + "<br/>"
                + "<p>This is your password: <b>" + plainPass
                + "<b> for email: " + u.getEmail() + "</b></p>"
                + "<p>Regards</p>"
                + "<p>The Tertiary Verify Team</p>"
                + "<br/>";
        welcome = welcome.replaceAll("#NAME#", u.getName() + " " + u.getSurname());

        Mail mail = new Mail();

        mail.setContent(welcome);
        mail.setFrom(AppConstants.FROM_EMAIL);
        String[] to = {u.getEmail()};
        mail.setTo(to);
        mail.setSubject("Tertiary Verify Registration");
        mail.setCc(to);
        mailSender.saveEmail(mail);
    }


    public String generatePassword() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            Random rand = new Random();
            int n = rand.nextInt(15) + 1;
            if (n > 10) {
                buffer.append(Character.toUpperCase((char) randomLimitedInt));
            } else {
                buffer.append((char) randomLimitedInt);
            }
        }
        String generatedString = buffer.toString();
        generatedString = generatedString.trim();
        System.out.println(generatedString);
        return generatedString;
    }

    public void saveProfileImage(User currentUser, FileUploadEvent event) throws Exception {

        if (currentUser.getImage() == null) {
            ImageModel imageModel = new ImageModel();
            imageModel.setName(event.getFile().getFileName().trim());
            imageModel.setType(event.getFile().getContentType());
            imageModel.setPic(event.getFile().getContents());
            imageModel = imagesService.save(imageModel);
            currentUser.setImage(imageModel);
            saveUser(currentUser);

        } else {
            ImageModel newImg = currentUser.getImage();
            newImg.setName(event.getFile().getFileName().trim());
            newImg.setType(event.getFile().getContentType());
            newImg.setPic(event.getFile().getContents());
            imagesService.save(newImg);
        }

    }

    public long countByTitle(Title title) {
        return repository.countByTitleAndDeleted(title, false);
    }

    public long countByGender(Gender gender) {
        return repository.countByGenderAndDeleted(gender, false);
    }

}
