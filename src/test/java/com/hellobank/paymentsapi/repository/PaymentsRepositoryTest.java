package com.hellobank.paymentsapi.repository;

import com.hellobank.paymentsapi.domain.Payment;
import com.hellobank.paymentsapi.repository.error.AccountNotFoundException;
import com.hellobank.paymentsapi.repository.error.PaymentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentsRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(PaymentsRepositoryTest.class);
    PaymentsRepository paymentRepository = new PaymentsRepository();

    @BeforeEach
    void clearRepositoryData() {
        log.info("Clearing repository data");
        paymentRepository.getPayments().clear();
    }

    @Test
    @DisplayName("Insert Payment")
    void insertPayment() {
        Payment payment = new Payment(UUID.randomUUID(), UUID.randomUUID(), 10.0);
        Payment insertedPayment = paymentRepository.insertPayment(payment);
        assertEquals(0, insertedPayment.getPaymentId().compareTo(payment.getPaymentId()));
        assertEquals(10.0, insertedPayment.getAmount());
    }

    @Test
    @DisplayName("Return a list of all payments.")
    void getAllPayments() {
        Payment payment1 = new Payment(UUID.randomUUID(), UUID.randomUUID(), 10.0);
        Payment payment2 = new Payment(UUID.randomUUID(), UUID.randomUUID(), 10.0);
        paymentRepository.insertPayment(payment1);
        paymentRepository.insertPayment(payment2);
        assertEquals(2, paymentRepository.getPayments().size());
    }

    @Test
    @DisplayName("Find a payment by payment id")
    void findPaymentByPaymentId() {
        UUID id = UUID.randomUUID();
        Payment payment = new Payment(id, UUID.randomUUID(), 10.0);
        paymentRepository.insertPayment(payment);
        try {
            Payment foundPayment = paymentRepository.findPaymentByPaymentId(id);
            assertEquals(payment, foundPayment);
        } catch (PaymentNotFoundException exception) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    @DisplayName("Fail to find a payment by payment id")
    void findPaymentByPaymentIdFailure() {
        try {
            paymentRepository.findPaymentByPaymentId(UUID.randomUUID());
            fail("Exception should've been thrown");
        } catch (PaymentNotFoundException exception) {
            assertEquals(exception.getStatus(), HttpStatusCode.valueOf(404));
            assertEquals(0, exception.getMessage().compareTo("Payment not found"));
        }
    }

    @Test
    @DisplayName("Find a payment by account id")
    void findPaymentByAccountId() {
        UUID id = UUID.randomUUID();
        paymentRepository.insertPayment(new Payment(UUID.randomUUID(), id, 10.0));
        paymentRepository.insertPayment(new Payment(UUID.randomUUID(), id, 10.0));
        try {
            List<Payment> foundPayments = paymentRepository.findPaymentsByAccountId(id);
            assertEquals(2, foundPayments.size());
        } catch (AccountNotFoundException exception) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    @DisplayName("Fail to find a payment by account id")
    void findPaymentsByAccountIdFailure() {
        try {
            paymentRepository.findPaymentsByAccountId(UUID.randomUUID());
            fail("Exception should've been thrown");
        } catch (AccountNotFoundException exception) {
            assertEquals(exception.getStatus(), HttpStatusCode.valueOf(404));
            assertEquals(0, exception.getMessage().compareTo("Account not found"));
        }
    }

    @Test
    @DisplayName("Update payment")
    void updatePayment() {
        UUID paymentId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        paymentRepository.insertPayment(new Payment(paymentId,accountId, 10.0));
        try {
            Payment updatePayment = new Payment(paymentId, accountId, 20.0);
            paymentRepository.updatePayment(updatePayment);
            assertEquals(updatePayment, paymentRepository.findPaymentByPaymentId(paymentId));
        } catch (PaymentNotFoundException exception) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    @DisplayName("Fail to update payment")
    void updatePaymentFailure() {
        try {
            paymentRepository.updatePayment(new Payment(UUID.randomUUID(), UUID.randomUUID(), 20.0));
            fail("Exception should've been thrown");
        } catch (PaymentNotFoundException exception) {
            assertEquals(exception.getStatus(), HttpStatusCode.valueOf(404));
            assertEquals(0, exception.getMessage().compareTo("Payment not found"));
        }
    }

    @Test
    @DisplayName("Delete payment")
    void deletePayment() {
        UUID id = UUID.randomUUID();
        try {
            paymentRepository.insertPayment(new Payment(id, UUID.randomUUID(), 10.0));
            assertNotNull(paymentRepository.findPaymentByPaymentId(id));

            paymentRepository.deletePayment(id);
            assertEquals(0, paymentRepository.getPayments().size());
        } catch (PaymentNotFoundException exception) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    @DisplayName("Fail to delete payment")
    void deletePaymentFailure() {
        try {
            paymentRepository.deletePayment(UUID.randomUUID());
            assertEquals(0, paymentRepository.getPayments().size());
            fail("Exception should've been thrown");
        } catch (PaymentNotFoundException exception) {
            assertEquals(exception.getStatus(), HttpStatusCode.valueOf(404));
            assertEquals(0, exception.getMessage().compareTo("Payment not found"));
        }
    }

}