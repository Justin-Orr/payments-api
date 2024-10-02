package com.hellobank.paymentsapi.repository.error;

import org.springframework.http.HttpStatusCode;

public class PaymentNotFoundException extends NotFoundException {
    public PaymentNotFoundException() {
        super("Payment not found");
    }
}
