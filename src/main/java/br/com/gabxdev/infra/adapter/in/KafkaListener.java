package br.com.gabxdev.infra.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListener {



    @org.springframework.kafka.annotation.KafkaListener(
            topics = "test",
            groupId = "test",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onMessage(
            @Payload Object evento,

            Acknowledgment ack
    ) {

        processar(evento);
        ack.acknowledge();
    }

    private void processar(Object evento) {

    }
}
