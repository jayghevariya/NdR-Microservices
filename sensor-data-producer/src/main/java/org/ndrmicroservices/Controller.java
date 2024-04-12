package org.ndrmicroservices;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors/produce")
@RequiredArgsConstructor
public class Controller {

    private final SensorDataProducerService sensorDataProducerService;

    @PostMapping
    public String produce(@RequestBody ProducerObject producerObject) {
        sensorDataProducerService.produceEvent(producerObject);
        return "Event produced";
    }
}
