package ru.turaev.order.exception;

import org.springframework.http.HttpStatus;

public class PickupPointNotFoundException extends BaseException {

    public PickupPointNotFoundException() {
        this("Pickup point not found");
    }

    public PickupPointNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}