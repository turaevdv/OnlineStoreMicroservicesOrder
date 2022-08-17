package ru.turaev.order.restconsumer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.turaev.order.restconsumer.UserRestConsumer;

@Service
@RequiredArgsConstructor
public class UserRestConsumerImpl implements UserRestConsumer {
    private final WebClient.Builder webClientBuilder;

    @Value("${microservice.user.name}")
    private String userServiceName;

    @Override
    public boolean canUserPlaceOrder(long userId) {
        return webClientBuilder.build()
                .get()
                .uri(getPathToService() + "users/place-order/" + userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    private String getPathToService() {
        return "http://" + userServiceName +"/api/v1/";
    }
}
