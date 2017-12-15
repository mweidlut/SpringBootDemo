package org.test.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.test.web.protobuf.CustomerProtos;
import org.test.web.repo.CustomerRepository;

/**
 * User: weimeng
 * Date: 2017/12/15 18:01
 */
@RestController("/app")
public class SpringMVCController {

    private static Logger logger = LoggerFactory.getLogger(SpringMVCController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "/customers2/{id}", method = RequestMethod.GET)
    public CustomerProtos.Customer customer(@PathVariable Integer id) {
        CustomerProtos.Customer customer = customerRepository.findById(id);

        logger.info("customers2 ...");

        return customer;

    }

}
