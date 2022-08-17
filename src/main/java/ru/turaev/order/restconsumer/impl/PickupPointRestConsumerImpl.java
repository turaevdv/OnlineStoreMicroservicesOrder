package ru.turaev.order.restconsumer.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.turaev.order.restconsumer.PickupPointRestConsumer;

@Service
@RequiredArgsConstructor
public class PickupPointRestConsumerImpl implements PickupPointRestConsumer {
    private final WebClient.Builder webClientBuilder;

    @Value("${microservice.pickup-point.name}")
    private String pickupPointServiceName;

    @Override
    public boolean isPickupPointExist(long id) {
        return webClientBuilder.build()
                .get()
                .uri(getPathToService() + "pickup-points/exist/" + id)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    private String getPathToService() {
        return "http://" + pickupPointServiceName + "/api/v1/";
    }
}
