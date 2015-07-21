package org.samples.javaee7.arquillian;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * Created by achaudha on 7/10/2015.
 */
public class MultipartService implements MultipartResource {

    @Override
    public InputStream uploadPhoto(DocumentForm documentForm) {
        return IOUtils.toInputStream(documentForm.getEmployee().getFirstname() + " " + documentForm.getEmployee().getLastname());
    }
}
