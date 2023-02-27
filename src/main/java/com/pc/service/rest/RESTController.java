package com.pc.service.rest;

import com.ibm.icu.text.SimpleDateFormat;
import com.pc.entities.*;
import com.pc.entities.enums.ApprovalStatusEnum;
import com.pc.entities.enums.ReportTypeEnum;
import com.pc.entities.enums.ReportedInstitutionsStatus;
import com.pc.entities.lookup.Course;
import com.pc.entities.lookup.Province;
import com.pc.model.*;
import com.pc.service.ContactUsService;
import com.pc.service.InstitutionCourseService;
import com.pc.service.InstitutionService;
import com.pc.service.ReportedInstitutionsService;
import com.pc.service.lookup.CourseService;
import com.pc.service.lookup.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "http://localhost", maxAge = 3600)
@RestController
public class RESTController {

    @Autowired
    InstitutionCourseService istitutionCourseService;
    @Autowired
    private InstitutionService institutionService;
    @Autowired
    private InstitutionCourseService institutionCourseService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ReportedInstitutionsService reportedInstutionService;
    @Autowired
    private ReportedInstitutionsService reportedInstitutionsService;
    @Autowired
    private ContactUsService contactUsService;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    @GetMapping("/api/findInistitutionByInstitutionName")
    public InstitutionListDTO findInistitutionByInstitutionName(@RequestParam("name") String name) {

        List<Institution> list = new ArrayList<>();
        List<InstitutionDTO> instList = new ArrayList<>();
        InstitutionListDTO institutionDTODetails = new InstitutionListDTO();
        try {
            if (name != null && !name.isEmpty()) {
                name = name.trim();
                list = institutionService.searchByInstitutionName(name, ApprovalStatusEnum.Expired);
                for (Institution institution : list) {
                    instList.add(createInstitutionDTO(institution));
                }
                institutionDTODetails.setErrorMessage("");
                if (instList == null || instList.size() <= 0) {
                    institutionDTODetails.setErrorMessage("Institution NOT found");
                }
            } else {

                institutionDTODetails.setErrorMessage("Please enter institution name");
            }


        } catch (Exception err) {
            err.printStackTrace();
            institutionDTODetails.setErrorMessage("Error: " + err.getMessage());
        }

        institutionDTODetails.setInstitutions(instList);
        return institutionDTODetails;
    }

    @GetMapping("/api/findInistitutionByID")
    public InstitituionDetailsDTO findInistitutionByID(@RequestParam("id") String id) {
        InstitituionDetailsDTO institituionDetailsDTO = new InstitituionDetailsDTO();
        try {

            Institution institution = institutionService.findById(Long.parseLong(id));
            if (institution != null) {
                institituionDetailsDTO.setAccreditationNumber(institution.getAccreditationNumber());
                institituionDetailsDTO.setAlternativeContactNumber(institution.getAlternativeContactNumber());
                institituionDetailsDTO.setContactNumber(institution.getContactNumber());
                institituionDetailsDTO.setEmail(institution.getEmail());
                institituionDetailsDTO.setKnownAs(institution.getKnownAs());
                institituionDetailsDTO.setName(institution.getInstitutionName());
                institituionDetailsDTO.setStartDate(sdf.format(institution.getAccreditationStartDate()));
                institituionDetailsDTO.setEndDate(sdf.format(institution.getAccreditationEndDate()));

                //TODO RETURN A LIST OF ADDRESS
                if (institution.getAddressList() != null) {
                    institituionDetailsDTO.setAddressLine1(institution.getAddressList().get(0).getAddressLine1());
                    institituionDetailsDTO.setAddressLine2(institution.getAddressList().get(0).getAddressLine2());
                    if (institution.getAddressList().get(0).getProvince() != null) {
                        institituionDetailsDTO.setProvince(institution.getAddressList().get(0).getProvince().getDescription());
                    }
                }
                institituionDetailsDTO.setErrorMessage("");
            } else {
                institituionDetailsDTO.setErrorMessage("Institution NOT found");
            }

        } catch (Exception err) {
            err.printStackTrace();
            institituionDetailsDTO.setErrorMessage("Error: " + err.getMessage());
        }
        return institituionDetailsDTO;
    }

    @GetMapping("/api/findInstitutionIDByAccreditationNumber")
    public InstitutionIDDTO findInstitutionIDByAccreditationNumber(@RequestParam("accreditationNumber") String accreditationNumber) {
        InstitutionIDDTO institutionIDDTO = new InstitutionIDDTO();
        try {
            if (accreditationNumber != null && !accreditationNumber.isEmpty()) {
                accreditationNumber = accreditationNumber.trim();
                List<Institution> institutionList = institutionService.findByAccreditationNumber(accreditationNumber);
                if (institutionList != null && institutionList.size() > 0) {
                    institutionIDDTO.setId(institutionList.get(0).getId());
                    institutionIDDTO.setErrorMessage("");
                } else {
                    institutionIDDTO.setErrorMessage("Institution NOT found");
                }
            } else {
                institutionIDDTO.setErrorMessage("Please enter accreditation number");
            }
        } catch (Exception err) {
            err.printStackTrace();
            institutionIDDTO.setErrorMessage("Error: " + err.getMessage());
        }
        return institutionIDDTO;
    }


    @GetMapping("/api/findInstitutionCourses")
    public CouseListDTO findInstitutionCourses(@RequestParam("id") String id) {
        List<CouseDTO> list = new ArrayList<>();
        CouseListDTO institutionCouseListDTO = new CouseListDTO();
        try {
            Institution institution = institutionService.findInstitutionById(Long.parseLong(id));
            List<InstitutionCourse> instCourseList = institutionCourseService.findByInstitution(institution);
            try {
                for (InstitutionCourse ic : instCourseList) {
                    CouseDTO institutionCouseDTO = new CouseDTO(ic.getCourse().getId(), ic.getCourse().getDescription(), ic.getCourse().getCourseType().getDescription(), ic.getCourse().getCourseLevel().getDescription(), ic.getInstitution().getInstitutionName());
                    list.add(institutionCouseDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            institutionCouseListDTO.setErrorMessage("");
            if (list == null || list.size() <= 0) {
                institutionCouseListDTO.setErrorMessage("Course(s) NOT found");
            }
        } catch (Exception err) {
            err.printStackTrace();
            institutionCouseListDTO.setErrorMessage("Error: " + err.getMessage());
        }

        institutionCouseListDTO.setCouses(list);
        return institutionCouseListDTO;

    }

    @GetMapping("/api/findCourseByCourseNameAndProvince")
    public CouseListDTO findCourseByCourseNameAndProvince(@RequestParam("course") String course, @RequestParam("provinceCode") String provinceCode) {
        List<CouseDTO> list = new ArrayList<>();
        CouseListDTO couseListDTO = new CouseListDTO();
        System.out.println("Course: " + course + " Province Code" + provinceCode);
        try {
            List<InstitutionCourse> lnstitutionCourseList = new ArrayList<>();
            if (provinceCode != null && !provinceCode.isEmpty() && !provinceCode.equalsIgnoreCase("None")) {
                provinceCode = provinceCode.trim();
                if (course != null && !course.isEmpty()) {
                    Province province = provinceService.findByCode(provinceCode);
                    lnstitutionCourseList = institutionCourseService.getCourseByDescription(course, ApprovalStatusEnum.Expired);
                } else {
                    couseListDTO.setErrorMessage("Please enter course name");
                }
            } else {
                if (course != null && !course.isEmpty()) {
                    lnstitutionCourseList = institutionCourseService.getCouseByDescriptionNoProvince(course, ApprovalStatusEnum.Expired);
                } else {
                    couseListDTO.setErrorMessage("Please enter course name");
                }
            }

            for (InstitutionCourse ic : lnstitutionCourseList) {
                CouseDTO institutionCouseDTO = new CouseDTO(ic.getCourse().getId(), ic.getCourse().getDescription(), ic.getCourse().getCourseType().getDescription(), ic.getCourse().getCourseLevel().getDescription(), ic.getInstitution().getInstitutionName());
                list.add(institutionCouseDTO);
            }
            couseListDTO.setErrorMessage("");
            if (lnstitutionCourseList == null || lnstitutionCourseList.size() <= 0) {
                couseListDTO.setErrorMessage("Course(s) NOT found");
            }
        } catch (Exception err) {
            err.printStackTrace();
            couseListDTO.setErrorMessage("Error: " + err.getMessage());
        }

        couseListDTO.setCouses(list);
        return couseListDTO;
    }

    @GetMapping("/api/findCourseById")
    public CourseDetailDTO findCourseById(@RequestParam("id") String id) {
        CourseDetailDTO courseDetailDTO = new CourseDetailDTO();
        try {
            Course course = courseService.findById(Long.parseLong(id));
            if (course != null) {
                courseDetailDTO.setCourseLevel(course.getCourseLevel().getDescription());
                courseDetailDTO.setCourseType(course.getCourseType().getDescription());
                courseDetailDTO.setDescription(course.getDescription());
                courseDetailDTO.setErrorMessage("");
            } else {
                courseDetailDTO.setErrorMessage("Course Not Found");
            }
        } catch (Exception err) {
            err.printStackTrace();
            courseDetailDTO.setErrorMessage("Error: " + err.getMessage());
        }
        return courseDetailDTO;
    }


    @GetMapping("/api/findInistitutionByCourseID")
    public InstitutionListDTO findInistitutionByCourseID(@RequestParam("id") String id, @RequestParam("provinceCode") String provinceCode) {
        List<Institution> list = new ArrayList<>();
        List<InstitutionDTO> instList = new ArrayList<>();
        InstitutionListDTO institutionDTODetails = new InstitutionListDTO();
        try {
            Course course = courseService.findById(Long.parseLong(id));
            List<InstitutionCourse> institutionCourseList = institutionCourseService.findByCourse(course);

            for (InstitutionCourse ic : institutionCourseList) {
                if (ic.getInstitution().getStatus() != ApprovalStatusEnum.EndDateExpired) {

                    if (provinceCode != null && !provinceCode.isEmpty() && !provinceCode.equalsIgnoreCase("None")) {
                        provinceCode = provinceCode.trim();
                        if (ic.getInstitution().getAddressList() != null) {
                            for (Address address : ic.getInstitution().getAddressList()) {
                                if (address.getProvince() != null && address.getProvince().getCode().equalsIgnoreCase(provinceCode)) {
                                    list.add(ic.getInstitution());
                                    break;
                                }
                            }
                        }

                    } else {
                        list.add(ic.getInstitution());
                    }
                }
            }
            for (Institution institution : list) {
                instList.add(createInstitutionDTO(institution));
            }
            institutionDTODetails.setErrorMessage("");
            if (list != null && list.size() <= 0) {
                institutionDTODetails.setErrorMessage("Institution NOT Found");
            }
        } catch (Exception err) {
            err.printStackTrace();
            institutionDTODetails.setErrorMessage("Error: " + err.getMessage());
        }

        institutionDTODetails.setInstitutions(instList);
        return institutionDTODetails;
    }


    private InstitutionDTO createInstitutionDTO(Institution inst) {
        InstitutionDTO instDto = new InstitutionDTO();
        if (inst != null) {
            instDto.setId(inst.getId());
            instDto.setAccreditationNumber(inst.getAccreditationNumber());
            instDto.setTel(inst.getContactNumber());
            instDto.setThumb(inst.getAccreditationNumber());
            instDto.setTitle(inst.getInstitutionName());
            if (inst.getKnownAs() != null && !inst.getKnownAs().equalsIgnoreCase(inst.getInstitutionName())) {
                instDto.setTitle(inst.getInstitutionName() + "(" + inst.getKnownAs() + ")");
            }
            //TODO 1 Institution belongs to many province
            if (inst.getAddressList() != null && inst.getAddressList().get(0).getProvince() != null) {
                instDto.setProvince(inst.getAddressList().get(0).getProvince().getDescription());
            }

        }
        return instDto;
    }

    @GetMapping("/api/chechStatus")
    public ReportedInstitutionDTO chechStatus(@RequestParam("refNum") String refNum) {
        ReportedInstitutionDTO reportedInstitutionDTO = new ReportedInstitutionDTO();
        try {
            if (refNum != null && !refNum.isEmpty()) {
                refNum = refNum.trim();
                ReportedInstitutions reportedInstitutions = reportedInstitutionsService.findByRefNumber(refNum);
                if (reportedInstitutions != null) {
                    String institutionName = "";
                    if (reportedInstitutions.getInstitution() != null) {
                        institutionName = reportedInstitutions.getInstitution().getInstitutionName();
                    } else {
                        institutionName = reportedInstitutions.getInstitutionName();
                    }
                    reportedInstitutionDTO.setInstitutionName(institutionName);
                    reportedInstitutionDTO.setInvestigatorFeedback(reportedInstitutions.getInvestigatorFeedback());
                    reportedInstitutionDTO.setRefNumber(refNum);
                    reportedInstitutionDTO.setRepordedDate(sdf.format(reportedInstitutions.getCreateDate()));
                    reportedInstitutionDTO.setReporterFullName(reportedInstitutions.getReporterName() + " " + reportedInstitutions.getReporterSurname());
                    reportedInstitutionDTO.setReportType(reportedInstitutions.getReportType().getFriendlyName());
                    reportedInstitutionDTO.setStatus(reportedInstitutions.getStatus().getFriendlyName());
                    if (reportedInstitutions.getLastUpdateDate() != null) {
                        reportedInstitutionDTO.setLastUpdatedDate(sdf.format(reportedInstitutions.getLastUpdateDate()));
                    } else {
                        reportedInstitutionDTO.setLastUpdatedDate("N/A");
                    }
                    reportedInstitutionDTO.setErrorMessage("");
                } else {
                    reportedInstitutionDTO.setErrorMessage("Invalid reference number");
                }
            } else {
                reportedInstitutionDTO.setErrorMessage("Please enter reference number");
            }
        } catch (Exception err) {
            err.printStackTrace();
            reportedInstitutionDTO.setErrorMessage("Error: " + err.getMessage());
        }
        return reportedInstitutionDTO;
    }

    @PostMapping(value = "/api/report")
    @ResponseBody
    public Response saveReport(@RequestBody AppReportedInstitution appReportedInstitution) {
        Response response = new Response();
        try {
            reportedInstutionService.submitReportedInstitutions(mapObject(appReportedInstitution), mapAddress(appReportedInstitution), mapCourses(appReportedInstitution));
            response.setMessage(appReportedInstitution.getInstitutionName() + " successfully reported");
            return response;

        } catch (Exception err) {
            err.printStackTrace();
            response.setMessage("Error couldn't save report " + err.getMessage());
            return response;
        }
    }

    private ArrayList<ReportedInstitutionCourse> mapCourses(AppReportedInstitution appReportedInstitution) {
        List<ReportedInstitutionCourse> reportedInstitutionCourses = new ArrayList<>();
        appReportedInstitution.getCourseName().forEach(course -> {
            ReportedInstitutionCourse reportedInstitutionCourse = new ReportedInstitutionCourse();
            try {
                Course newCourse = courseService.findById(Long.parseLong(course));
                reportedInstitutionCourse.setCourse(newCourse);
                reportedInstitutionCourse.setCourseType(newCourse.getCourseType().getDescription());
                reportedInstitutionCourse.setCourseDesc(newCourse.getDescription());
                reportedInstitutionCourse.setCourseLevel(newCourse.getCourseLevel().getDescription());

            } catch (Exception e) {
                e.printStackTrace();
            }
            reportedInstitutionCourses.add(reportedInstitutionCourse);
        });
        return (ArrayList<ReportedInstitutionCourse>) reportedInstitutionCourses;
    }

    private Address mapAddress(AppReportedInstitution appReportedInstitution) throws Exception {
        Address address = new Address();
        if (appReportedInstitution.getReportType()) {
            Province province = provinceService.findByCode(appReportedInstitution.getProvince());
            address.setAddressLine1(appReportedInstitution.getAddressLineOne());
            address.setAddressLine2(appReportedInstitution.getAddressLineTwo());
            address.setAddressLine3(appReportedInstitution.getAddressLineThree());
            address.setProvince(province);
            address.setSuburb(appReportedInstitution.getSurbub());
            address.setAddressLine4(appReportedInstitution.getCode());
        }
        return address;
    }

    private ReportedInstitutions mapObject(AppReportedInstitution appReportedInstitution) {
        ReportedInstitutions reportedInstitutions = new ReportedInstitutions();

        if (appReportedInstitution.getReportType()) {
            reportedInstitutions.setReportType(ReportTypeEnum.BogusInstitute);
            reportedInstitutions.setInstitutionName(appReportedInstitution.getInstitutionName());
        } else {
            reportedInstitutions.setReportType(ReportTypeEnum.BogusCourses);
            reportedInstitutions.setInstitutionName(appReportedInstitution.getInstitutionName());

        }
        reportedInstitutions.setCreateDate(new Date());
        reportedInstitutions.setRefNumber(getRefNumber());
        reportedInstitutions.setStatus(ReportedInstitutionsStatus.Submitted);
        reportedInstitutions.setReporterCell(appReportedInstitution.getCellNumber());
        reportedInstitutions.setReporterEmail(appReportedInstitution.getEmail());
        reportedInstitutions.setReporterName(appReportedInstitution.getFirstname());
        reportedInstitutions.setReporterSurname(appReportedInstitution.getLastname());

        return reportedInstitutions;

    }

    private String getRefNumber() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 7;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        long millis = System.currentTimeMillis();
        String generatedString = "REF" + buffer.toString().toUpperCase() + "" + millis;
        return generatedString;
    }

    @ResponseBody
    @RequestMapping(value = "/api/sendContactUsMessage", method = RequestMethod.POST)
    public Response sendContactUsMessage(@RequestBody ContactUs contactUs) {
        Response response = new Response();
        try {
            ContactUs cu = contactUsService.sendContactUsMessage(contactUs);
            response.setMessage("Your message has been sent, thank you.");
            response.setId(cu.getId());
        } catch (Exception err) {
            err.printStackTrace();
            response.setMessage("Error couldn't send message " + err.getMessage());
        }
        return response;
    }

    @GetMapping("/api/statisticsReport")
    public StatisticsReport getStatisticsReport() {

        StatisticsReport statisticsReport = new StatisticsReport();
        try {
            statisticsReport.setTotalInstitutionUndetInvestigation(reportedInstitutionsService.countByStatus(ReportedInstitutionsStatus.UnderInvestigation));
            statisticsReport.setTotalInstitutionReported(reportedInstitutionsService.countAll());
            statisticsReport.setTotalCourse(istitutionCourseService.countAll());
            statisticsReport.setTotalInstitution(institutionService.countAll());
            statisticsReport.setErrorMessage("");
        } catch (Exception e) {
            statisticsReport.setErrorMessage("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return statisticsReport;
    }

    @GetMapping("/api/find-all-institutions")
    public List<AllInstitutionDTO> findAllRegisteredInstitutions() {
        try {
            return institutionService.findAllInstitutionDTO();
        } catch (Exception err) {
            return null;
        }
    }

    @GetMapping("/api/find-all-courses")
    public List<AllCourseDTO> findAllCourses() {
        try {
            return courseService.findAllCourseDTO();
        } catch (Exception err) {
            return null;
        }
    }

    private List<Institution> filterInstitutions(List<Institution> institutions) {
        List<Institution> institutions1ist = new ArrayList<>();
        for (Institution institution : institutions) {
            Institution in = new Institution();
            in.setId(institution.getId());
            in.setInstitutionName(institution.getInstitutionName());
            in.setKnownAs(institution.getKnownAs());
            institutions1ist.add(in);
        }
        return institutions1ist;
    }

    @ResponseBody
    @RequestMapping(value = "/api/reportBogusInstitution", method = RequestMethod.POST)
    public Response reportBogusInstitution(@RequestBody BogusInstitution bogusInstitution) {
        Response response = new Response();
        try {
            ReportedInstitutions reportedInstitutions = new ReportedInstitutions();
            reportedInstitutions.setInstitutionName(bogusInstitution.getInstName());
            reportedInstitutions.setReportType(ReportTypeEnum.BogusInstitute);
            reportedInstitutions.setKnownAs(bogusInstitution.getKnownAs());
            reportedInstitutions.setReporterCell(bogusInstitution.getReporterPhone());
            reportedInstitutions.setReporterEmail(bogusInstitution.getReporterEmail());
            reportedInstitutions.setReporterName(bogusInstitution.getReporterName());
            reportedInstitutions.setReporterSurname(bogusInstitution.getReporterSurname());

            Address address = new Address();
            Province province = provinceService.findByCode(bogusInstitution.getProvince());
            address.setAddressLine1(bogusInstitution.getAddressLine1());
            address.setAddressLine2(bogusInstitution.getAddressLine2());
            address.setAddressLine3(bogusInstitution.getAddressLine3());
            address.setProvince(province);
            address.setSuburb(bogusInstitution.getSuburbArea());
            address.setCode(bogusInstitution.getPostalCode());

            reportedInstutionService.submitReportedInstitutions(reportedInstitutions, address, null);
            response.setMessage(bogusInstitution.getInstName() + " successfully reported, thank you.");
            response.setSuccess(true);
        } catch (Exception err) {
            err.printStackTrace();
            response.setMessage("Error couldn't submit request " + err.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/api/reportBogusCourses", method = RequestMethod.POST)
    public Response reportBogusCourses(@RequestBody BogusCourseDetails bogusCourseDetails) {
        Response response = new Response();
        try {
            if (bogusCourseDetails.getBogusCourses() == null ||
                    ((bogusCourseDetails.getBogusCourses().getCourseList() == null && bogusCourseDetails.getCourseList() == null) ||
                            (bogusCourseDetails.getBogusCourses().getCourseList().size() == 0 && bogusCourseDetails.getCourseList().size() == 0))) {
                response.setMessage("Please select or specify bogus course details");
                response.setSuccess(false);
            } else {
                BogusCourses bogusCourses = bogusCourseDetails.getBogusCourses();
                List<CouseDTO> courseList = bogusCourseDetails.getCourseList();
                System.out.println(bogusCourseDetails.toString());

                ReportedInstitutions reportedInstitutions = new ReportedInstitutions();
                reportedInstitutions.setReportType(ReportTypeEnum.BogusCourses);
                reportedInstitutions.setReporterCell(bogusCourses.getReporterPhone());
                reportedInstitutions.setReporterEmail(bogusCourses.getReporterEmail());
                reportedInstitutions.setReporterName(bogusCourses.getReporterName());
                reportedInstitutions.setReporterSurname(bogusCourses.getReporterSurname());

                Institution institution = institutionService.findInstitutionById(bogusCourses.getInstitutionID());
                reportedInstitutions.setInstitution(institution);
                ArrayList<ReportedInstitutionCourse> reportedInstitutionCourseList = new ArrayList<>();
                if (bogusCourses.getCourseList() != null && bogusCourses.getCourseList().size() > 0) {
                    for (Long courseId : bogusCourses.getCourseList()) {
                        Course course = courseService.findById(courseId);
                        ReportedInstitutionCourse reportedInstitutionCourse = new ReportedInstitutionCourse();
                        reportedInstitutionCourse.setCourse(course);
                        reportedInstitutionCourse.setReportedInstitution(reportedInstitutions);
                        reportedInstitutionCourseList.add(reportedInstitutionCourse);
                    }
                }
                if (bogusCourseDetails.getCourseList() != null && bogusCourseDetails.getCourseList().size() > 0) {
                    for (CouseDTO course : bogusCourseDetails.getCourseList()) {
                        ReportedInstitutionCourse reportedInstitutionCourse = new ReportedInstitutionCourse();
                        reportedInstitutionCourse.setCourseDesc(course.getDescription());
                        reportedInstitutionCourse.setCourseLevel(course.getCourseLevel());
                        reportedInstitutionCourse.setCourseType(course.getCourseType());
                        reportedInstitutionCourse.setReportedInstitution(reportedInstitutions);
                        reportedInstitutionCourseList.add(reportedInstitutionCourse);
                    }
                }
                reportedInstutionService.submitReportedInstitutions(reportedInstitutions, null, reportedInstitutionCourseList);
                response.setMessage(institution.getInstitutionName() + " successfully reported, thank you.");
                response.setSuccess(true);
            }
        } catch (Exception err) {
            err.printStackTrace();
            response.setMessage("Error couldn't submit request " + err.getMessage());
            response.setSuccess(false);
        }
        return response;
    }
}
