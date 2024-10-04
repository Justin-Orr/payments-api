package com.hellobank.paymentsapi.controller.domain;

import com.hellobank.paymentsapi.domain.Payment;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentListResponseTest {

    @Test
    void defaultConstructor() {
        PaymentListResponse response = new PaymentListResponse(
                List.of(new Payment(UUID.randomUUID(), UUID.randomUUID(), 10))
        );
        assertNotNull(response);
    }

    @Test
    void getPayments() {
        PaymentListResponse response = new PaymentListResponse(
                List.of(new Payment(UUID.randomUUID(), UUID.randomUUID(), 10))
        );
        assertNotNull(response.getPayments());
    }

    @Test
    void setPayments() {
        PaymentListResponse response = new PaymentListResponse(
                List.of(new Payment(UUID.randomUUID(), UUID.randomUUID(), 10))
        );

        response.setPayments(
                List.of(
                        new Payment(UUID.randomUUID(), UUID.randomUUID(), 20),
                        new Payment(UUID.randomUUID(), UUID.randomUUID(), 30)
                )
        );
        assertEquals(2, response.getPayments().size());
    }

    @Test
    void getMetadata() {
        PaymentListResponse response = new PaymentListResponse(
                List.of(new Payment(UUID.randomUUID(), UUID.randomUUID(), 10))
        );
        assertNotNull(response.getMetadata());
    }
}