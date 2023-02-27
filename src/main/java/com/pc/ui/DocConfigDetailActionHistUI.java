package com.pc.ui;

import com.pc.entities.DocConfigDetailActionHist;
import com.pc.framework.AbstractUI;
import com.pc.service.DocConfigDetailActionHistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("docConfigDetailActionHistUI")
@ViewScoped
public class DocConfigDetailActionHistUI extends AbstractUI {

    @Autowired
    DocConfigDetailActionHistService docConfigDetailActionHistService;
    private ArrayList<DocConfigDetailActionHist> docConfigDetailActionHistList;

    private DocConfigDetailActionHist docConfigDetailActionHist;

    @PostConstruct
    public void init() {
        docConfigDetailActionHist = new DocConfigDetailActionHist();
        docConfigDetailActionHistList = (ArrayList<DocConfigDetailActionHist>) findAllDocConfigDetailActionHist();
    }

    public void saveDocConfigDetailActionHist() {
        try {
            docConfigDetailActionHistService.saveDocConfigDetailActionHist(docConfigDetailActionHist);
            displayInfoMssg("Update Successful...!!");
            docConfigDetailActionHistList = (ArrayList<DocConfigDetailActionHist>) findAllDocConfigDetailActionHist();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteDocConfigDetailActionHist() {
        try {
            docConfigDetailActionHistService.deleteDocConfigDetailActionHist(docConfigDetailActionHist);
            displayWarningMssg("Update Successful...!!");
            docConfigDetailActionHistList = (ArrayList<DocConfigDetailActionHist>) findAllDocConfigDetailActionHist();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<DocConfigDetailActionHist> findAllDocConfigDetailActionHist() {
        List<DocConfigDetailActionHist> list = new ArrayList<>();
        try {
            list = docConfigDetailActionHistService.findAllDocConfigDetailActionHist();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<DocConfigDetailActionHist> findAllDocConfigDetailActionHistPageable() {
        Pageable p = null;
        try {
            return docConfigDetailActionHistService.findAllDocConfigDetailActionHist(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<DocConfigDetailActionHist> findAllDocConfigDetailActionHistSort() {
        Sort s = null;
        List<DocConfigDetailActionHist> list = new ArrayList<>();
        try {
            list = docConfigDetailActionHistService.findAllDocConfigDetailActionHist(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        docConfigDetailActionHist = new DocConfigDetailActionHist();
    }


    public DocConfigDetailActionHist getDocConfigDetailActionHist() {
        return docConfigDetailActionHist;
    }

    public void setDocConfigDetailActionHist(DocConfigDetailActionHist docConfigDetailActionHist) {
        this.docConfigDetailActionHist = docConfigDetailActionHist;
    }

    public ArrayList<DocConfigDetailActionHist> getDocConfigDetailActionHistList() {
        return docConfigDetailActionHistList;
    }

    public void setDocConfigDetailActionHistList(ArrayList<DocConfigDetailActionHist> docConfigDetailActionHistList) {
        this.docConfigDetailActionHistList = docConfigDetailActionHistList;
    }

}
