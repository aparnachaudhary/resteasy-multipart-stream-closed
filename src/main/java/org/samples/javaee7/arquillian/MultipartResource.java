package org.samples.javaee7.arquillian;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

/**
 * Created by achaudha on 7/10/2015.
 */
@Path("multipart")
public interface MultipartResource {

    @Path("photo")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    InputStream uploadPhoto(@MultipartForm DocumentForm documentForm);
}
