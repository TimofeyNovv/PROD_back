package com.example.prodBack8.exceptions;

public class NoActiveSessionException extends RuntimeException {
    public NoActiveSessionException(String message) {
        super(message);
    }
}
