package com.example.prodBack8.exceptions;

public class YouAreInTheQueueException extends RuntimeException {
    public YouAreInTheQueueException(String message) {
        super(message);
    }
}
