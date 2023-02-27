package com.pc.entities;

import com.pc.framework.IDataEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "mail_log")
public class MailLog implements IDataEntity {

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

    @Column(name = "from_email")
    private String fromEmail;

    @Column(name = "to_email")
    private String toEmail;

    @Column(name = "cc_email")
    private String ccEmails;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content_mssg", length = 5000)
    private String contentMssg;

    @Column(name = "error")
    private Boolean error;

    @Column(name = "error_message", length = 5000)
    private String errorMessage;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_update_user")
    private User lastUpdateUser;

    @Column(name = "deleted", nullable = false, columnDefinition = ("boolean default false"))
    private Boolean deleted;

    public MailLog() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MailLog(String fromEmail, String toEmail, String ccEmails, String subject, String contentMssg, Boolean error,
                   String errorMessage) {
        super();
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.ccEmails = ccEmails;
        this.subject = subject;
        this.contentMssg = contentMssg;
        this.error = error;
        this.errorMessage = errorMessage;
    }


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
        MailLog other = (MailLog) obj;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }


    public String getErrorMessage() {
        return errorMessage;
    }


    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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


    public String getFromEmail() {
        return fromEmail;
    }


    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }


    public String getToEmail() {
        return toEmail;
    }


    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }


    public String getCcEmails() {
        return ccEmails;
    }


    public void setCcEmails(String ccEmails) {
        this.ccEmails = ccEmails;
    }


    public String getContentMssg() {
        return contentMssg;
    }


    public void setContentMssg(String contentMssg) {
        this.contentMssg = contentMssg;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}