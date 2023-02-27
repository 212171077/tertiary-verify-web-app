package com.pc.ui;

import com.pc.entities.DocConfig;
import com.pc.entities.enums.PermissionEnum;
import com.pc.entities.lookup.DocumentLevel;
import com.pc.entities.lookup.Workflow;
import com.pc.entities.lookup.WorkflowRole;
import com.pc.framework.AbstractUI;
import com.pc.service.DocConfigService;
import com.pc.service.lookup.DocumentLevelService;
import com.pc.service.lookup.WorkflowRoleService;
import com.pc.service.lookup.WorkflowService;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("docConfigUI")
@ViewScoped
public class DocConfigUI extends AbstractUI {

    @Autowired
    DocConfigService docConfigService;
    @Autowired
    WorkflowService workflowService;
    private ArrayList<DocConfig> docConfigList;
    private DocConfig docConfig;
    private TreeNode root;
    private DefaultDiagramModel model;
    private TreeNode selectedNode;
    private ArrayList<Workflow> workflowList;
    private Boolean expanded;

    @Autowired
    private WorkflowRoleService workflowRoleService;

    @Autowired
    private DocumentLevelService documentLevelSevice;
    private DocumentLevel documentLevel;
    private List<DocumentLevel> documentLevelList;

    @PostConstruct
    public void init() {
        try {
            docConfig = new DocConfig();
            docConfigList = (ArrayList<DocConfig>) findAllDocConfig();
            expanded = true;
            prepareWorkflowData();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void prepareWorkflowData() throws Exception {
        root = new DefaultTreeNode("Root", "Root", null);
        root.setExpanded(expanded);
        workflowList = (ArrayList<Workflow>) findAllWorkflowResolveWorkflowRoles();
        for (Workflow wf : workflowList) {
            TreeNode workflowNode = new DefaultTreeNode("Workflow", wf, root);
            workflowNode.setExpanded(expanded);
            List<DocumentLevel> dlList = documentLevelSevice.findAllDocumentLevel();
            for (DocumentLevel dl : dlList) {
                DocumentLevel dlv = new DocumentLevel();
                dlv.setId(dl.getId());
                dlv.setCode(dl.getCode());
                dlv.setCreateDate(dl.getCreateDate());
                dlv.setDescription(dl.getDescription());
                dlv.setWorkflow(wf);
                TreeNode documentLevelNode = new DefaultTreeNode("DocumentLevel", dlv, workflowNode);
                documentLevelNode.setExpanded(expanded);
                List<DocConfig> configDocList = docConfigService.findByWorkflowAndDocumentLevelOrderByDescriptionAsc(wf, dl);
                for (DocConfig dc : configDocList) {
                    TreeNode docConfigNode = new DefaultTreeNode("DocConfig", dc, documentLevelNode);
                    docConfigNode.setExpanded(expanded);
                }
            }

        }
    }


    public List<WorkflowRole> getWorkflowRoles() {
        List<WorkflowRole> workflowRoleList = new ArrayList<>();
        if (selectedNode != null && selectedNode.getData() instanceof DocumentLevel) {
            DocumentLevel dl = (DocumentLevel) selectedNode.getData();
            workflowRoleList = workflowRoleService.getUploadWorkflowRoles(dl.getWorkflow(), PermissionEnum.Upload, PermissionEnum.EditAndUpload);

        } else if (selectedNode != null && selectedNode.getData() instanceof DocConfig) {
            DocConfig dc = (DocConfig) selectedNode.getData();
            workflowRoleList = workflowRoleService.getUploadWorkflowRoles(dc.getWorkflow(), PermissionEnum.Upload, PermissionEnum.EditAndUpload);
        }


        return workflowRoleList;
    }

    public List<Workflow> findAllWorkflowResolveWorkflowRoles() {
        List<Workflow> list = new ArrayList<>();
        try {
            list = workflowService.findAllWorkflowResolveWorkflowRoles();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public void saveDocConfig() {
        try {
            if (selectedNode.getData() instanceof DocumentLevel) {
                DocumentLevel dl = (DocumentLevel) selectedNode.getData();
                docConfig.setDocumentLevel(dl);
                docConfig.setWorkflow(dl.getWorkflow());
                docConfigService.saveDocConfig(docConfig);
                displayInfoMssg("Document added successful...!!");
                prepareWorkflowData();
                reset();
            } else {
                docConfigService.saveDocConfig(docConfig);
                displayInfoMssg("Document update Successful...!!");
                prepareWorkflowData();
                reset();
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }


    public void saveDocConfigOld() {
        try {
            docConfigService.saveDocConfig(docConfig);
            displayInfoMssg("Update Successful...!!");
            docConfigList = (ArrayList<DocConfig>) findAllDocConfig();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }


    public void prepareDocConfigUpdate() {
        try {
            if (selectedNode.getData() instanceof DocConfig) {
                docConfig = (DocConfig) selectedNode.getData();
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteDocConfig() {
        try {
            if (selectedNode.getData() instanceof DocConfig) {
                docConfig = (DocConfig) selectedNode.getData();
                docConfigService.deleteDocConfig(docConfig);
                displayWarningMssg("Document deleted successful...!!");
                prepareWorkflowData();
                reset();
            }

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteDocConfig_Old() {
        try {
            docConfigService.deleteDocConfig(docConfig);
            displayWarningMssg("Update Successful...!!");
            docConfigList = (ArrayList<DocConfig>) findAllDocConfig();
            reset();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<DocConfig> findAllDocConfig() {
        List<DocConfig> list = new ArrayList<>();
        try {
            list = docConfigService.findAllDocConfig();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Page<DocConfig> findAllDocConfigPageable() {
        Pageable p = null;
        try {
            return docConfigService.findAllDocConfig(p);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<DocConfig> findAllDocConfigSort() {
        Sort s = null;
        List<DocConfig> list = new ArrayList<>();
        try {
            list = docConfigService.findAllDocConfig(s);
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public void reset() {
        docConfig = new DocConfig();
    }


    public DocConfig getDocConfig() {
        return docConfig;
    }

    public void setDocConfig(DocConfig docConfig) {
        this.docConfig = docConfig;
    }

    public ArrayList<DocConfig> getDocConfigList() {
        return docConfigList;
    }

    public void setDocConfigList(ArrayList<DocConfig> docConfigList) {
        this.docConfigList = docConfigList;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public DefaultDiagramModel getModel() {
        return model;
    }

    public void setModel(DefaultDiagramModel model) {
        this.model = model;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public ArrayList<Workflow> getWorkflowList() {
        return workflowList;
    }

    public void setWorkflowList(ArrayList<Workflow> workflowList) {
        this.workflowList = workflowList;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public DocumentLevel getDocumentLevel() {
        return documentLevel;
    }

    public void setDocumentLevel(DocumentLevel documentLevel) {
        this.documentLevel = documentLevel;
    }

    public List<DocumentLevel> getDocumentLevelList() {
        return documentLevelList;
    }

    public void setDocumentLevelList(List<DocumentLevel> documentLevelList) {
        this.documentLevelList = documentLevelList;
    }

}
