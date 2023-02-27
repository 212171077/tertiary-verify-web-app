package com.pc.ui;

import com.pc.entities.ImageModel;
import com.pc.service.ImagesService;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import java.io.ByteArrayInputStream;

@Named
@ApplicationScoped
public class ImageModelUI {

    @Autowired
    private ImagesService imagesService;

    public StreamedContent getImage() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            ImageModel image = imagesService.getById(Long.valueOf(id));
            return new DefaultStreamedContent(new ByteArrayInputStream(image.getPic()));
        }
    }

}