package com.hellobank.paymentsapi.repository.error;

public class PaymentNotFoundException extends NotFoundException {
    public PaymentNotFoundException() {
        super("Payment not found");
    }
}
