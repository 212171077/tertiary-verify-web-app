package com.pc.entities;

import com.pc.entities.enums.ReportTypeEnum;
import com.pc.entities.enums.ReportedInstitutionsStatus;
import com.pc.framework.IDataEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "reported_institutions", indexes = {@Index(name = "rep_inst_name_idx", columnList = "institution_name"),
        @Index(name = "rep_inst_known_as_idx", columnList = "known_as"),
        @Index(name = "rep_inst_ref_num_idx", columnList = "ref_number")})
public class ReportedInstitutions implements IDataEntity {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", length = 19)
    private Date createDate;

    @Column(name = "last_update_date", length = 19)
    private Date lastUpdateDate;

    @Enumerated
    @Column(name = "report_type")
    private ReportTypeEnum reportType;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "known_as")
    private String knownAs;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address", nullable = true)
    private Address address;

    @Column(name = "ref_number")
    private String refNumber;

    @Column(name = "investigator_feedback", length = 5000)
    private String investigatorFeedback;

    @Enumerated
    @Column(name = "status")
    private ReportedInstitutionsStatus status;

    @Column(name = "deleted", nullable = false, columnDefinition = ("boolean default false"))
    private Boolean deleted;

    /**
     * Report Information
     **/

    @Column(name = "reporter_name")
    private String reporterName;

    @Column(name = "reporter_surname")
    private String reporterSurname;

    @Column(name = "reporter_email")
    private String reporterEmail;

    @Column(name = "reporter_cell")
    private String reporterCell;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investigator_id", nullable = true)
    private User investigator;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id", nullable = true)
    private Institution institution;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_update_user")
    private User lastUpdateUser;


    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ReportedInstitutions other = (ReportedInstitutions) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the reportType
     */
    public ReportTypeEnum getReportType() {
        return reportType;
    }

    /**
     * @param reportType the reportType to set
     */
    public void setReportType(ReportTypeEnum reportType) {
        this.reportType = reportType;
    }

    /**
     * @return the institutionName
     */
    public String getInstitutionName() {
        return institutionName;
    }

    /**
     * @param institutionName the institutionName to set
     */
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return the refNumber
     */
    public String getRefNumber() {
        return refNumber;
    }

    /**
     * @param refNumber the refNumber to set
     */
    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    /**
     * @return the status
     */
    public ReportedInstitutionsStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(ReportedInstitutionsStatus status) {
        this.status = status;
    }

    /**
     * @return the investigatorFeedback
     */
    public String getInvestigatorFeedback() {
        return investigatorFeedback;
    }

    /**
     * @param investigatorFeedback the investigatorFeedback to set
     */
    public void setInvestigatorFeedback(String investigatorFeedback) {
        this.investigatorFeedback = investigatorFeedback;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterSurname() {
        return reporterSurname;
    }

    public void setReporterSurname(String reporterSurname) {
        this.reporterSurname = reporterSurname;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }

    public String getReporterCell() {
        return reporterCell;
    }

    public void setReporterCell(String reporterCell) {
        this.reporterCell = reporterCell;
    }

    public String getKnownAs() {
        return knownAs;
    }

    public void setKnownAs(String knownAs) {
        this.knownAs = knownAs;
    }

    public User getInvestigator() {
        return investigator;
    }

    public void setInvestigator(User investigator) {
        this.investigator = investigator;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public User getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(User lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}