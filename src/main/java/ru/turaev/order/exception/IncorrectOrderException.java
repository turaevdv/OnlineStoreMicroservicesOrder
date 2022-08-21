package ru.turaev.order.exception;

import org.springframework.http.HttpStatus;

public class IncorrectOrderException extends BaseException {

    public IncorrectOrderException() {
        this("Order is incorrect");
    }

    public IncorrectOrderException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}