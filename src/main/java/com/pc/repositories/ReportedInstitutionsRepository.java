package com.pc.repositories;

import com.pc.beans.ReportedInstitutionBean;
import com.pc.entities.ReportedInstitutions;
import com.pc.entities.User;
import com.pc.entities.enums.ReportedInstitutionsStatus;
import com.pc.entities.lookup.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportedInstitutionsRepository extends JpaRepository<ReportedInstitutions, Integer> {

    @Query("SELECT new com.pc.beans.ReportedInstitutionBean(o.institutionName, COUNT(o)) "
            + "FROM ReportedInstitutions o GROUP BY o.institutionName")
    List<ReportedInstitutionBean> getReportedInstitutionBean();

    @Query("SELECT new com.pc.beans.ReportedInstitutionBean(o.institutionName, COUNT(o)) "
            + "FROM ReportedInstitutions o WHERE YEAR(o.createDate) = ?1 GROUP BY o.institutionName")
    List<ReportedInstitutionBean> getReportedInstitutionBean(int year);

    @Query("SELECT new com.pc.beans.ReportedInstitutionBean(o.institutionName, COUNT(o)) "
            + "FROM ReportedInstitutions o WHERE YEAR(o.createDate) = ?1 AND MONTH(o.createDate) = ?2 GROUP BY o.institutionName")
    List<ReportedInstitutionBean> getReportedInstitutionBean(int year, int mon);

    @Query("SELECT new com.pc.beans.ReportedInstitutionBean(o.institutionName, COUNT(o)) "
            + "FROM ReportedInstitutions o WHERE o.address.province = ?1 AND YEAR(o.createDate) = ?2 AND MONTH(o.createDate) = ?3 GROUP BY o.institutionName")
    List<ReportedInstitutionBean> getReportedInstitutionBean(Province province, int year, int mon);

    @Query("SELECT new com.pc.beans.ReportedInstitutionBean(o.institutionName, COUNT(o)) "
            + "FROM ReportedInstitutions o WHERE o.address.province = ?1 AND YEAR(o.createDate) = ?2 GROUP BY o.institutionName")
    List<ReportedInstitutionBean> getReportedInstitutionBean(Province province, int year);

    @Query("SELECT new com.pc.beans.ReportedInstitutionBean(o.institutionName, COUNT(o)) "
            + "FROM ReportedInstitutions o WHERE o.address.province = ?1 AND MONTH(o.createDate) = ?2 GROUP BY o.institutionName")
    List<ReportedInstitutionBean> getReportedInstitutionBeanByMonAndProvince(Province province, int mon);

    @Query("SELECT new com.pc.beans.ReportedInstitutionBean(o.institutionName, COUNT(o)) "
            + "FROM ReportedInstitutions o WHERE o.address.province = ?1 GROUP BY o.institutionName")
    List<ReportedInstitutionBean> getReportedInstitutionBean(Province province);

    ReportedInstitutions findByRefNumber(String refNumber);

    List<ReportedInstitutions> findByInvestigatorAndStatusNot(User investigator, ReportedInstitutionsStatus status);

    long count();

    long countByStatus(ReportedInstitutionsStatus status);

    long countByInvestigator(User investigator);

}
