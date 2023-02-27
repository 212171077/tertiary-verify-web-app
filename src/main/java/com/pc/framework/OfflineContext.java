package com.pc.framework;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * The Class OfflineContext.
 */
public class OfflineContext extends javax.faces.context.FacesContext {

    /**
     * The instance.
     */
    private static OfflineContext instance = null;

    /**
     * The list messages.
     */
    private List<FacesMessage> listMessages = new ArrayList<FacesMessage>();

    /**
     * Instantiates a new offline context.
     */
    protected OfflineContext() {
        // Exists only to defeat instantiation.
    }

    /**
     * Gets the current instance.
     *
     * @return the current instance
     */
    public static OfflineContext getCurrentInstance() {
        if (instance == null) {
            instance = new OfflineContext();
        }
        return instance;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#addMessage(java.lang.String, javax.faces.application.FacesMessage)
     */
    @Override
    public void addMessage(String clientId, FacesMessage message) {
        listMessages.add(message);
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#getApplication()
     */
    @Override
    public Application getApplication() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#getClientIdsWithMessages()
     */
    @Override
    public Iterator<String> getClientIdsWithMessages() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#getExternalContext()
     */
    @Override
    public ExternalContext getExternalContext() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#getMaximumSeverity()
     */
    @Override
    public Severity getMaximumSeverity() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#getMessages()
     */
    @Override
    public Iterator<FacesMessage> getMessages() {
        // TODO Auto-generated method stub
        return listMessages.iterator();
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#getMessages(java.lang.String)
     */
    @Override
    public Iterator<FacesMessage> getMessages(String clientId) {
        // TODO Auto-generated method stub
        return listMessages.iterator();
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#getRenderKit()
     */
    @Override
    public RenderKit getRenderKit() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#getRenderResponse()
     */
    @Override
    public boolean getRenderResponse() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#getResponseComplete()
     */
    @Override
    public boolean getResponseComplete() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#getResponseStream()
     */
    @Override
    public ResponseStream getResponseStream() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#setResponseStream(javax.faces.context.ResponseStream)
     */
    @Override
    public void setResponseStream(ResponseStream arg0) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#getResponseWriter()
     */
    @Override
    public ResponseWriter getResponseWriter() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#setResponseWriter(javax.faces.context.ResponseWriter)
     */
    @Override
    public void setResponseWriter(ResponseWriter arg0) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#getViewRoot()
     */
    @Override
    public UIViewRoot getViewRoot() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#setViewRoot(javax.faces.component.UIViewRoot)
     */
    @Override
    public void setViewRoot(UIViewRoot arg0) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#release()
     */
    @Override
    public void release() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#renderResponse()
     */
    @Override
    public void renderResponse() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see javax.faces.context.FacesContext#responseComplete()
     */
    @Override
    public void responseComplete() {
        // TODO Auto-generated method stub

    }

}
