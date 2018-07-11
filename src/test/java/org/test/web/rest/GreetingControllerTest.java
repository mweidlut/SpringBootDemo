package org.test.web.rest;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.test.web.ApplicationConfig4Test;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ApplicationConfig4Test.class)
@Profile("dev")
public class GreetingControllerTest {
    private Logger logger = LoggerFactory.getLogger(GreetingControllerTest.class);

    @Test
    public void test() {
        logger.info("--------abc--------");
        logger.warn("--------abc--------");
        logger.error("--------abc--------");
    }
}