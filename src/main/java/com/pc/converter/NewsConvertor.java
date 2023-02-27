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

import com.pc.entities.lookup.News;
import com.pc.repositories.lookup.NewsRepository;
import com.pc.service.lookup.NewsService;

@Service
public class NewsConvertor implements Converter {
	
 	protected final Log logger = LogFactory.getLog(this.getClass());
	
 	@Autowired
 	NewsService newsService;
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uc, String value) {
		
		if (StringUtils.isBlank(value))
		{
			return null;
		} 
		else 
		{
			try{
				
				return  newsService.findById(Long.parseLong(value));
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
		  return ""+((News)obj).getId();
		}
		else
		{
			return "";
		}
	}


}
