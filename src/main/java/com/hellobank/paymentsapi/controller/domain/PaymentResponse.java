package com.hellobank.paymentsapi.controller.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hellobank.paymentsapi.domain.Payment;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {
    private Payment payment;
    private String message;


    public PaymentResponse(Payment payment) {
        this.payment = payment;
        this.message = null;
    }

    public PaymentResponse(String message) {
        this.payment = null;
        this.message = message;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
