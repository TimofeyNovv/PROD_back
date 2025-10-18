package com.example.prodBack8.exceptions;

public class NoGPULeftException extends RuntimeException {
    public NoGPULeftException(String message) {
        super(message);
    }
}
