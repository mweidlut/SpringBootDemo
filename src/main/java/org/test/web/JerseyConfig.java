package org.test.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.internal.scanning.PackageNamesScanner;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
//        register(GreetingController.class);

        registerFinder(new PackageNamesScanner(new String[]{"org.test"}, true));
    }

}
