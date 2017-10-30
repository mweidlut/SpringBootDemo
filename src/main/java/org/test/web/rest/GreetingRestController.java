package org.test.web.rest;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.test.web.annotation.CheckMethod;
import org.test.web.domain.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/app")
public class GreetingRestController {

    @GET
    @Path("/hello")
    public String greeting() {
        return "Hello World!";
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @CheckMethod
    public String user(){
        User user = new User();
        user.setName("anna");

        return new Gson().toJson(user);
    }
}
