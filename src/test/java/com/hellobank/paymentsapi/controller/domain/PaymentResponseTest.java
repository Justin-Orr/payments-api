package com.hellobank.paymentsapi.controller.domain;

import com.hellobank.paymentsapi.domain.Payment;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentResponseTest {

    @Test
    void paymentFieldOverloadedConstructor() {
        PaymentResponse response = new PaymentResponse(
                new Payment(UUID.randomUUID(),UUID.randomUUID(), 10)
        );
        assertNotNull(response);
    }

    @Test
    void messageFieldOverloadedConstructor() {
        PaymentResponse response = new PaymentResponse("message");
        assertNotNull(response);
    }

    @Test
    void getPayment() {
        PaymentResponse response = new PaymentResponse(
                new Payment(UUID.randomUUID(),UUID.randomUUID(), 10)
        );
        assertNotNull(response.getPayment());
    }

    @Test
    void setPayment() {
        PaymentResponse response = new PaymentResponse(
                new Payment(UUID.randomUUID(),UUID.randomUUID(), 10)
        );
        response.setPayment(new Payment(UUID.randomUUID(),UUID.randomUUID(), 20));
        assertEquals(20, response.getPayment().getAmount());
    }

    @Test
    void getMessage() {
        PaymentResponse response = new PaymentResponse("message");
        assertEquals(0, response.getMessage().compareTo("message"));
    }

    @Test
    void setMessage() {
        PaymentResponse response = new PaymentResponse("message1");
        response.setMessage("message2");
        assertEquals(0, response.getMessage().compareTo("message2"));
    }
}