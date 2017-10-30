package org.test.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.web.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByNameNot(String name);

}
