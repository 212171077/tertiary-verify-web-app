package com.pc.ui;

import com.pc.dao.EntityDAOFacade;
import com.pc.entities.User;
import com.pc.framework.AbstractUI;
import com.pc.service.UserRoleService;
import com.pc.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
@ManagedBean(name = "userUI")
@ViewScoped
public class UserUI extends AbstractUI {

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    EntityDAOFacade myObjFacade;
    private LazyDataModel<User> dataModel;
    private User user;
    private StreamedContent profileImg;
    private ArrayList<User> userList;
    private boolean registeredSuccessful;

    /**
     * Check rsa id.
     *
     * @param idVal the id val
     * @return true, if successful
     */
    public static boolean checkRsaId(String idVal) {
        if (idVal == null || (idVal != null && idVal.trim().length() == 0)) return true;
        idVal = idVal.trim();
        if (idVal.length() < 13) return false;
        int checkDigit = ((Integer.valueOf("" + (idVal.charAt(idVal.length() - 1)))).intValue());
        String odd = "0";
        String even = "";
        int evenResult = 0;
        int result;
        for (int c = 1; c <= idVal.length(); c++) {
            if (c % 2 == 0) {
                even += idVal.charAt(c - 1);
            } else {
                if (c == idVal.length()) {
                    continue;
                } else {
                    odd = "" + (Integer.valueOf("" + odd).intValue() + Integer.valueOf("" + (idVal.charAt(c - 1))));
                }
            }
        }
        String evenS = "" + (Integer.valueOf(even) * 2);
        for (int r = 1; r <= evenS.length(); r++) {
            evenResult += Integer.valueOf("" + evenS.charAt(r - 1));
        }
        result = (Integer.valueOf(odd) + Integer.valueOf(evenResult));
        String resultS = "" + result;
        resultS = "" + (10 - (Integer.valueOf("" + (resultS.charAt(resultS.length() - 1)))).intValue());
        if (resultS.length() > 1) {
            resultS = "" + resultS.charAt(resultS.length() - 1);
        }
        return Integer.valueOf(resultS) == checkDigit;
    }

    public static void checkPassword(String newPassword, User u) throws Exception {
        if (newPassword != null) {
            if (newPassword.equals(u.getName()) || newPassword.equals(u.getSurname()))
                throw new Exception("Password cannot be your Firstname or Surname.");

            if (newPassword.length() < 8 || newPassword.length() < 8)
                throw new Exception("Password must be 8 characters long.");

            String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}";
            // Create a Pattern object
            Pattern r = Pattern.compile(regex);
            Matcher m = r.matcher(newPassword);
            if (!m.find())
                throw new Exception("Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character");
        }
    }

    @PostConstruct
    public void init() {


        try {
            super.prepareCurrentUser();
            registeredSuccessful = false;
            user = new User();
            allUsers();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void allUsers() {
        dataModel = new LazyDataModel<User>() {

            private static final long serialVersionUID = 1L;
            private List<User> list = new ArrayList<User>();

            @Override
            public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                try {
                    list = (List<User>) myObjFacade.getResultList(User.class, first, pageSize, sortField, sortOrder, filters);
                    dataModel.setRowCount(myObjFacade.count(filters, User.class));
                } catch (Exception e) {
                    logger.fatal(e);
                }
                return list;
            }

            @Override
            public Object getRowKey(User obj) {
                return obj.getId();
            }

            @Override
            public User getRowData(String rowKey) {
                for (User obj : list) {
                    if (obj.getId().equals(Long.valueOf(rowKey)))
                        return obj;
                }
                return null;
            }

        };

    }

    public void saveUser() {
        try {
            userService.saveUser(user);
            displayInfoMssg("User registered successful..!!");
            registeredSuccessful = true;
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void registeUser() {
        try {
            if (!checkRsaId(user.getRsaId())) {
                throw new Exception("Invalid RSA ID number");
            } else if (userService.findByEmail(user.getEmail()) != null) {
                throw new Exception("Email Address already exist, please provide a unique email");
            } else if (userService.getUserByRsaId(user.getRsaId()) != null) {
                throw new Exception("ID number already exist, please provide a unique ID number");
            } else {
                checkPassword(user.getPassword(), user);
                userService.saveUser(user);
                displayInfoMssg("User registered successful..!!");
                reset();
            }

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }


    public void deleteUser() {
        try {
            userService.deleteUser(user);
            displayInfoMssg("User deleted Successful..!!");
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<User> findAllUser() {
        List<User> list = new ArrayList<>();
        try {
            list = userService.findAllUser();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }


    public void reset() {
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void handleFileUpload(FileUploadEvent event) {
        profileImg = new DefaultStreamedContent(new ByteArrayInputStream(event.getFile().getContents()), "png", event.getFile().getFileName().trim());

        displayInfoMssg("upload completed...!!! Content Type: " + event.getFile().getContentType() + " File name: " + event.getFile().getFileName());

    }

    public StreamedContent getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(StreamedContent profileImg) {
        this.profileImg = profileImg;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public boolean isRegisteredSuccessful() {
        return registeredSuccessful;
    }

    public void setRegisteredSuccessful(boolean registeredSuccessful) {
        this.registeredSuccessful = registeredSuccessful;
    }

    public LazyDataModel<User> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<User> dataModel) {
        this.dataModel = dataModel;
    }


}
