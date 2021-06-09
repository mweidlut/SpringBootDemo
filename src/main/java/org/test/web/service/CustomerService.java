package org.test.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.test.web.domain.Customer;
import org.test.web.repo.CustomerRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
public class CustomerService {

    private static Logger log = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository repository;
    @Autowired
    private CustomerService customerService;

    @PostConstruct
    public void init() {
        repository.deleteAll();

        // save a few customers
        repository.save(new Customer("Jack", "11"));
        repository.save(new Customer("Chloe", "22"));
        repository.save(new Customer("Kim", "33"));
        repository.save(new Customer("David", "44"));
        repository.save(new Customer("Michelle", "55"));

        // fetch all customers
        log.info("Customers found with findAll():");
        log.info("-------------------------------");

        for (Customer customer : repository.findAll()) {
            log.info(customer.toString());
        }
    }

    @Transactional
    public void update(String firstName, String age) {
        Customer customer = repository.findByFirstName(firstName);
        customer.setAge(age);
        repository.save(customer);

        // 增加代理
        customerService.findDb(firstName);

        log.info("before update:{}", repository.findByFirstName(firstName));
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void findDb(String firstName) {
        log.info("findDb:{}", repository.findByFirstName(firstName));

    }
}
