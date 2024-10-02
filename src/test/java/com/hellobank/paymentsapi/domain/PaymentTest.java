package com.hellobank.paymentsapi.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void defaultConstructorTest() {
        Payment payment = new Payment();
        assertNotNull(payment);
    }

    @Test
    void overloadedConstructorTest() {
        Payment payment = new Payment(UUID.randomUUID(), UUID.randomUUID(), 10.0);
        assertNotNull(payment);
    }

    @Test
    void getPaymentId() {
        UUID id = UUID.randomUUID();
        Payment payment = new Payment(id, UUID.randomUUID(), 10.0);
        assertEquals(0, payment.getPaymentId().compareTo(id));
    }

    @Test
    void getAmount() {
        Payment payment = new Payment(UUID.randomUUID(), UUID.randomUUID(), 10.0);
        assertEquals(10.0, payment.getAmount());
    }

    @Test
    void getAccountId() {
        UUID id = UUID.randomUUID();
        Payment payment = new Payment(UUID.randomUUID(), id, 10.0);
        assertEquals(0, payment.getAccountId().compareTo(id));
    }

    @Test
    void setPaymentId() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Payment payment = new Payment(id1, UUID.randomUUID(), 10.0);
        payment.setPaymentId(id2);
        assertEquals(0, payment.getPaymentId().compareTo(id2));
    }

    @Test
    void setAmount() {
        double amount1 = 10.0;
        double amount2 = 20.0;
        Payment payment = new Payment(UUID.randomUUID(), UUID.randomUUID(), amount1);
        payment.setAmount(amount2);
        assertEquals(amount2, payment.getAmount());
    }

    @Test
    void setAccountId() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Payment payment = new Payment(UUID.randomUUID(), id1, 10.0);
        payment.setAccountId(id2);
        assertEquals(0, payment.getAccountId().compareTo(id2));
    }
}