package com.pc.ui;

import com.pc.entities.UserRole;
import com.pc.entities.lookup.EmailTemplate;
import com.pc.entities.lookup.WorkFlowRoleUser;
import com.pc.entities.lookup.Workflow;
import com.pc.entities.lookup.WorkflowRole;
import com.pc.framework.AbstractUI;
import com.pc.service.UserRoleService;
import com.pc.service.lookup.WorkFlowRoleUserService;
import com.pc.service.lookup.WorkflowRoleService;
import com.pc.service.lookup.WorkflowService;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component("workflowConfigUI")
@ViewScoped
public class WorkflowConfigUI extends AbstractUI {

    @Autowired
    WorkflowService workflowService;
    @Autowired
    WorkflowRoleService workflowRoleService;
    @Autowired
    WorkFlowRoleUserService workFlowRoleUserService;
    @Autowired
    UserRoleService userRoleService;
    private TreeNode root;
    private DefaultDiagramModel model;
    private TreeNode selectedNode;
    private ArrayList<Workflow> workflowList;
    private Workflow workflow;
    private WorkflowRole workflowRole;
    private List<WorkflowRole> workflowRoleList;
    private List<EmailTemplate> emailTemplateList;
    private ArrayList<WorkFlowRoleUser> workFlowRoleUserList;
    private WorkFlowRoleUser workFlowRoleUser;
    private List<UserRole> userRoleList;

    private Boolean expanded;

    @PostConstruct
    public void init() {

        try {
            workflow = new Workflow();
            workflowRole = new WorkflowRole();
            workflowRoleList = new ArrayList<>();
            workFlowRoleUser = new WorkFlowRoleUser();
            expanded = true;
            prepareWorkflowData();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void prepareWorkflowData() throws Exception {
        root = new DefaultTreeNode("Workflow", "Worklow", null);
        root.setExpanded(expanded);
        workflowList = (ArrayList<Workflow>) findAllWorkflowResolveWorkflowRoles();
        for (Workflow wf : workflowList) {
            TreeNode workflowNode = new DefaultTreeNode("WorkflowRole", wf, root);
            workflowNode.setExpanded(expanded);
            for (WorkflowRole wfr : wf.getWorkflowRoleRoleList()) {

                TreeNode workflowRoleNode = new DefaultTreeNode("WorkflowRoleUser", wfr, workflowNode);
                workflowRoleNode.setExpanded(expanded);
                List<WorkFlowRoleUser> workFlowRoleUserList = workFlowRoleUserService.findByWorkflowRole(wfr);
                for (WorkFlowRoleUser wru : workFlowRoleUserList) {
                    TreeNode workflowRoleUserNode = new DefaultTreeNode("WorkflowRoleUserLast", wru, workflowRoleNode);
                    workflowRoleUserNode.setExpanded(expanded);
                }
            }
			/*TreeNode workflowRole = new DefaultTreeNode("Node 1.0", workflowNode);
			workflowNode.getChildren().add(new DefaultTreeNode("Node 1.1"));
			workflowRole.getChildren().add(new DefaultTreeNode("Node 1.0.0"));*/
        }
    }


    public void prepareUserRoles() {
        try {
            if (selectedNode.getData() instanceof WorkflowRole) {
                WorkflowRole wsr = (WorkflowRole) selectedNode.getData();
                userRoleList = userRoleService.findByRole(wsr.getRole());
                if (userRoleList != null && userRoleList.size() <= 0) {
                    displayWarningMssg("No users with " + wsr.getRole().getDescription() + " role found");
                }
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveWorkFlowRoleUser() {
        try {
            if (selectedNode.getData() instanceof WorkflowRole) {
                workFlowRoleUser.setWorkflowRole((WorkflowRole) selectedNode.getData());
                workFlowRoleUserService.saveWorkFlowRoleUser(workFlowRoleUser);
                displayInfoMssg("Update Successful...!!");
                prepareWorkflowData();
                resetWorkFlowRoleUser();
            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteWorkFlowRoleUser() {
        try {
            if (selectedNode.getData() instanceof WorkFlowRoleUser) {
                workFlowRoleUserService.deleteWorkFlowRoleUser((WorkFlowRoleUser) selectedNode.getData());
                displayWarningMssg("Update Successful...!!");
                prepareWorkflowData();
                resetWorkFlowRoleUser();
            }

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void resetWorkFlowRoleUser() {
        workFlowRoleUser = new WorkFlowRoleUser();
    }

    public void saveWorkflow() {
        try {
            workflowService.saveWorkflow(workflow);
            displayInfoMssg("Update Successful...!!");
            prepareWorkflowData();
            resetWorkflow();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteWorkflow() {
        try {
            if (selectedNode.getData() instanceof Workflow) {
                workflow = (Workflow) selectedNode.getData();
                workflowService.deleteWorkflow(workflow);
                displayWarningMssg("Update Successful...!!");
                prepareWorkflowData();
                resetWorkflow();
            }

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
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

    public void saveWorkflowRole() {
        try {

            workflowRole.setWorkflow((Workflow) selectedNode.getData());
            workflowRoleService.saveWorkflowRole(workflowRole);
            prepareWorkflowRoleList();
            prepareWorkflowData();
            displayInfoMssg("Update Successful...!!");
            resetWorkflowRole();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteWorkflowRole() {
        try {
            if (selectedNode.getData() instanceof WorkflowRole) {
                WorkflowRole wfr = ((WorkflowRole) selectedNode.getData());
                workflowRoleService.deleteWorkflowRole(wfr);
                workflowRoleList = workflowRoleService.findByWorkflowOrderByPosition(wfr.getWorkflow());
                reorderPositions();
                displayWarningMssg("Update Successful...!!");
                prepareWorkflowData();
                resetWorkflowRole();
            }

        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void resetWorkflowRole() {
        workflowRole = new WorkflowRole();
    }

    public void prepareWorkflowRoleList() {
        try {
            workflowRoleList = new ArrayList<>();
            if (selectedNode != null) {
                if (selectedNode.getData() instanceof Workflow) {
                    Workflow wf = (Workflow) selectedNode.getData();
                    workflowRoleList = workflowRoleService.findByWorkflowOrderByPosition(wf);
                    emailTemplateList = wf.getEmailTemplateList();
                }


            }
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void resetWorkflow() {
        workflow = new Workflow();
    }

    public void createWorkflowRoleDesc() {
        if (workflowRole.getRole() != null && workflowRole.getPermission() != null) {
            workflowRole.setDescription(workflowRole.getRole().getDescription() + "(" + workflowRole.getPermission().getFriendlyName() + ")");
        }
    }

    public void onSelect(SelectEvent event) {
        if (event.getObject() instanceof WorkflowRole) {
            workflowRole = (WorkflowRole) event.getObject();
        }

    }

    public void onUnselect(UnselectEvent event) {
        //displayInfoMssg( "Item Unselected"+ event.getObject().toString());
    }

    public void onReorder() {
        try {
            reorderPositions();
            prepareWorkflowData();
            prepareWorkflowRoleList();
        } catch (Exception e) {
            displayErrorMssg(e.getMessage());
            e.printStackTrace();
        }
    }

    public void reorderPositions() throws Exception {

        for (WorkflowRole wfr : workflowRoleList) {
            wfr.setPosition(workflowRoleList.indexOf(wfr) + 1);
            workflowRoleService.saveWorkflowRole(wfr);
        }
    }


    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public DiagramModel getModel() {
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

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public WorkflowRole getWorkflowRole() {
        return workflowRole;
    }

    public void setWorkflowRole(WorkflowRole workflowRole) {
        this.workflowRole = workflowRole;
    }

    public List<WorkflowRole> getWorkflowRoleList() {
        return workflowRoleList;
    }

    public void setWorkflowRoleList(List<WorkflowRole> workflowRoleList) {
        this.workflowRoleList = workflowRoleList;
    }

    public List<EmailTemplate> getEmailTemplateList() {
        return emailTemplateList;
    }

    public void setEmailTemplateList(List<EmailTemplate> emailTemplateList) {
        this.emailTemplateList = emailTemplateList;
    }

    public ArrayList<WorkFlowRoleUser> getWorkFlowRoleUserList() {
        return workFlowRoleUserList;
    }

    public void setWorkFlowRoleUserList(ArrayList<WorkFlowRoleUser> workFlowRoleUserList) {
        this.workFlowRoleUserList = workFlowRoleUserList;
    }

    public WorkFlowRoleUser getWorkFlowRoleUser() {
        return workFlowRoleUser;
    }

    public void setWorkFlowRoleUser(WorkFlowRoleUser workFlowRoleUser) {
        this.workFlowRoleUser = workFlowRoleUser;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}