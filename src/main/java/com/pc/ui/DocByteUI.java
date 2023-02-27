package com.pc.ui;

import com.pc.entities.DocByte;
import com.pc.framework.AbstractUI;
import com.pc.service.DocByteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("docByteUI")
@ViewScoped
public class DocByteUI extends AbstractUI {

    @Autowired
    DocByteService docByteService;
    private ArrayList<DocByte> docByteList;

    private DocByte docByte;

    @PostConstruct
    public void init() {
        docByte = new DocByte();
        docByteList = (ArrayList<DocByte>) findAllDocByte();
    }

    public void saveDocByte() {
        try {
            docByteService.saveDocByte(docByte);
            displayInfoMssg("Update Successful...!!");
            docByteList = (ArrayList<DocByte>) findAllDocByte();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteDocByte() {
        try {
            docByteService.deleteDocByte(docByte);
            displayWarningMssg("Update Successful...!!");
            docByteList = (ArrayList<DocByte>) findAllDocByte();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<DocByte> findAllDocByte() {
        List<DocByte> list = new ArrayList<>();
        try {
            list = docByteService.findAllDocByte();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<DocByte> findAllDocBytePageable() {
        Pageable p = null;
        try {
            return docByteService.findAllDocByte(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<DocByte> findAllDocByteSort() {
        Sort s = null;
        List<DocByte> list = new ArrayList<>();
        try {
            list = docByteService.findAllDocByte(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        docByte = new DocByte();
    }


    public DocByte getDocByte() {
        return docByte;
    }

    public void setDocByte(DocByte docByte) {
        this.docByte = docByte;
    }

    public ArrayList<DocByte> getDocByteList() {
        return docByteList;
    }

    public void setDocByteList(ArrayList<DocByte> docByteList) {
        this.docByteList = docByteList;
    }

}
