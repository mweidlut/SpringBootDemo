package org.test.web.repo;


import org.springframework.data.repository.CrudRepository;
import org.test.web.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByFirstName(String firstName);

    Customer findById(long id);
}