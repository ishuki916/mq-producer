package com.gateweb.amqp_producer.endpoints;

import com.gateweb.amqp_producer.domain.CustomMsg;
import com.gateweb.amqp_producer.usecase.AmqpProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RequiredArgsConstructor
@RestController
public class ProducerApi {

    final private AmqpProducerService amqpProducerService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody CustomMsg customMsg) {
        log.debug("sendMessage: {}", customMsg);
        amqpProducerService.sendMessage(customMsg);
    }


}
