package org.ndrmicroservices;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SensorDataProducerService {

    private final KafkaTemplate<String, ProducerObject> kafkaTemplate;

    public void produceEvent(ProducerObject producerObject) {
        kafkaTemplate.send("sensor-data", producerObject);
    }
}
