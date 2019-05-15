//package org.test.web.rest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import org.test.web.protobuf.CustomerProtos;
//import org.test.web.repo.CustomerRepository;
//
//
//@RestController
//public class SpringMvcController {
//
//    private static Logger logger = LoggerFactory.getLogger(SpringMvcController.class);
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @RequestMapping(value = "/app2/customers2/{id}", method = RequestMethod.GET)
//    public CustomerProtos.Customer customer(@PathVariable("id") Integer id) {
//        CustomerProtos.Customer customer = customerRepository.findById(id);
//
//        logger.info("customers2 ...");
//
//        return customer;
//    }
//
//}
