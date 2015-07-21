package org.samples.javaee7.arquillian;

import java.io.InputStream;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

/**
 * Created by achaudha on 7/13/2015.
 */
public class DocumentForm {

    @FormParam("content")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream content;

    @FormParam("filename")
    @PartType(MediaType.TEXT_PLAIN)
    private String filename;

    @FormParam("username")
    @PartType(MediaType.TEXT_PLAIN)
    private String username;

    @FormParam("employee")
    @PartType(MediaType.APPLICATION_JSON)
    private Employee employee;

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "DocumentForm{" + "filename='" + filename + '\'' + ", username='" + username + '\'' + ", employee=" + employee + '}';
    }
}
