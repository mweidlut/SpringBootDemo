package org.test.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan({"org.test.web.domain"})
@EnableJpaRepositories({"org.test.web.repo"})
@EnableAspectJAutoProxy
@SpringBootApplication
@ComponentScan(value = "org.test")
public class ApplicationConfig4Test {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig4Test.class, args);
    }

}