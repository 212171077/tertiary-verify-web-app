package com.pc.ui;

import com.pc.common.AES;
import com.pc.entities.*;
import com.pc.entities.enums.*;
import com.pc.entities.lookup.*;
import com.pc.framework.AbstractUI;
import com.pc.service.*;
import com.pc.service.lookup.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component("commonItemUI")
@ViewScoped
public class CommonItemUI extends AbstractUI {
    @Autowired
    GenderService genderService;

    @Autowired
    ProvinceService provinceService;

    @Autowired
    InstitutionTypeService institutionTypeService;

    @Autowired
    CourseTypeService courseTypeService;

    @Autowired
    CourseLevelService courseLevelService;

    @Autowired
    TitleService titleService;

    @Autowired
    CityService cityService;

    @Autowired
    CourseService courseService;

    @Autowired
    FacultyService facultyService;

    @Autowired
    RoleService roleService;
    @Autowired
    InstitutionService institutionService;
    @Autowired
    UserBrowserInfoService userBrowserInfoService;
    @Autowired
    WorkflowService workflowService;
    @Autowired
    WorkflowRoleService workflowRoleService;
    @Autowired
    EmailTemplateService emailTemplateService;
    @Autowired
    WorkFlowRoleUserService workFlowRoleUserService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    DocConfigService docConfigService;
    @Autowired
    DocByteService docByteService;
    @Autowired
    DocConfigDetailService docConfigDetailService;
    @Autowired
    DocConfigDetailActionHistService docConfigDetailActionHistService;
    @Autowired
    DocumentLevelService documentLevelService;
    @Autowired
    MailLogService mailLogService;
    @Autowired
    TaskService taskService;
    @Autowired
    UserTaskService userTaskService;
    @Autowired
    ContactUsService contactUsService;
    @Autowired
    AppConfigService appConfigService;
    @Autowired
    RejectReasonService rejectReasonService;
    @Autowired
    InstitutionRejectReasonService institutionRejectReasonService;
    @Autowired
    AddressService addressService;

    @Autowired
    @PostConstruct
    public void init() {

    }

    /**
     * Gets the select items gender.
     *
     * @return the select items gender
     */
    public List<Gender> getSelectItemsGender() {

        List<Gender> l = null;
        try {

            l = genderService.findAllGender();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    /**
     * Gets the select items title.
     *
     * @return the select items title
     */
    public List<Title> getSelectItemsTitle() {

        List<Title> l = null;
        try {

            l = titleService.findAllTitle();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<SelectItem> getYesNoEnumDD() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        for (YesNoEnum val : YesNoEnum.values()) {
            l.add(new SelectItem(val, val.getFriendlyName()));
        }
        return l;
    }

    public List<SelectItem> getReportTypeEnumDD() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        for (ReportTypeEnum val : ReportTypeEnum.values()) {
            l.add(new SelectItem(val, val.getFriendlyName()));
        }
        return l;
    }

    public List<SelectItem> getSearchTypeEnumDD() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        for (SearchTypeEnum val : SearchTypeEnum.values()) {
            l.add(new SelectItem(val, val.getFriendlyName()));
        }
        return l;
    }

    public List<Province> getSelectItemsProvince() {

        List<Province> l = null;
        try {

            l = provinceService.findAllProvince();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<Role> getSelectItemsRole() {

        List<Role> l = null;
        try {

            l = roleService.findAllRole();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<Faculty> getSelectItemsFuculty() {

        List<Faculty> l = null;
        try {

            l = facultyService.findAllFaculty();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<InstitutionType> getSelectItemsInstitutionType() {

        List<InstitutionType> l = null;
        try {

            l = institutionTypeService.findAllInstitutionType();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<CourseType> getSelectItemsCourseType() {

        List<CourseType> l = null;
        try {

            l = courseTypeService.findAllCourseType();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<CourseLevel> getSelectItemsCourseLeve() {

        List<CourseLevel> l = null;
        try {

            l = courseLevelService.findAllCourseLevel();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<Course> getSelectItemsCourse() {

        List<Course> l = null;
        try {

            l = courseService.findAllCourse();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<City> getSelectItemsCity(Province province) {

        List<City> l = null;
        try {

            l = cityService.findByProvince(province);

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<SelectItem> getReportedInstitutionsStatusDD() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        for (ReportedInstitutionsStatus val : ReportedInstitutionsStatus.values()) {
            l.add(new SelectItem(val, val.getFriendlyName()));
        }
        return l;
    }

    public List<SelectItem> getPermissionDD() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        for (PermissionEnum val : PermissionEnum.values()) {
            l.add(new SelectItem(val, val.getFriendlyName()));
        }
        return l;
    }

    public List<SelectItem> getEmailTemplateTypeDD() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        for (EmailTemplateTypeEnum val : EmailTemplateTypeEnum.values()) {
            l.add(new SelectItem(val, val.getFriendlyName()));
        }
        return l;
    }

    public List<Course> autoCompleteCorse(String text) {
        List<Course> list = null;
        try {
            if (text == null || text.isEmpty()) {
                list = courseService.findAllCourse();
            } else {
                list = courseService.findByDescriptionStartingWith(text);
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Institution> autoCompleteInstitution(String searchText) {
        List<Institution> list = null;
        try {
            if (searchText == null || searchText.isEmpty()) {
                list = institutionService.findAllInstitution();
            } else {
                list = institutionService.searchByInstitutionName(searchText, ApprovalStatusEnum.Expired);
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Institution> getSelectItemsInstitution() {

        List<Institution> l = null;
        try {

            l = institutionService.findAllInstitution();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<UserBrowserInfo> autoCompleteUserBrowserInfo(String searchText) {
        List<UserBrowserInfo> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=userBrowserInfoService.findAllUserBrowserInfo();
			}
			else
			{
				list=userBrowserInfoService.findByDescriptionStartingWith(searchText);
			}*/
            list = userBrowserInfoService.findAllUserBrowserInfo();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<UserBrowserInfo> getSelectItemsUserBrowserInfo() {

        List<UserBrowserInfo> l = null;
        try {

            l = userBrowserInfoService.findAllUserBrowserInfo();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<Workflow> autoCompleteWorkflow(String searchText) {
        List<Workflow> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=workflowService.findAllWorkflow();
			}
			else
			{
				list=workflowService.findByDescriptionStartingWith(searchText);
			}*/
            list = workflowService.findAllWorkflow();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Workflow> getSelectItemsWorkflow() {

        List<Workflow> l = null;
        try {

            l = workflowService.findAllWorkflow();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<WorkflowRole> autoCompleteWorkflowRole(String searchText) {
        List<WorkflowRole> list = null;
        try {
            if (searchText == null || searchText.isEmpty()) {
                list = workflowRoleService.findAllWorkflowRole();
            } else {
                list = workflowRoleService.findByDescriptionStartingWith(searchText);
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<WorkflowRole> getSelectItemsWorkflowRole() {

        List<WorkflowRole> l = null;
        try {

            l = workflowRoleService.findAllWorkflowRole();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<EmailTemplate> autoCompleteEmailTemplate(String searchText) {
        List<EmailTemplate> list = null;
        try {
            if (searchText == null || searchText.isEmpty()) {
                list = emailTemplateService.findAllEmailTemplate();
            } else {
                list = emailTemplateService.findByDescriptionStartingWith(searchText);
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<EmailTemplate> getSelectItemsEmailTemplate() {

        List<EmailTemplate> l = null;
        try {

            l = emailTemplateService.findAllEmailTemplate();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<WorkFlowRoleUser> autoCompleteWorkFlowRoleUser(String searchText) {
        List<WorkFlowRoleUser> list = null;
        try {
            if (searchText == null || searchText.isEmpty()) {
                list = workFlowRoleUserService.findAllWorkFlowRoleUser();
            } else {
                list = workFlowRoleUserService.findAllWorkFlowRoleUser();
                //list=workFlowRoleUserService.findByDescriptionStartingWith(searchText);
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<WorkFlowRoleUser> getSelectItemsWorkFlowRoleUser() {

        List<WorkFlowRoleUser> l = null;
        try {

            l = workFlowRoleUserService.findAllWorkFlowRoleUser();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<UserRole> autoCompleteUserRole(String searchText) {
        List<UserRole> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=userRoleService.findAllUserRole();
			}
			else
			{
				list=userRoleService.findByDescriptionStartingWith(searchText);
			}*/
            list = userRoleService.findAllUserRole();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<UserRole> getSelectItemsUserRole() {

        List<UserRole> l = null;
        try {

            l = userRoleService.findAllUserRole();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<DocConfig> autoCompleteDocConfig(String searchText) {
        List<DocConfig> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=docConfigService.findAllDocConfig();
			}
			else
			{
				list=docConfigService.findByDescriptionStartingWith(searchText);
			}*/
            list = docConfigService.findAllDocConfig();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<DocConfig> getSelectItemsDocConfig() {

        List<DocConfig> l = null;
        try {

            l = docConfigService.findAllDocConfig();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<DocByte> autoCompleteDocByte(String searchText) {
        List<DocByte> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=docByteService.findAllDocByte();
			}
			else
			{
				list=docByteService.findByDescriptionStartingWith(searchText);
			}*/
            list = docByteService.findAllDocByte();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<DocByte> getSelectItemsDocByte() {

        List<DocByte> l = null;
        try {

            l = docByteService.findAllDocByte();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<DocConfigDetail> autoCompleteDocConfigDetail(String searchText) {
        List<DocConfigDetail> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=docConfigDetailService.findAllDocConfigDetail();
			}
			else
			{
				list=docConfigDetailService.findByDescriptionStartingWith(searchText);
			}*/
            list = docConfigDetailService.findAllDocConfigDetail();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<DocConfigDetail> getSelectItemsDocConfigDetail() {

        List<DocConfigDetail> l = null;
        try {

            l = docConfigDetailService.findAllDocConfigDetail();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<DocConfigDetailActionHist> autoCompleteDocConfigDetailActionHist(String searchText) {
        List<DocConfigDetailActionHist> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=docConfigDetailActionHistService.findAllDocConfigDetailActionHist();
			}
			else
			{
				list=docConfigDetailActionHistService.findByDescriptionStartingWith(searchText);
			}*/
            list = docConfigDetailActionHistService.findAllDocConfigDetailActionHist();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<DocConfigDetailActionHist> getSelectItemsDocConfigDetailActionHist() {

        List<DocConfigDetailActionHist> l = null;
        try {

            l = docConfigDetailActionHistService.findAllDocConfigDetailActionHist();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<DocumentLevel> autoCompleteDocumentLevel(String searchText) {
        List<DocumentLevel> list = null;
        try {
            if (searchText == null || searchText.isEmpty()) {
                list = documentLevelService.findAllDocumentLevel();
            } else {
                list = documentLevelService.findByDescriptionStartingWith(searchText);
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<DocumentLevel> getSelectItemsDocumentLevel() {

        List<DocumentLevel> l = null;
        try {

            l = documentLevelService.findAllDocumentLevel();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<MailLog> autoCompleteMailLog(String searchText) {
        List<MailLog> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=mailLogService.findAllMailLog();
			}
			else
			{
				list=mailLogService.findByDescriptionStartingWith(searchText);
			}*/
            list = mailLogService.findAllMailLog();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<MailLog> getSelectItemsMailLog() {

        List<MailLog> l = null;
        try {

            l = mailLogService.findAllMailLog();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<Task> autoCompleteTask(String searchText) {
        List<Task> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=taskService.findAllTask();
			}
			else
			{
				list=taskService.findByDescriptionStartingWith(searchText);
			}*/
            list = taskService.findAllTask();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Task> getSelectItemsTask() {

        List<Task> l = null;
        try {

            l = taskService.findAllTask();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<UserTask> autoCompleteUserTask(String searchText) {
        List<UserTask> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=userTaskService.findAllUserTask();
			}
			else
			{
				list=userTaskService.findByDescriptionStartingWith(searchText);
			}*/
            list = userTaskService.findAllUserTask();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<UserTask> getSelectItemsUserTask() {

        List<UserTask> l = null;
        try {

            l = userTaskService.findAllUserTask();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<ContactUs> autoCompleteContactUs(String searchText) {
        List<ContactUs> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=contactUsService.findAllContactUs();
			}
			else
			{
				list=contactUsService.findByDescriptionStartingWith(searchText);
			}*/
            list = contactUsService.findAllContactUs();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<ContactUs> getSelectItemsContactUs() {

        List<ContactUs> l = null;
        try {

            l = contactUsService.findAllContactUs();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<AppConfig> autoCompleteAppConfig(String searchText) {
        List<AppConfig> list = null;
        try {
            if (searchText == null || searchText.isEmpty()) {
                list = appConfigService.findAllAppConfig();
            } else {
                //list=appConfigService.findByDescriptionStartingWith(searchText);
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<AppConfig> getSelectItemsAppConfig() {

        List<AppConfig> l = null;
        try {

            l = appConfigService.findAllAppConfig();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<RejectReason> autoCompleteRejectReason(String searchText) {
        List<RejectReason> list = null;
        try {
            if (searchText == null || searchText.isEmpty()) {
                list = rejectReasonService.findAllRejectReason();
            } else {
                list = rejectReasonService.findByDescriptionStartingWith(searchText);
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<RejectReason> getSelectItemsRejectReason() {

        List<RejectReason> l = null;
        try {

            l = rejectReasonService.findAllRejectReason();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<InstitutionRejectReason> autoCompleteInstitutionRejectReason(String searchText) {
        List<InstitutionRejectReason> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=institutionRejectReasonService.findAllInstitutionRejectReason();
			}
			else
			{
				list=institutionRejectReasonService.findByDescriptionStartingWith(searchText);
			}*/
            list = institutionRejectReasonService.findAllInstitutionRejectReason();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<InstitutionRejectReason> getSelectItemsInstitutionRejectReason() {

        List<InstitutionRejectReason> l = null;
        try {

            l = institutionRejectReasonService.findAllInstitutionRejectReason();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public List<Address> autoCompleteAddress(String searchText) {
        List<Address> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=addressService.findAllAddress();
			}
			else
			{
				list=addressService.findByDescriptionStartingWith(searchText);
			}*/
            list = addressService.findAllAddress();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Address> getSelectItemsAddress() {

        List<Address> l = null;
        try {

            l = addressService.findAllAddress();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }


    @Autowired
    InstCourseAddressService instCourseAddressService;

    public List<InstCourseAddress> autoCompleteInstCourseAddress(String searchText) {
        List<InstCourseAddress> list = null;
        try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=instCourseAddressService.findAllInstCourseAddress();
			}
			else
			{
				list=instCourseAddressService.findByDescriptionStartingWith(searchText);
			}*/
            list = instCourseAddressService.findAllInstCourseAddress();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<InstCourseAddress> getSelectItemsInstCourseAddress() {

        List<InstCourseAddress> l = null;
        try {

            l = instCourseAddressService.findAllInstCourseAddress();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }


    @Autowired
    NewsService newsService;

    public List<News> autoCompleteNews(String searchText) {
        List<News> list = null;
        try {
            if (searchText == null || searchText.isEmpty()) {
                list = newsService.findAllNews();
            } else {
                list = newsService.findByDescriptionStartingWith(searchText);
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<News> getSelectItemsNews() {

        List<News> l = null;
        try {

            l = newsService.findAllNews();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }


    @Autowired
    EventService eventService;

    public List<Event> autoCompleteEvent(String searchText) {
        List<Event> list = null;
        try {
            if (searchText == null || searchText.isEmpty()) {
                list = eventService.findAllEvent();
            } else {
                list = eventService.findByDescriptionStartingWith(searchText);
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Event> getSelectItemsEvent() {

        List<Event> l = null;
        try {

            l = eventService.findAllEvent();

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
        }
        return l;
    }

    public String substringText(String value, int maxLength) {
        if (value.length() > maxLength) {
            return value.substring(0, maxLength)+"...";
        } else {
            return value;
        }
    }

    public String formatDate(Date date){
        if(date !=null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
            return simpleDateFormat.format(date);
        }else {
            return null;
        }
    }

    public String encrypt(String value){
        return AES.encrypt(value);
    }

    
	@Autowired
	EmailContentService emailContentService; 
	
	public List<EmailContent> autoCompleteEmailContent(String searchText) 
	{
		List<EmailContent> list=null;
		try {
			/*if(searchText==null || searchText.isEmpty())
			{
				list=emailContentService.findAllEmailContent();
			}
			else
			{
				list=emailContentService.findByDescriptionStartingWith(searchText);
			}*/
			list=emailContentService.findAllEmailContent();
		} catch (Exception e) {
			displayErrorMssg(e.getMessage());
			e.printStackTrace();
		}
		
		return list;
	}
	
	 public List<EmailContent> getSelectItemsEmailContent() {
		
		List<EmailContent> l = null;
		try {
			
			l = emailContentService.findAllEmailContent();
		
		} catch (Exception e) {
			displayErrorMssg(e.getMessage());
		}
		return l;
	}

	
	//===============DO NOT REMOVE THIS=================//




}
