package com.pc.service.rest;

import com.ibm.icu.text.SimpleDateFormat;
import com.pc.beans.CourseBean;
import com.pc.entities.Address;
import com.pc.entities.Institution;
import com.pc.entities.InstitutionCourse;
import com.pc.entities.ReportedInstitutions;
import com.pc.entities.enums.ApprovalStatusEnum;
import com.pc.entities.enums.ReportTypeEnum;
import com.pc.entities.enums.ReportedInstitutionsStatus;
import com.pc.entities.lookup.City;
import com.pc.entities.lookup.Course;
import com.pc.entities.lookup.Province;
import com.pc.model.AppCourse;
import com.pc.model.AppInstitution;
import com.pc.model.AppReportedInstitution;
import com.pc.service.AddressService;
import com.pc.service.InstitutionCourseService;
import com.pc.service.InstitutionService;
import com.pc.service.ReportedInstitutionsService;
import com.pc.service.lookup.CityService;
import com.pc.service.lookup.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@RestController
public class AppController {

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private InstitutionCourseService institutionCourseService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private ReportedInstitutionsService reportedInstutionService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/rest/findInistitutionByQRCode", method = RequestMethod.GET)
    @ResponseBody
    public AppInstitution findInistitutionByQRCode(@RequestParam("qrCode") String qrCode) {
        AppInstitution institution = new AppInstitution();
        try {
            institution = mapAppInstitution(institutionService.findInstitutionByQRCode(qrCode));
        } catch (Exception e) {

        }
        return institution;
    }

    @RequestMapping(value = "/rest/findCourseByInstitutionId/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<InstitutionCourse> getCoursesById(@PathVariable("id") String id) {
        List<InstitutionCourse> list = new ArrayList<>();
        try {
            Institution institution = institutionService.findInstitutionById(Long.parseLong(id));
            list = institutionCourseService.findByInstitution(institution);
        } catch (Exception err) {
            err.printStackTrace();
        }
        return list;

    }

    @RequestMapping(value = "/rest/findInstitutionCourses/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<CourseBean> findInstitutionCourses(@PathVariable("id") String id) {
        List<CourseBean> list = new ArrayList<>();
        try {
            Institution institution = institutionService.findInstitutionById(Long.parseLong(id));
            List<InstitutionCourse> instCourseList = institutionCourseService.findByInstitution(institution);
            for (InstitutionCourse ic : instCourseList) {
                CourseBean bean = new CourseBean(ic.getCourse().getDescription(), ic.getCourse().getCourseType().getDescription(), ic.getCourse().getCourseLevel().getDescription());
                list.add(bean);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return list;

    }


    @RequestMapping(value = "/rest/findCityByProvinceId/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<City> findCityByProvinceId(@PathVariable("id") String id) {
        List<City> list = new ArrayList<>();
        try {
            list = cityService.findByProvinceId(Long.parseLong(id));
        } catch (Exception err) {
            err.printStackTrace();
        }

        return list;
    }


    @RequestMapping(value = "/rest/verifyCourseAtInstitution/{id}/{name}", method = RequestMethod.GET)
    @ResponseBody
    public List<AppCourse> verifyCourseAtInstitution(@PathVariable("id") Long id, @PathVariable("name") String courseName) {
        List<InstitutionCourse> list = new ArrayList<>();
        List<AppCourse> applist = new ArrayList<>();
        try {
            list = institutionCourseService.findCourseByCourseName(id, courseName);
            for (InstitutionCourse institutionCourse : list) {
                applist.add(mapAppCourse(institutionCourse));
            }

        } catch (Exception err) {
            err.printStackTrace();
        }
        return applist;
    }

    @RequestMapping(value = "/rest/findInistitutionByCourseName/{name}", method = RequestMethod.GET)
    @ResponseBody
    public List<AppInstitution> findInistitutionByCourseName(@PathVariable("name") String name) {
        List<InstitutionCourse> list = new ArrayList<>();
        List<AppInstitution> applist = new ArrayList<>();
        try {
            list = institutionCourseService.getCouseByDescriptionNoProvince(name, ApprovalStatusEnum.Expired);
            for (InstitutionCourse institutionCourse : list) {
                applist.add(mapAppInstitution(institutionCourse));
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return applist;
    }

    @RequestMapping(value = "/rest/getInstitutionByCourseAndProvince/{name}/{provinceId}", method = RequestMethod.GET)
    @ResponseBody
    public List<AppInstitution> getInstitutionByCourseAndProvince(@PathVariable("name") String name, @PathVariable("provinceId") Long provinceId) {
        List<InstitutionCourse> list = new ArrayList<>();
        List<AppInstitution> applist = new ArrayList<>();
        try {
            Province province = provinceService.findById(provinceId);
            list = institutionCourseService.getCourseByDescription(name, ApprovalStatusEnum.Expired);
            for (InstitutionCourse institutionCourse : list) {
                applist.add(mapAppInstitution(institutionCourse));
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return applist;
    }

    @RequestMapping(value = "/rest/findInistitutionByInstitutionName/{name}", method = RequestMethod.GET)
    @ResponseBody
    public List<AppInstitution> findInistitutionByInstitutionName(@PathVariable("name") String name) {
        List<Institution> list = new ArrayList<>();
        List<AppInstitution> applist = new ArrayList<>();
        try {
            list = institutionService.searchByInstitutionName(name, ApprovalStatusEnum.Expired);
            for (Institution institution : list) {
                applist.add(mapAppInstitution(institution));
            }

        } catch (Exception err) {
            err.printStackTrace();
        }
        return applist;
    }

    @RequestMapping(value = "/rest/findAllProvince", method = RequestMethod.GET)
    @ResponseBody
    public List<Province> findAllProvince() {
        List<Province> list = new ArrayList<>();
        try {
            list = provinceService.findAllProvince();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return list;
    }

    @RequestMapping(value = "/rest/findByAccreditationNumber", method = RequestMethod.GET)
    @ResponseBody
    public AppInstitution findByAccreditationNumber(@RequestParam("accreditationNumber") String accreditationNumber) {
        List<Institution> list = new ArrayList<>();
        AppInstitution institution = new AppInstitution();
        try {
            list = institutionService.findByAccreditationNumber(accreditationNumber);
            if (list.size() > 0) {
                institution = mapAppInstitution(list.get(0));
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return institution;
    }

    private AppInstitution mapAppInstitution(Institution institution) {
        AppInstitution appInstitution = new AppInstitution();
        appInstitution.setId(institution.getId());
        appInstitution.setIntitutionType(institution.getInstitutionType().getDescription());
        appInstitution.setName(institution.getInstitutionName());
        appInstitution.setKnownAs(institution.getKnownAs());
        appInstitution.setContactNumber(institution.getContactNumber());
        appInstitution.setAlternativeContactNumber(institution.getAlternativeContactNumber());
        appInstitution.setEmail(institution.getEmail());
        appInstitution.setStartDate(new SimpleDateFormat("dd MMM yyyy").format(institution.getAccreditationStartDate()));
        appInstitution.setEndDate(new SimpleDateFormat("dd MMM yyyy").format(institution.getAccreditationEndDate()));

        //TODO RETURN A LIST OF ADDRESS
        appInstitution.setProvince(institution.getAddressList().get(0).getProvince().getDescription());
        appInstitution.setAddessLine1(institution.getAddressList().get(0).getAddressLine1());
        appInstitution.setAddessLine2(institution.getAddressList().get(0).getAddressLine2());
        appInstitution.setAddessLine3(institution.getAddressList().get(0).getAddressLine3());
        appInstitution.setAddessLine4(institution.getAddressList().get(0).getAddressLine4());
        appInstitution.setAccreditationNumber(institution.getAccreditationNumber());
        return appInstitution;
    }


    private AppInstitution mapAppInstitution(InstitutionCourse institutionCourse) {
        AppInstitution appInstitution = new AppInstitution();
        appInstitution.setId(institutionCourse.getInstitution().getId());
        appInstitution.setIntitutionType(institutionCourse.getInstitution().getInstitutionType().getDescription());
        appInstitution.setName(institutionCourse.getInstitution().getInstitutionName());
        appInstitution.setKnownAs(institutionCourse.getInstitution().getKnownAs());
        appInstitution.setContactNumber(institutionCourse.getInstitution().getContactNumber());
        appInstitution.setAlternativeContactNumber(institutionCourse.getInstitution().getAlternativeContactNumber());
        appInstitution.setEmail(institutionCourse.getInstitution().getEmail());
        appInstitution.setStartDate(new SimpleDateFormat("dd MMM yyyy").format(institutionCourse.getInstitution().getAccreditationStartDate()));
        appInstitution.setEndDate(new SimpleDateFormat("dd MMM yyyy").format(institutionCourse.getInstitution().getAccreditationEndDate()));

        //TODO RETURN A LIST OF ADDRESS
        appInstitution.setProvince(institutionCourse.getInstitution().getAddressList().get(0).getProvince().getDescription());
        appInstitution.setAddessLine1(institutionCourse.getInstitution().getAddressList().get(0).getAddressLine1());
        appInstitution.setAddessLine2(institutionCourse.getInstitution().getAddressList().get(0).getAddressLine2());
        appInstitution.setAddessLine3(institutionCourse.getInstitution().getAddressList().get(0).getAddressLine3());
        appInstitution.setAddessLine4(institutionCourse.getInstitution().getAddressList().get(0).getAddressLine4());
        appInstitution.setAccreditationNumber(institutionCourse.getInstitution().getAccreditationNumber());
        appInstitution.setCourseDesc(institutionCourse.getCourse().getDescription());
        appInstitution.setCourseLevel(institutionCourse.getCourse().getCourseLevel().getDescription());
        appInstitution.setCourseType(institutionCourse.getCourse().getCourseType().getDescription());
        return appInstitution;
    }

    private AppCourse mapAppCourse(InstitutionCourse institution) {
        Course course = institution.getCourse();
        return new AppCourse(course.getId(), course.getDescription(), institution.getId(), course.getCourseType().getDescription(), course.getDescription());
    }

    @RequestMapping(value = "/rest/sayHello/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String sayHello(@PathVariable("name") String name) {
        return "{message: hello " + name + "}";
    }

    @CrossOrigin(origins = "http://localhost")
    @RequestMapping(value = "/rest/report", method = RequestMethod.POST)
    @ResponseBody
    public String saveReport(@RequestBody AppReportedInstitution appReportedInstitution) {
        try {
            //addressService.saveAddress(mapObject(appReportedInstitution).getAddress());
            reportedInstutionService.saveReportedInstitutions(mapObject(appReportedInstitution));
            return appReportedInstitution.getInstitutionName() + " successfully reported";

        } catch (Exception err) {
            err.printStackTrace();
            return "Error couldn't save report " + err.getMessage();
        }
    }

    @RequestMapping(value = "/rest/findCourseMyInstitutionForAppId/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<AppCourse> findCourseByInstitutionId(@PathVariable("id") Long id) {
        List<InstitutionCourse> list = new ArrayList<>();
        List<AppCourse> listApps = new ArrayList<>();
        try {
            Institution institution = institutionService.findInstitutionById(id);
            list = institutionCourseService.findByInstitution(institution);
            for (InstitutionCourse course : list) {
                listApps.add(mapAppCourse(course));
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return listApps;
    }

    private ReportedInstitutions mapObject(AppReportedInstitution appReportedInstitution) {
        ReportedInstitutions reportedInstitutions = new ReportedInstitutions();
        Address address = new Address();
        address.setAddressLine1(appReportedInstitution.getCellNumber());
        address.setAddressLine2(appReportedInstitution.getProvince());
        address.setAddressLine3(appReportedInstitution.getAddressLineThree());
        address.setAddressLine4(appReportedInstitution.getCode());

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

}
