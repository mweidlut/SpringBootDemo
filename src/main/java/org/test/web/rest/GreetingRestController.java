//package org.test.web.rest;
//
//import com.google.gson.Gson;
//import com.googlecode.protobuf.format.JsonFormat;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.test.web.annotation.CheckMethod;
//import org.test.web.domain.User;
//import org.test.web.protobuf.CustomerProtos;
//import org.test.web.rabbitmq.Producer;
//import org.test.web.repo.CustomerRepository;
//import org.test.web.repo.UserRepository;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;
//
//@Configuration
//@Component
//@Path("/app")
//public class GreetingRestController {
//
//    @Autowired
//    private CustomerRepository customerRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private Producer producer;
//
//    @GET
//    @Path("/produce")
//    public String produce() {
//        producer.produce();
//        return "produce done!";
//    }
//
//    @GET
//    @Path("/hello")
//    public String greeting() {
//        return "Hello World!";
//    }
//
//    @GET
//    @Path("/reflect")
//    public String reflect() {
//        try {
//            Method method = this.getClass().getMethod("user", null);
//            Object invoke = method.invoke(this, null); //反射不会被aop拦截
//
//            System.out.println("reflect...");
//
//            return invoke.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "Hello World!";
//    }
//
//    @GET
//    @Path("/user")
//    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//    @CheckMethod
//    public String user() {
//        User user = new User();
//        user.setName("anna");
//
//        return new Gson().toJson(user);
//    }
//
//    @GET
//    @Path("/users")
//    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//    @CheckMethod
//    public String users() {
//        List<User> userList = userRepository.findAll();
//
//        return new Gson().toJson(userList);
//    }
//
//    @GET
//    @Path("/customers/{id}")
//    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//    @CheckMethod
//    public String customer(@PathParam("id") Integer id) {
//        CustomerProtos.Customer customer = customerRepository.findById(id);
//
//        String jsonStr = new JsonFormat().printToString(customer);
//        return jsonStr;
//    }
//
//    @Bean
//    CustomerRepository customerRepository() {
//        java.util.Map<Integer, CustomerProtos.Customer> customers = new ConcurrentHashMap<>();
//        // populate with some dummy data
//        Arrays.asList(
//                customer(1, "Chris", "Richardson", Arrays.asList("crichardson@email.com")),
//                customer(2, "Josh", "Long", Arrays.asList("jlong@email.com")),
//                customer(3, "Matt", "Stine", Arrays.asList("mstine@email.com")),
//                customer(4, "Russ", "Miles", Arrays.asList("rmiles@email.com"))
//        ).forEach(c -> customers.put(c.getId(), c));
//
//        // our lambda just gets forwarded to Map#get(Integer)
//        return customers::get;
//    }
//
//    private CustomerProtos.Customer customer(int id, String firstName, String lastName, Collection<String> emails) {
//        Collection<CustomerProtos.Customer.EmailAddress> emailAddresses =
//                emails.stream()
//                        .map(e -> CustomerProtos.Customer.EmailAddress.newBuilder()
//                                .setType(CustomerProtos.Customer.EmailType.PROFESSIONAL)
//                                .setEmail(e).build())
//                        .collect(Collectors.toList());
//
//        return CustomerProtos.Customer.newBuilder()
//                .setFirstName(firstName)
//                .setLastName(lastName)
//                .setId(id)
//                .addAllEmail(emailAddresses)
//                .build();
//    }
//}
