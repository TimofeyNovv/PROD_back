package com.example.prodBack8.exceptions;

public class ForbiddenGPUInCurrentTimeException extends RuntimeException {
    public ForbiddenGPUInCurrentTimeException(String message) {
        super(message);
    }
}
