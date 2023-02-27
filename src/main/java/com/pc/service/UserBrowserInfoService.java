package com.pc.service;

import com.pc.entities.User;
import com.pc.entities.UserBrowserInfo;
import com.pc.framework.AbstractService;
import com.pc.repositories.UserBrowserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class UserBrowserInfoService extends AbstractService {
    @Autowired
    UserBrowserInfoRepository repository;

    public void saveUserBrowserInfo(UserBrowserInfo userBrowserInfo) throws Exception {
        if (userBrowserInfo.getId() == null) {
            userBrowserInfo.setCreateDate(new Date());
        } else {
            if (getCurrentUser() != null) {
                userBrowserInfo.setLastUpdateUser(getCurrentUser());
            }
            userBrowserInfo.setLastUpdateDate(new Date());
        }
        repository.save(userBrowserInfo);
    }

    public void deleteUserBrowserInfo(UserBrowserInfo userBrowserInfo) throws Exception {
        repository.delete(userBrowserInfo);
    }

    public void deleteUserBrowserInfoByID(Integer arg0) throws Exception {
        repository.deleteById(arg0);
    }

    public List<UserBrowserInfo> findAllUserBrowserInfo() throws Exception {
        return repository.findAll();
    }

    public Page<UserBrowserInfo> findAllUserBrowserInfo(Pageable p) throws Exception {
        return repository.findAll(p);
    }

    public List<UserBrowserInfo> findAllUserBrowserInfo(Sort s) throws Exception {
        return repository.findAll(s);
    }

    public UserBrowserInfo findById(Long parseLong) throws Exception {
        return repository.findById(parseLong);
    }


    public void storeUserBrowserInfor(HttpServletRequest request, User user) throws Exception {
        UserBrowserInfo userBrowserInfo = new UserBrowserInfo();
        /*Referer**/
        String referer = request.getHeader("referer");
        /*Full URL*/
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        String fullURL = queryString == null ? requestURL.toString() : requestURL.append('?').append(queryString).toString();

        userBrowserInfo.setReferer(referer);
        userBrowserInfo.setBrowserName(getClientBrowser(request));
        userBrowserInfo.setFullURL(fullURL);
        userBrowserInfo.setIpAddress(getClientIpAddress(request));
        userBrowserInfo.setOperatingSystem(getClientOS(request));
        userBrowserInfo.setUserAgent(getUserAgent(request));
        userBrowserInfo.setUser(user);
        getLatLong(request, userBrowserInfo);//To be Fixed
        saveUserBrowserInfo(userBrowserInfo);

    }

    //Reference: http://stackoverflow.com/a/18030465/1845894
    public String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public String getClientOS(HttpServletRequest request) {
        final String browserDetails = request.getHeader("User-Agent");
        final String lowerCaseBrowser = browserDetails.toLowerCase();
        if (lowerCaseBrowser.contains("windows")) {
            return "Windows";
        } else if (lowerCaseBrowser.contains("mac")) {
            return "Mac";
        } else if (lowerCaseBrowser.contains("x11")) {
            return "Unix";
        } else if (lowerCaseBrowser.contains("android")) {
            return "Android";
        } else if (lowerCaseBrowser.contains("iphone")) {
            return "IPhone";
        } else {
            return "UnKnown, More-Info: " + browserDetails;
        }
    }

    public String getClientBrowser(HttpServletRequest request) {
        final String browserDetails = request.getHeader("User-Agent");
        final String user = browserDetails.toLowerCase();

        String browser = "";
        if (user.contains("msie")) {
            String substring = browserDetails.substring(browserDetails.indexOf("MSIE")).split(";")[0];
            browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version")) {
            browser = (browserDetails.substring(browserDetails.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-" + (browserDetails.substring(browserDetails.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if (user.contains("opr") || user.contains("opera")) {
            if (user.contains("opera"))
                browser = (browserDetails.substring(browserDetails.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-" + (browserDetails.substring(browserDetails.indexOf("Version")).split(" ")[0]).split("/")[1];
            else if (user.contains("opr"))
                browser = ((browserDetails.substring(browserDetails.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
        } else if (user.contains("chrome")) {
            browser = (browserDetails.substring(browserDetails.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1) || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {

            browser = "Netscape-?";

        } else if (user.contains("firefox")) {
            browser = (browserDetails.substring(browserDetails.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("rv")) {
            browser = "IE";
        } else {
            browser = "UnKnown, More-Info: " + browserDetails;
        }

        return browser;
    }

    public String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public void getLatLong(HttpServletRequest request, UserBrowserInfo userBrowserInfo) {
        HttpSession session = request.getSession();
    }


}
