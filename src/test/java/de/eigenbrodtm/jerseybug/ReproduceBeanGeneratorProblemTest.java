package de.eigenbrodtm.jerseybug;

import static org.junit.Assert.*;
import org.jboss.weld.environment.servlet.Listener;
import org.junit.Test;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.grizzly2.web.GrizzlyWebTestContainerFactory;

public class ReproduceBeanGeneratorProblemTest extends JerseyTest {
    
    static {
        System.setProperty("com.sun.jersey.server.impl.cdi.lookupExtensionInBeanManager", "true");
    }
    
    public ReproduceBeanGeneratorProblemTest() {
        super(new GrizzlyWebTestContainerFactory());
    }

    protected AppDescriptor configure() {
        return new WebAppDescriptor.Builder().
                servletClass(ServletContainer.class).
                initParam("com.sun.jersey.config.property.packages","de.eigenbrodtm.jersey1130").
                requestListenerClass(Listener.class).
                build();
        
        
    }
    
    // To show the problem both, foo and bar have to be run in the same thread. Foo will succeed, Bar will fail
    @Test public void foo() {
        ClientResponse response = resource().path("/").get(ClientResponse.class);
        assertEquals (response.getStatus(),200);
        assertEquals (response.getEntity(String.class), "Hello World");
    }
    
    @Test public void bar() {
        ClientResponse response = resource().path("/").get(ClientResponse.class);
        assertEquals (response.getStatus(),200);
        assertEquals (response.getEntity(String.class), "Hello World");
    } 
}