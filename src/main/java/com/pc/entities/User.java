package com.pc.entities;

import com.pc.entities.enums.UsersStatusEnum;
import com.pc.entities.lookup.Gender;
import com.pc.entities.lookup.Title;
import com.pc.framework.IDataEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "users", indexes = {@Index(name = "users_email_idx", columnList = "email"),
        @Index(name = "users_username_idx", columnList = "username")})
public class User implements IDataEntity {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_date", length = 19)
  private Date createDate;

  @Column(name = "last_update_date", length = 19)
  private Date lastUpdateDate;

  @Column(name = "last_login_date", length = 19)
  private Date lastLoginDate;

  @Column(name = "rsa_id", length = 20, nullable = true)
  private String rsaId;

  @Column(name = "date_of_birth", nullable = true)
  private Date dob;


  @Fetch(FetchMode.JOIN)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "title_id", nullable = true)
  private Title title;

  @Fetch(FetchMode.JOIN)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "gender_id", nullable = true)
  private Gender gender;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "surname", nullable = false)
  private String surname;

  @Column(name = "cell_number", nullable = false)
  private String cellNumber;

  @Column(name = "email", nullable = true)
  private String email;

  @Column(name = "username", nullable = true, length = 250)
  private String username;

  @Column(name = "password", nullable = true, length = 250)
  private String password;

  @Column(name = "enabled")
  private Boolean enabled;

  @Enumerated
  @Column(name = "status", nullable = true)
  private UsersStatusEnum status;

  @Fetch(FetchMode.JOIN)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "residential_address", nullable = true)
  private Address residentialAddress;

  @Fetch(FetchMode.JOIN)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "postal_address", nullable = true)
  private Address postalAddress;

  @Fetch(FetchMode.JOIN)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_user_id", nullable = true)
  private User creatorUser;

  @Fetch(FetchMode.JOIN)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "image_id", insertable = true, updatable = true, nullable = true)
  private ImageModel image;

  @Column(name = "change_password")
  private Boolean changePassword;

  @Fetch(FetchMode.JOIN)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "last_update_user")
  private User lastUpdateUser;

  @Column(name = "deleted", nullable = false, columnDefinition = ("boolean default false"))
  private Boolean deleted;


  @Transient
  private ArrayList<UserRole> userRole;

  @Transient
  private String[] roles;

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
    User other = (User) obj;
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

  public String getRsaId() {
    return rsaId;
  }

  public void setRsaId(String rsaId) {
    this.rsaId = rsaId;
  }

  public Title getTitle() {
    return title;
  }

  public void setTitle(Title title) {
    this.title = title;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getCellNumber() {
    return cellNumber;
  }

  public void setCellNumber(String cellNumber) {
    this.cellNumber = cellNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UsersStatusEnum getStatus() {
    return status;
  }

  public void setStatus(UsersStatusEnum status) {
    this.status = status;
  }

  public User getCreatorUser() {
    return creatorUser;
  }

  public void setCreatorUser(User creatorUser) {
    this.creatorUser = creatorUser;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public ArrayList<UserRole> getUserRole() {
    return userRole;
  }

  public void setUserRole(ArrayList<UserRole> userRole) {
    this.userRole = userRole;
  }

  public String[] getRoles() {
    return roles;
  }

  public void setRoles(String[] roles) {
    this.roles = roles;
  }

  public Address getPostalAddress() {
    return postalAddress;
  }

  public void setPostalAddress(Address postalAddress) {
    this.postalAddress = postalAddress;
  }

  public Address getResidentialAddress() {
    return residentialAddress;
  }

  public void setResidentialAddress(Address residentialAddress) {
    this.residentialAddress = residentialAddress;
  }

  public ImageModel getImage() {
    return image;
  }

  public void setImage(ImageModel image) {
    this.image = image;
  }


  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public Boolean getChangePassword() {
    return changePassword;
  }

  public void setChangePassword(Boolean changePassword) {
    this.changePassword = changePassword;
  }

  public Date getLastLoginDate() {
    return lastLoginDate;
  }

  public void setLastLoginDate(Date lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
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
