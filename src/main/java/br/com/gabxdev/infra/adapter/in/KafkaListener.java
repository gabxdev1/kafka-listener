package br.com.gabxdev.infra.adapter.in;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
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
            ConsumerRecord<String, Object> record,
            Acknowledgment ack
    ) {
        var evento = record.value();
        var key = record.key();

        // topic/partition/offset pra log/observabilidade
        var topic = record.topic();
        var partition = record.partition();
        var offset = record.offset();

        try {
            processar(evento);
            ack.acknowledge();
        } catch (Exception e) {
            // log.warn("fail topic={} partition={} offset={}", record.topic(), record.partition(), record.offset(), e);
            throw e;
        }
    }

    private void processar(Object evento) {

    }
}
