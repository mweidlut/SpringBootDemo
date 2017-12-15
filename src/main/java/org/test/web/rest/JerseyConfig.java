package org.test.web.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.internal.scanning.PackageNamesScanner;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component("myJerseyConfig")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
//        register(GreetingController.class);

        registerFinder(new PackageNamesScanner(new String[]{"org.test"}, true));
    }

}
