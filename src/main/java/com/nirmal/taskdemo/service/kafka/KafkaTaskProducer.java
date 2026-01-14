package com.nirmal.taskdemo.service.kafka;

import com.nirmal.taskdemo.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaTaskProducer {

    private final String topicName;
    private final KafkaTemplate<String, Object> taskKafkaTemplate;

    @Autowired
    public KafkaTaskProducer(@Value("${kafka.task.management.topic}") String topicName, KafkaTemplate<String, Object> taskKafkaTemplate) {
        this.topicName = topicName;
        this.taskKafkaTemplate = taskKafkaTemplate;
    }

    public void produceTask(Task task){

      try{
          log.info("Producing Kafka message to topic: {}", topicName);
          taskKafkaTemplate.send(topicName, task.getId(), task);
          log.info("Message successfully produced");
      } catch (Exception e) {
          log.error("Exception occurred when producing kafka message to topic {}, exception: {}", topicName, e.getMessage());
          e.printStackTrace();
      }
    }
}
