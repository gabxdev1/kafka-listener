package br.com.gabxdev.infra.config;

import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

//    @Bean
//    public ConsumerFactory<Object, Object> consumerFactory(KafkaProperties props) {
//        var consumerProps = new HashMap<>(props.buildConsumerProperties());
//
//        return new DefaultKafkaConsumerFactory<>(consumerProps);
//    }

    @Bean
    public CommonErrorHandler kafkaErrorHandler() {
        var backOff = new FixedBackOff(1000L, 3L); // 1s entre tentativas

        var handler = new DefaultErrorHandler(backOff);

        handler.setCommitRecovered(true);

        handler.addNotRetryableExceptions(
                IllegalArgumentException.class,
                ClassNotFoundException.class
        );

        return handler;
    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
//            ConsumerFactory<String, Object> consumerFactory,
//            CommonErrorHandler kafkaErrorHandler
//    ) {
//        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
//        factory.setConsumerFactory(consumerFactory);
//
//        factory.setConcurrency(3); // do YAML
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
//        factory.getContainerProperties().setMissingTopicsFatal(false); // do YAML
//        factory.getContainerProperties().setObservationEnabled(true);  // do YAML
//
//        factory.setCommonErrorHandler(kafkaErrorHandler);
//        return factory;
//    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> consumerFactory,
            CommonErrorHandler kafkaErrorHandler
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();

        // ✅ aplica exatamente spring.kafka.* (listener, consumer, etc.)
        configurer.configure(factory, consumerFactory);

        // ✅ sua customização enterprise
        factory.setCommonErrorHandler(kafkaErrorHandler);

        return factory;
    }


}
