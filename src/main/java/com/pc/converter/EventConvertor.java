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

import com.pc.entities.lookup.Event;
import com.pc.repositories.lookup.EventRepository;
import com.pc.service.lookup.EventService;

@Service
public class EventConvertor implements Converter {
	
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
 	@Autowired
 	EventService eventService;
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uc, String value) {
		
		if (StringUtils.isBlank(value))
		{
			return null;
		} 
		else 
		{
			try{
				
				return  eventService.findById(Long.parseLong(value));
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
		  return ""+((Event)obj).getId();
		}
		else
		{
			return "";
		}
	}


}
