package ru.turaev.order.restconsumer;

public interface UserRestConsumer {
    boolean canUserPlaceOrder(long userId);
}
