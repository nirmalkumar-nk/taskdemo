package com.nirmal.taskdemo.service.kafka;

import com.nirmal.taskdemo.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.nirmal.taskdemo.utility.AppUtility.logObjectAsString;

@Service
@Slf4j
public class KafkaTaskConsumer {

    @KafkaListener(
            topics = "${kafka.task.management.topic}",
            groupId = "${kafka.task.management.creation.group-id}",
            containerFactory = "kafkaListenerContainerFactoryTask"
    )
    public void consumeKafkaTask(ConsumerRecord<String, Task> consumerRecord){
        log.info("Kafka message received from {} for: {}", consumerRecord.topic(), consumerRecord.key());
        log.info("Received Kafka Message: {}", logObjectAsString(consumerRecord.value()));
        log.info("Kafka Message processed successfully for {}", consumerRecord.key());
    }
}
