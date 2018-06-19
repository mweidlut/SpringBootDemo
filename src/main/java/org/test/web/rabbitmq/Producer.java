package org.test.web.rabbitmq;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.test.web.domain.User;
import org.test.web.repo.UserRepository;

import java.time.LocalDate;

@Service
public class Producer {
    private static Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserRepository userRepository;

    public void produce(){
        User user = new User("Mike", "M", LocalDate.of(1990, 1, 8));
        userRepository.save(user);

        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME ,RabbitMqConfig.ROUTING_KEY, user);
        logger.info("convertAndSend: {}", user);
    }

}
