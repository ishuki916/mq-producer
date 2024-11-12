package com.gateweb.amqp_producer.usecase;

import com.gateweb.amqp_producer.domain.CustomMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AmqpProducerService {

    private final RabbitTemplate rabbitTemplate;

    public AmqpProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.debug("Message sent successfully with correlation data: " + correlationData);
            } else {
                log.error("Message failed to send. Reason: " + cause);
            }
        });
    }

    public void sendMessage(CustomMsg customMsg) {
        rabbitTemplate.convertAndSend("myExchange", "myRoutingKey", customMsg, message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setHeader("myHeaderKey", "headerValue");
            return message;
        });
    }

}
