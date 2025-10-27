package com.example.HW4;

import com.example.HW4.dto.UserInformationForKafkaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, UserInformationForKafkaDto> producerFactory(
            ObjectMapper objectMapper
    ) {
        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        JsonSerializer<UserInformationForKafkaDto> serializer = new JsonSerializer<>(objectMapper);
        serializer.setAddTypeInfo(false);

        return new DefaultKafkaProducerFactory<>(
                configProperties,
                new StringSerializer(),
                serializer
        );
    }

    @Bean
    public KafkaTemplate<String, UserInformationForKafkaDto> kafkaTemplate(
            ProducerFactory<String, UserInformationForKafkaDto> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

}
