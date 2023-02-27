package com.pc.framework;

import com.pc.entities.User;
import com.pc.service.UserBrowserInfoService;
import com.pc.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Logger;


public abstract class AbstractUI implements Serializable {
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected User currentUser;
    @Autowired
    UserService userService;
    @Autowired
    UserBrowserInfoService userBrowserInfoService;

    @PostConstruct
    public void init() {

        try {
            prepareCurrentUser();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
    }

    public void prepareCurrentUser() throws Exception {
        if (currentUser == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getName() != null) {
                currentUser = userService.findByEmail(authentication.getName());
                if (currentUser != null && currentUser.getChangePassword() != null && currentUser.getChangePassword() == true) {
                    ajaxRedirect("/changepassword.xhtml");
                }
            }

            //storeClientInfo();
        }

    }

    public boolean hasRole(String role) {
        boolean has = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authenticationList = authentication.getAuthorities();
        for (GrantedAuthority auth : authenticationList) {
            if (auth.getAuthority().equalsIgnoreCase("ROLE_" + role.toUpperCase())) {
                has = true;
                break;
            }
        }
        return has;
    }

    public void prepareCurrentUserNoChangePassCheck() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        currentUser = userService.findByEmail(authentication.getName());
    }

    public void displayInfoMssg(String mssg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mssg));
    }

    public void displaySuccessMssg(String mssg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", mssg));
    }

    public void displayWarningMssg(String mssg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", mssg));
    }

    public void displayFatalMssg(String mssg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal", mssg));
    }

    public void displayErrorMssg(String mssg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", mssg));
    }

    /**
     * Redirect to specified page.
     *
     * @param outcome the outcome
     */
    protected void ajaxRedirect(String outcome) {
        FacesContext ctx = getContext();
        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, outcome));
        try {
            extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);
        }
    }

    /**
     * Return the FacexContext.
     *
     * @return the context
     */
    protected FacesContext getContext() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null) context = OfflineContext.getCurrentInstance();
        return context;
    }

    /**
     * Retrieve the current value of parameter.
     *
     * @param key       Key of parameter to retrieve.
     * @param inSession Return parameter from HttpSession if true, else from ServletRequest
     * @return the parameter
     */
    protected Object getParameter(String key, boolean inSession) {
        FacesContext facesContext = getContext();
        if (facesContext != null && facesContext.getExternalContext() != null) {
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            if (inSession) return request.getSession().getAttribute(key);
            else return request.getParameter(key);
        }
        return null;
    }

    public void storeClientInfo() {
        try {
            FacesContext facesContext = getContext();
            if (facesContext != null && facesContext.getExternalContext() != null) {
                HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
                userBrowserInfoService.storeUserBrowserInfor(request, currentUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Logger getLog(Class<?> theClass) {
        return Logger.getLogger(theClass.getName());
    }

    public void scrollTo(String to) {
        PrimeFaces.current().scrollTo(to);
    }

}
