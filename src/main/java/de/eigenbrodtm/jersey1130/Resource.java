package de.eigenbrodtm.jersey1130;

import javax.annotation.ManagedBean;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path ("/")
@ManagedBean
public class Resource 
{
 
    /* Producer and inject in the same class make not much sense usually, its just to demonstrate injection within
     * grizzly actually works.
     */
    @Produces
    public String greeting() {
        return "World";
    }

    @Inject
    String greeting;

    // This is necessary to reproduce the problem. The CDI Extension tries to register QueryParam as CDI Qualifier and
    // generates a bean for this
    @QueryParam("param") String param;
    
    @GET
    public String get() {
        return "Hello "+greeting;
    }
}
