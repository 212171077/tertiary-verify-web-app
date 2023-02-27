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

import com.pc.entities.MailLog;
import com.pc.framework.AbstractUI;
import com.pc.service.MailLogService;

@Component("mailLogUI")
@ViewScoped
public class MailLogUI extends AbstractUI{

	@Autowired
	MailLogService mailLogService;
	private ArrayList<MailLog> mailLogList;
	private MailLog mailLog;
	private LazyDataModel<MailLog> dataModel;
	@Autowired
	EntityDAOFacade entityDAOFacade;

	@PostConstruct
	public void init() {
		mailLog = new MailLog();
		loadMailLogInfo();
	}

	public void saveMailLog()
	{
		try {
			mailLogService.saveMailLog(mailLog);
			displayInfoMssg("Update Successful...!!");
			loadMailLogInfo();
			reset();
		} catch (Exception e) {
			displayErrorMssg(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void deleteMailLog()
	{
		try {
			mailLogService.deleteMailLog(mailLog);
			displayWarningMssg("Update Successful...!!");
			loadMailLogInfo();
			reset();
		} catch (Exception e) {
			displayErrorMssg(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<MailLog> findAllMailLog()
	{
		List<MailLog> list=new ArrayList<>();
		try {
			list= mailLogService.findAllMailLog();
		} catch (Exception e) {
			displayErrorMssg(e.getMessage());
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Page<MailLog> findAllMailLogPageable()
	{
		Pageable p=null;
		try {
			return mailLogService.findAllMailLog(p);
		} catch (Exception e) {
			displayErrorMssg(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<MailLog> findAllMailLogSort()
	{
		Sort s=null;
		List<MailLog> list=new ArrayList<>();
		try {
			list =mailLogService.findAllMailLog(s);
		} catch (Exception e) {
			displayErrorMssg(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	public void reset() {
		mailLog = new MailLog();
	}
	
	public void loadMailLogInfo()
	{
		 dataModel = new LazyDataModel<MailLog>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<MailLog> list = new  ArrayList<MailLog>();
			   
			   @Override 
			   public List<MailLog> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					list = (List<MailLog>) entityDAOFacade.getResultList(MailLog.class,first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(entityDAOFacade.count(filters,MailLog.class));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return list; 
			   }
			   
			    @Override
			    public Object getRowKey(MailLog obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public MailLog getRowData(String rowKey) {
			        for(MailLog obj : list) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
	}
	public MailLog getMailLog() {
		return mailLog;
	}

	public void setMailLog(MailLog mailLog) {
		this.mailLog = mailLog;
	}
	
	public ArrayList<MailLog> getMailLogList() {
		return mailLogList;
	}

	public void setMailLogList(ArrayList<MailLog> mailLogList) {
		this.mailLogList =mailLogList;
	}
	public LazyDataModel<MailLog> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MailLog> dataModel) {
		this.dataModel = dataModel;
	}
	

}
