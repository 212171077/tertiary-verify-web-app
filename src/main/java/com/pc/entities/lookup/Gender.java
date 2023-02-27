package com.pc.entities.lookup;

import com.pc.entities.User;
import com.pc.framework.IDataEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "gender", indexes = {@Index(name = "gender_code_idx", columnList = "code"),
        @Index(name = "gender_g_name_idx", columnList = "gender_name")})
public class Gender implements IDataEntity {


    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;


    @Column(name = "gender_name", length = 10)
    private String genderName;


    @Column(name = "code", length = 10)
    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", length = 19)
    private Date createDate;

    @Column(name = "last_update_date", length = 19)
    private Date lastUpdateDate;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_update_user")
    private User lastUpdateUser;

    @Column(name = "deleted", nullable = false, columnDefinition = ("boolean default false"))
    private Boolean deleted;

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Gender other = (Gender) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }


    public String getGenderName() {
        return genderName;
    }


    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    @Override
    public String toString() {
        return "Gender [id=" + id + ", genderName=" + genderName + ", code=" + code + ", createDate=" + createDate
                + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
