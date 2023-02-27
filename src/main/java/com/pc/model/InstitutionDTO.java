package com.pc.model;

// {id:1,title: 'University of Cape Town', tel: '01235658988',accreditationNumber:"2018/956/22",province:"Mpumalanga",thumb:'assets/img/trip/thumb/trip_1.jpg'},

public class InstitutionDTO {

    private Long id;
    private String title;
    private String tel;
    private String accreditationNumber;
    private String province;
    private String thumb;

    public InstitutionDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public InstitutionDTO(Long id, String title, String tel, String accreditationNumber, String province, String thumb) {
        super();
        this.id = id;
        this.title = title;
        this.tel = tel;
        this.accreditationNumber = accreditationNumber;
        this.province = province;
        this.thumb = thumb;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            title = "";
        }
        this.title = title;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        if (tel == null) {
            tel = "";
        }
        this.tel = tel;
    }

    public String getAccreditationNumber() {
        return accreditationNumber;
    }

    public void setAccreditationNumber(String accreditationNumber) {
        if (accreditationNumber == null) {
            accreditationNumber = "";
        }
        this.accreditationNumber = accreditationNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        if (province == null) {
            province = "";
        }
        this.province = province;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        if (thumb == null) {
            thumb = "";
        }
        this.thumb = thumb;
    }

    @Override
    public String toString() {
        return "InstitutionDTO [id=" + id + ", title=" + title + ", tel=" + tel + ", accreditationNumber="
                + accreditationNumber + ", province=" + province + ", thumb=" + thumb + ", errorMessage="
                + "]";
    }


}
