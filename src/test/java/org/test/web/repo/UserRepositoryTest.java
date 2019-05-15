//package org.test.web.repo;
//
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.ExampleMatcher;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import org.test.web.ApplicationConfig4Test;
//import org.test.web.domain.User;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//@Ignore
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Import(ApplicationConfig4Test.class)
//@Profile("dev")
//@Transactional
//public class UserRepositoryTest {
//
//    private Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void testFind() {
//        User user = new User();
//        user.setName("mike");
//        //user.setBirthday(LocalDate.now());
//
//        userRepository.save(user);
//
//        User saved = userRepository.findOne(user.getId());
//
//        List<User> users = userRepository.findByNameNot("mi");
//        users.remove(0);
//
//        logger.info("find user {} ", saved);
//        logger.info("find users {} ", users);
//    }
//
//
//    @Test
//    public void testFindByExample() {
//        userRepository.save(Arrays.asList(
//                new User("anna", "F", LocalDate.now()),
//                new User("anna2", "F", LocalDate.now()),
//                new User("mike", "M", LocalDate.now()),
//                new User("mike2", "M", LocalDate.now())));
//
//        userRepository.flush();
//
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                .withIgnorePaths("createTime", "birthday")
//                .withIgnoreNullValues();
//
//        User query = new User();
//        query.setName("mike");
//
////        Example<User> userExample = Example.of(query);
//        Example<User> userExample = Example.of(query, matcher);
//
//        List<User> users = userRepository.findAll(userExample);
//
//        logger.info("find user size {} ", users.size());
//        logger.info("find user {} ", users);
//    }
//}