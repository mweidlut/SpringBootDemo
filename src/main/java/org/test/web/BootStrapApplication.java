package org.test.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.test.web.batch.SpringBatchDemoBootstrap;

import java.security.Security;

@Configuration
@EntityScan({"org.test.web.domain"})
@EnableJpaRepositories({"org.test.web.repo"})
@ComponentScan({"org.test"})
@EnableAspectJAutoProxy
@SpringBootApplication
public class BootStrapApplication {

    static {
        //for UFX client
        Security.setProperty("jdk.tls.disabledAlgorithms", "");
        Security.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
    }

    public static void main(String[] args) {
        SpringApplication.run(BootStrapApplication.class, args);
    }

    @Bean
    public SpringBatchDemoBootstrap batchBootstrap() {
        return new SpringBatchDemoBootstrap();
    }

    /*@Bean
    public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }*/


    /*@Bean
    public UFXBootstrap ufxBootstrap(){
        return new UFXBootstrap();
    }*/


}