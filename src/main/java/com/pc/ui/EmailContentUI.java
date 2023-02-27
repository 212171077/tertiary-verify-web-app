package com.pc.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import com.pc.dao.EntityDAOFacade;

import com.pc.entities.EmailContent;
import com.pc.framework.AbstractUI;
import com.pc.service.EmailContentService;

@Component("emailContentUI")
@ViewScoped
public class EmailContentUI extends AbstractUI{

	@Autowired
	EmailContentService emailContentService;
	private ArrayList<EmailContent> emailContentList;
	private EmailContent emailContent;
	private LazyDataModel<EmailContent> dataModel;
	@Autowired
	EntityDAOFacade entityDAOFacade;

	@PostConstruct
	public void init() {
		emailContent = new EmailContent();
		loadEmailContentInfo();
	}

	public void saveEmailContent()
	{
		try {
			emailContentService.saveEmailContent(emailContent);
			displayInfoMssg("Update Successful...!!");
			loadEmailContentInfo();
			reset();
		} catch (Exception e) {
			displayErrorMssg(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void deleteEmailContent()
	{
		try {
			emailContentService.deleteEmailContent(emailContent);
			displayWarningMssg("Update Successful...!!");
			loadEmailContentInfo();
			reset();
		} catch (Exception e) {
			displayErrorMssg(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<EmailContent> findAllEmailContent()
	{
		List<EmailContent> list=new ArrayList<>();
		try {
			list= emailContentService.findAllEmailContent();
		} catch (Exception e) {
			displayErrorMssg(e.getMessage());
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Page<EmailContent> findAllEmailContentPageable()
	{
		Pageable p=null;
		try {
			return emailContentService.findAllEmailContent(p);
		} catch (Exception e) {
			displayErrorMssg(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<EmailContent> findAllEmailContentSort()
	{
		Sort s=null;
		List<EmailContent> list=new ArrayList<>();
		try {
			list =emailContentService.findAllEmailContent(s);
		} catch (Exception e) {
			displayErrorMssg(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	public void reset() {
		emailContent = new EmailContent();
	}
	
	public void loadEmailContentInfo()
	{
		 dataModel = new LazyDataModel<EmailContent>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<EmailContent> list = new  ArrayList<EmailContent>();
			   
			   @Override 
			   public List<EmailContent> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					list = (List<EmailContent>) entityDAOFacade.getResultList(EmailContent.class,first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(entityDAOFacade.count(filters,EmailContent.class));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return list; 
			   }
			   
			    @Override
			    public Object getRowKey(EmailContent obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public EmailContent getRowData(String rowKey) {
			        for(EmailContent obj : list) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
	}
	public EmailContent getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(EmailContent emailContent) {
		this.emailContent = emailContent;
	}
	
	public ArrayList<EmailContent> getEmailContentList() {
		return emailContentList;
	}

	public void setEmailContentList(ArrayList<EmailContent> emailContentList) {
		this.emailContentList =emailContentList;
	}
	public LazyDataModel<EmailContent> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<EmailContent> dataModel) {
		this.dataModel = dataModel;
	}
	

}
