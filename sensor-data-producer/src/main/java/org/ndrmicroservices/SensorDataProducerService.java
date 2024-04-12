package org.ndrmicroservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SensorDataProducerService {

    @Value("${kafka.topic.sensor-data}")
    private String sensorDataTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public SensorDataProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceSensorDataEntryEvent(String entryEvent) {
        // Produce message for entry gate sensor event
        kafkaTemplate.send(sensorDataTopic, "entryEvent:"+entryEvent);
    }

    public void produceSensorDataExitEvent(String exitEvent) {
        // Produce message for exit gate sensor event
        kafkaTemplate.send(sensorDataTopic, "exitEvent:"+exitEvent);
    }
}
