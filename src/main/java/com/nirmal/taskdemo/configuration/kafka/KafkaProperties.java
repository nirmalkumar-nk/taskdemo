package com.nirmal.taskdemo.configuration.kafka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class KafkaProperties {

    @Value("${kafka.bootstrap.server}")
    private String bootstrapServer;
}
