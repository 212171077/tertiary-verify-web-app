package com.pc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.pc.entities.InstCourseAddress;
import com.pc.repositories.InstCourseAddressRepository;
import com.pc.service.InstCourseAddressService;

@Service
public class InstCourseAddressConvertor implements Converter {
	
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
 	@Autowired
 	InstCourseAddressService instCourseAddressService;
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uc, String value) {
		
		if (StringUtils.isBlank(value))
		{
			return null;
		} 
		else 
		{
			try{
				
				return  instCourseAddressService.findById(Long.parseLong(value));
			} 
			catch (NumberFormatException e){
				logger.fatal(e);
			} 
			catch (Exception e){
				logger.fatal(e);
			}

		}
	    return null;
	}
	@Override
	public String getAsString(FacesContext fc, UIComponent uc, Object obj) 
	{
		if(obj !=null)
		{
		  return ""+((InstCourseAddress)obj).getId();
		}
		else
		{
			return "";
		}
	}


}
