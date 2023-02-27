package com.pc.ui;

import com.pc.entities.DocConfigDetail;
import com.pc.framework.AbstractUI;
import com.pc.service.DocConfigDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("docConfigDetailUI")
@ViewScoped
public class DocConfigDetailUI extends AbstractUI {

    @Autowired
    DocConfigDetailService docConfigDetailService;
    private ArrayList<DocConfigDetail> docConfigDetailList;

    private DocConfigDetail docConfigDetail;

    @PostConstruct
    public void init() {
        docConfigDetail = new DocConfigDetail();
        docConfigDetailList = (ArrayList<DocConfigDetail>) findAllDocConfigDetail();
    }

    public void saveDocConfigDetail() {
        try {
            docConfigDetailService.saveDocConfigDetail(docConfigDetail);
            displayInfoMssg("Update Successful...!!");
            docConfigDetailList = (ArrayList<DocConfigDetail>) findAllDocConfigDetail();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteDocConfigDetail() {
        try {
            docConfigDetailService.deleteDocConfigDetail(docConfigDetail);
            displayWarningMssg("Update Successful...!!");
            docConfigDetailList = (ArrayList<DocConfigDetail>) findAllDocConfigDetail();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<DocConfigDetail> findAllDocConfigDetail() {
        List<DocConfigDetail> list = new ArrayList<>();
        try {
            list = docConfigDetailService.findAllDocConfigDetail();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<DocConfigDetail> findAllDocConfigDetailPageable() {
        Pageable p = null;
        try {
            return docConfigDetailService.findAllDocConfigDetail(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<DocConfigDetail> findAllDocConfigDetailSort() {
        Sort s = null;
        List<DocConfigDetail> list = new ArrayList<>();
        try {
            list = docConfigDetailService.findAllDocConfigDetail(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        docConfigDetail = new DocConfigDetail();
    }


    public DocConfigDetail getDocConfigDetail() {
        return docConfigDetail;
    }

    public void setDocConfigDetail(DocConfigDetail docConfigDetail) {
        this.docConfigDetail = docConfigDetail;
    }

    public ArrayList<DocConfigDetail> getDocConfigDetailList() {
        return docConfigDetailList;
    }

    public void setDocConfigDetailList(ArrayList<DocConfigDetail> docConfigDetailList) {
        this.docConfigDetailList = docConfigDetailList;
    }

}
