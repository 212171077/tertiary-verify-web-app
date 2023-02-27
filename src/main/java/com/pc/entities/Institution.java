package com.pc.entities;

import com.pc.entities.enums.ApprovalStatusEnum;
import com.pc.entities.lookup.InstitutionType;
import com.pc.framework.IDataEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "institution", indexes = {@Index(name = "inst_name_idx", columnList = "institution_name"),
        @Index(name = "inst_known_as_idx", columnList = "known_as"),
        @Index(name = "inst_accre_num_idx", columnList = "accreditation_number")})
public class Institution implements IDataEntity {

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

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_user_id", nullable = true)
    private User creatorUser;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_user_id", nullable = true)
    private User supervisor;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institutionType_id", nullable = true)
    private InstitutionType institutionType;

    @Column(name = "institution_name")
    private String institutionName;

    @Column(name = "known_as")
    private String knownAs;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "alternative_contact_number")
    private String alternativeContactNumber;

    @Column(name = "email")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "accreditation_start_date", length = 19)
    private Date accreditationStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "accreditation_end_date", length = 19)
    private Date accreditationEndDate;

    @Column(name = "accreditation_number")
    private String accreditationNumber;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Address> addressList;

    @Enumerated
    @Column(name = "status")
    private ApprovalStatusEnum status;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "status_updated_by_scheduler")
    private Boolean statusUpdatedByScheduler;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_update_user")
    private User lastUpdateUser;

    @Column(name = "deleted", nullable = false, columnDefinition = ("boolean default false"))
    private Boolean deleted;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public User getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(User creatorUser) {
        this.creatorUser = creatorUser;
    }

    public InstitutionType getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(InstitutionType institutionType) {
        this.institutionType = institutionType;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getKnownAs() {
        return knownAs;
    }

    public void setKnownAs(String knownAs) {
        this.knownAs = knownAs;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAlternativeContactNumber() {
        return alternativeContactNumber;
    }

    public void setAlternativeContactNumber(String alternativeContactNumber) {
        this.alternativeContactNumber = alternativeContactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getAccreditationStartDate() {
        return accreditationStartDate;
    }

    public void setAccreditationStartDate(Date accreditationStartDate) {
        this.accreditationStartDate = accreditationStartDate;
    }

    public Date getAccreditationEndDate() {
        return accreditationEndDate;
    }

    public void setAccreditationEndDate(Date accreditationEndDate) {
        this.accreditationEndDate = accreditationEndDate;
    }

    public String getAccreditationNumber() {
        return accreditationNumber;
    }

    public void setAccreditationNumber(String accreditationNumber) {
        this.accreditationNumber = accreditationNumber;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public ApprovalStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatusEnum status) {
        this.status = status;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Boolean getStatusUpdatedByScheduler() {
        return statusUpdatedByScheduler;
    }

    public void setStatusUpdatedByScheduler(Boolean statusUpdatedByScheduler) {
        this.statusUpdatedByScheduler = statusUpdatedByScheduler;
    }

    public User getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(User lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}