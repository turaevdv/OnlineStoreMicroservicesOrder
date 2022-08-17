package ru.turaev.order.restconsumer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.turaev.order.dto.AccountingAndQuantityDto;
import ru.turaev.order.restconsumer.GoodsRestConsumer;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsRestConsumerImpl implements GoodsRestConsumer {
    private final WebClient.Builder webClientBuilder;

    @Value("${microservice.goods.name}")
    private String goodsServiceName;

    @Override
    public int bookingGoods(List<AccountingAndQuantityDto> accountingAndQuantityDtos) {
        return webClientBuilder.build()
                .post()
                .uri(getPathToService() + "accounting/booking-goods/")
                .bodyValue(accountingAndQuantityDtos)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }

    @Override
    public void returnGoods(List<AccountingAndQuantityDto> accountingAndQuantityDtos) {
         webClientBuilder.build()
                .post()
                .uri(getPathToService() + "accounting/return-goods/")
                .bodyValue(accountingAndQuantityDtos)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private String getPathToService() {
        return "http://" + goodsServiceName +"/api/v1/";
    }
}
