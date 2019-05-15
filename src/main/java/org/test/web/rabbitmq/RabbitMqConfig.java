//package org.test.web.rabbitmq;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@EnableRabbit
//@Configuration
//public class RabbitMqConfig {
//
//    public static final String QUEUE_NAME = "spring-support-test-queue";
//    public static final String EXCHANGE_NAME = "spring-support-test-exchange";
//    public static final String ROUTING_KEY = "spring-support-test-routing-key";
//
////    @Bean
////    public Queue queue() {
////        return new Queue(QUEUE_NAME, true);
////    }
////
////    @Bean
////    public DirectExchange directExchange() {
////        return new DirectExchange(EXCHANGE_NAME);
////    }
////
////    @Bean
////    public Binding binding(@Autowired Queue queue, @Autowired DirectExchange directExchange) {
////        return BindingBuilder.bind(queue).to(directExchange).with(ROUTING_KEY);
////    }
//
//}
