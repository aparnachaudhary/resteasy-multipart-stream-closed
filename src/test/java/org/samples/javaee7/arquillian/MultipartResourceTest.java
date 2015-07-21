package org.samples.javaee7.arquillian;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import javax.ws.rs.ProcessingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MultipartResourceTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class).addClasses(RegistryApplication.class, MultipartResource.class, MultipartService.class,
                Employee.class, DocumentForm.class).addAsManifestResource("jboss-deployment-structure.xml");
    }

    @ArquillianResource
    private URL base;

    @Test
    public void multipartPost_TempFile() {
        try {
            ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
            ResteasyProviderFactory.pushContext(javax.ws.rs.ext.Providers.class, factory);
            ApacheHttpClient4Engine httpEngine = new ApacheHttpClient4Engine(HttpClientBuilder.create().build(), true);
            // Set memory threshold lower than the actual file size so that streaming to temp file is used
            httpEngine.setFileUploadInMemoryThresholdLimit(64);
            httpEngine.setFileUploadMemoryUnit(ApacheHttpClient4Engine.MemoryUnit.KB);
            ResteasyClient client = new ResteasyClientBuilder().providerFactory(factory).httpEngine(httpEngine).build();
            ResteasyWebTarget target = client.target(URI.create(new URL(base, "registry").toExternalForm()));

            // Prepare Request
            DocumentForm documentForm = new DocumentForm();
            documentForm.setFilename("test.xml");
            documentForm.setUsername("Aparna");
            documentForm.setEmployee(new Employee("Aparna", "Chaudhary"));
            documentForm.setContent(getClass().getResourceAsStream("/cheatsheet_236KB.xml"));
            // send request
            MultipartResource resource = target.proxy(MultipartResource.class);
            InputStream response = resource.uploadPhoto(documentForm);
            // verify response
            assertEquals("Unexpected result", "Aparna Chaudhary", IOUtils.toString(response));
        } catch (ProcessingException e) {
            e.printStackTrace();
            fail("Failed to send post request");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to post multipart request");
        }

    }

    @Test
    public void multipartPost_InMemory() {
        try {
            ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
            ResteasyProviderFactory.pushContext(javax.ws.rs.ext.Providers.class, factory);
            ApacheHttpClient4Engine httpEngine = new ApacheHttpClient4Engine(HttpClientBuilder.create().build(), true);
            // Set memory threshold higher than the actual file size; so that in memory store is used
            httpEngine.setFileUploadInMemoryThresholdLimit(256);
            httpEngine.setFileUploadMemoryUnit(ApacheHttpClient4Engine.MemoryUnit.KB);
            ResteasyClient client = new ResteasyClientBuilder().providerFactory(factory).httpEngine(httpEngine).build();
            ResteasyWebTarget target = client.target(URI.create(new URL(base, "registry").toExternalForm()));

            // Prepare Request
            DocumentForm documentForm = new DocumentForm();
            documentForm.setFilename("test.xml");
            documentForm.setUsername("Aparna");
            documentForm.setEmployee(new Employee("Aparna", "Chaudhary"));
            documentForm.setContent(getClass().getResourceAsStream("/cheatsheet_236KB.xml"));
            // send request
            MultipartResource resource = target.proxy(MultipartResource.class);
            InputStream response = resource.uploadPhoto(documentForm);
            // verify response
            assertEquals("Unexpected result", "Aparna Chaudhary", IOUtils.toString(response));
        } catch (ProcessingException e) {
            e.printStackTrace();
            fail("Failed to send post request");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to post multipart request");
        }

    }

}