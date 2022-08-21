package ru.turaev.order.exception;

import org.springframework.http.HttpStatus;

public class IncorrectUserException extends BaseException {

    public IncorrectUserException() {
        this("User is incorrect");
    }

    public IncorrectUserException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}