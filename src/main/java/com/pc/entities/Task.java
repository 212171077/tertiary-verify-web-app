package com.pc.entities;

import com.pc.entities.enums.ActionTakenEnum;
import com.pc.entities.enums.TastStatusEnum;
import com.pc.entities.lookup.WorkflowRole;
import com.pc.framework.IDataEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "task")
public class Task implements IDataEntity {


    private static final long serialVersionUID = 1L;
    @Transient
    List<User> userList;
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
    @JoinColumn(name = "last_update_user")
    private User lastUpdateUser;
    @Column(name = "description", length = 500)
    private String description;
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user")
    private User createUser;
    @Column(name = "target_class")
    private String targetClass;
    @Column(name = "target_key")
    private String targetKey;
    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WorkflowRole_id", nullable = true)
    private WorkflowRole workflowRole;
    @Column(name = "guide", length = 500)
    private String guide;
    @Enumerated
    @Column(name = "status")
    private TastStatusEnum status;
    @Enumerated
    @Column(name = "action_taken")
    private ActionTakenEnum actionTaken;

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
        Task other = (Task) obj;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }


    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public WorkflowRole getWorkflowRole() {
        return workflowRole;
    }

    public void setWorkflowRole(WorkflowRole workflowRole) {
        this.workflowRole = workflowRole;
    }

    public TastStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TastStatusEnum status) {
        this.status = status;
    }

    public ActionTakenEnum getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(ActionTakenEnum actionTaken) {
        this.actionTaken = actionTaken;
    }


}