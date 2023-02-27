package com.pc.converter;

import com.pc.entities.lookup.EmailTemplate;
import com.pc.service.lookup.EmailTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Service
public class EmailTemplateConvertor implements Converter {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    EmailTemplateService emailTemplateService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uc, String value) {

        if (StringUtils.isBlank(value)) {
            return null;
        } else {
            try {

                return emailTemplateService.findById(Long.parseLong(value));
            } catch (NumberFormatException e) {
                logger.fatal(e);
            } catch (Exception e) {
                logger.fatal(e);
            }

        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uc, Object obj) {
        if (obj != null) {
            return "" + ((EmailTemplate) obj).getId();
        } else {
            return "";
        }
    }


}
