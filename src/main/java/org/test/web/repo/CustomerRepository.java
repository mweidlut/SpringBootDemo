package org.test.web.repo;

import org.test.web.protobuf.CustomerProtos;

/**
 * User: weimeng
 * Date: 2017/12/15 17:38
 */
public interface CustomerRepository {
    CustomerProtos.Customer findById(int id);
}
