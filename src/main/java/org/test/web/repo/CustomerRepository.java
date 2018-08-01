package org.test.web.repo;

import org.test.web.protobuf.CustomerProtos;


public interface CustomerRepository {
    CustomerProtos.Customer findById(int id);
}
