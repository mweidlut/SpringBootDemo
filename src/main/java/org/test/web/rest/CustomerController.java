package org.test.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.test.web.dto.CustomerDto;
import org.test.web.service.CustomerService;

@RestController
public class CustomerController {
    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping("/update")
    public String update(@RequestBody CustomerDto dto){
        logger.info(dto.toString());

        customerService.update(dto.getFirstName(), dto.getAge());

        return "success";
    }


}
