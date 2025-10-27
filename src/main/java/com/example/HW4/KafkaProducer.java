package com.example.HW4;

import com.example.HW4.dto.UserInformationForKafkaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, UserInformationForKafkaDto> kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    public KafkaProducer(KafkaTemplate<String, UserInformationForKafkaDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserInfoKafka(UserInformationForKafkaDto userInfoDto) {
        kafkaTemplate.send("users", userInfoDto);
        log.info("User sent to kafka: email={}, status={}", userInfoDto.getEmail(), userInfoDto.getStatus());
    }

}
