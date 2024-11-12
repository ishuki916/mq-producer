package com.gateweb.amqp_producer.usecase;

import com.gateweb.amqp_producer.domain.CustomMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AmqpProducerService {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(CustomMsg customMsg) {
        rabbitTemplate.convertAndSend("myExchange", "myRoutingKey", customMsg, message -> {
            log.debug("Setting message properties");
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setHeader("myHeaderKey", "headerValue");
            return message;
        });
    }

}
