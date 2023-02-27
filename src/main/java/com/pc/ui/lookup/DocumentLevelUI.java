package com.pc.ui.lookup;

import com.pc.entities.lookup.DocumentLevel;
import com.pc.framework.AbstractUI;
import com.pc.service.lookup.DocumentLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("documentLevelUI")
@ViewScoped
public class DocumentLevelUI extends AbstractUI {

    @Autowired
    DocumentLevelService documentLevelService;
    private ArrayList<DocumentLevel> documentLevelList;

    private DocumentLevel documentLevel;

    @PostConstruct
    public void init() {
        documentLevel = new DocumentLevel();
        documentLevelList = (ArrayList<DocumentLevel>) findAllDocumentLevel();
    }

    public void saveDocumentLevel() {
        try {
            documentLevelService.saveDocumentLevel(documentLevel);
            displayInfoMssg("Update Successful...!!");
            documentLevelList = (ArrayList<DocumentLevel>) findAllDocumentLevel();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteDocumentLevel() {
        try {
            documentLevelService.deleteDocumentLevel(documentLevel);
            displayWarningMssg("Update Successful...!!");
            documentLevelList = (ArrayList<DocumentLevel>) findAllDocumentLevel();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<DocumentLevel> findAllDocumentLevel() {
        List<DocumentLevel> list = new ArrayList<>();
        try {
            list = documentLevelService.findAllDocumentLevel();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<DocumentLevel> findAllDocumentLevelPageable() {
        Pageable p = null;
        try {
            return documentLevelService.findAllDocumentLevel(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<DocumentLevel> findAllDocumentLevelSort() {
        Sort s = null;
        List<DocumentLevel> list = new ArrayList<>();
        try {
            list = documentLevelService.findAllDocumentLevel(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        documentLevel = new DocumentLevel();
    }


    public DocumentLevel getDocumentLevel() {
        return documentLevel;
    }

    public void setDocumentLevel(DocumentLevel documentLevel) {
        this.documentLevel = documentLevel;
    }

    public ArrayList<DocumentLevel> getDocumentLevelList() {
        return documentLevelList;
    }

    public void setDocumentLevelList(ArrayList<DocumentLevel> documentLevelList) {
        this.documentLevelList = documentLevelList;
    }

}
