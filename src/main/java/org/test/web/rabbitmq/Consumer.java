//package org.test.web.rabbitmq;
//
//import com.rabbitmq.client.Channel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Service;
//import org.test.web.domain.User;
//
//import java.io.IOException;
//
//@Service
//@RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
//public class Consumer {
//
//    private static Logger logger = LoggerFactory.getLogger(Consumer.class);
//
//    @RabbitHandler
//    public void consume(@Payload User user,
//                        @Header(AmqpHeaders.CHANNEL) Channel channel,
//                        @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
//        logger.info(">>>tag: {}", tag);
//
//        logger.info("received a user: {}", user);
//
//        try {
//            channel.basicAck(tag, false);
//        } catch (IOException e) {
//            logger.error("basicAck error:{}", e.getMessage(), e);
//            try {
//                channel.basicNack(tag, false, true);
//            } catch (IOException e1) {
//                logger.error("basicNack error:{}", e1.getMessage(), e1);
//            }
//        }
//    }
//
//}
