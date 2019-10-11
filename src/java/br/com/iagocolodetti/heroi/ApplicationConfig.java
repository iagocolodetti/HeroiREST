package br.com.iagocolodetti.heroi;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author iagocolodetti
 */
@javax.ws.rs.ApplicationPath("wr")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(br.com.iagocolodetti.heroi.NewCrossOriginResourceSharingFilter.class);
        resources.add(br.com.iagocolodetti.heroi.controller.HeroiFacadeREST.class);
        resources.add(br.com.iagocolodetti.heroi.controller.UniversoFacadeREST.class);
    }

}
