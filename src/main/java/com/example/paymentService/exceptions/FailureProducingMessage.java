package com.example.paymentService.exceptions;

public class FailureProducingMessage extends RuntimeException {
    public FailureProducingMessage(String message,Exception e) {
        super(message);
    }
}
