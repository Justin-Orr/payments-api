package com.hellobank.paymentsapi.service;

import com.hellobank.paymentsapi.domain.Payment;
import com.hellobank.paymentsapi.repository.PaymentRepository;
import com.hellobank.paymentsapi.repository.error.AccountNotFoundException;
import com.hellobank.paymentsapi.repository.error.PaymentNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentService paymentService;

    @Test
    @DisplayName("Create a payment")
    void createPayment() {
        Payment payment = new Payment(UUID.randomUUID(), UUID.randomUUID(), 10.0);
        when(paymentRepository.insertPayment(any())).thenReturn(payment);

        Payment insertedPayment = paymentService.createPayment(payment);

        assertEquals(payment, insertedPayment);
        verify(paymentRepository, times(1)).insertPayment(any());
    }

    @Test
    @DisplayName("Return all payments")
    void getAllPayments() {
        ArrayList<Payment> payments = new ArrayList<>(
                List.of(
                        new Payment(UUID.randomUUID(), UUID.randomUUID(), 10.0),
                        new Payment(UUID.randomUUID(), UUID.randomUUID(), 20.0)
                )
        );

        when(paymentRepository.getPayments()).thenReturn(payments);

        assertEquals(2, paymentService.getPayments().size());
        verify(paymentRepository, times(1)).getPayments();
    }

    @Test
    @DisplayName("Return payment by payment id")
    void findPaymentByPaymentId() {
        try {
            Payment payment = new Payment(UUID.randomUUID(), UUID.randomUUID(), 10.0);
            when(paymentRepository.findPaymentByPaymentId(any())).thenReturn(payment);

            Payment foundPayment = paymentService.findPaymentByPaymentId(payment.getPaymentId());

            assertEquals(payment, foundPayment);
            verify(paymentRepository, times(1)).findPaymentByPaymentId(any());
        } catch (PaymentNotFoundException exception) {
            fail("Should not throw an exception");
        }
    }

    @Test
    @DisplayName("Fail to return payment by payment id")
    void failToFindPaymentByPaymentId() throws PaymentNotFoundException {

        when(paymentRepository.findPaymentByPaymentId(any())).thenThrow(new PaymentNotFoundException());

        try {
            paymentService.findPaymentByPaymentId(UUID.randomUUID());
            fail("Should throw an exception");
        } catch (PaymentNotFoundException exception) {
            assertEquals(404, exception.getStatus().value());
            assertEquals(0, exception.getMessage().compareTo("Payment not found"));
            verify(paymentRepository, times(1)).findPaymentByPaymentId(any());
        }
    }

    @Test
    @DisplayName("Return payments by account id")
    void findPaymentsByAccountId() {
        try {
            UUID accountId = UUID.randomUUID();
            ArrayList<Payment> payments = new ArrayList<>(
                    List.of(
                            new Payment(UUID.randomUUID(), accountId, 10.0),
                            new Payment(UUID.randomUUID(), accountId, 20.0)
                    )
            );

            when(paymentRepository.findPaymentsByAccountId(any())).thenReturn(payments);

            assertEquals(2, paymentService.findPaymentsByAccountId(UUID.randomUUID()).size());
            verify(paymentRepository, times(1)).findPaymentsByAccountId(any());
        } catch (AccountNotFoundException exception) {
            fail("Should not thrown an exception");
        }
    }

    @Test
    @DisplayName("Fail to return all payments by account id")
    void failToFindPaymentsByPaymentId() throws AccountNotFoundException {

        when(paymentRepository.findPaymentsByAccountId(any())).thenThrow(new AccountNotFoundException());

        try {
            paymentService.findPaymentsByAccountId(UUID.randomUUID());
            fail("Should throw an exception");
        } catch (AccountNotFoundException exception) {
            assertEquals(404, exception.getStatus().value());
            assertEquals(0, exception.getMessage().compareTo("Account not found"));
            verify(paymentRepository, times(1)).findPaymentsByAccountId(any());
        }
    }

    @Test
    @DisplayName("Update a payment")
    void updatePayment() {
        try {
            Payment payment = new Payment(UUID.randomUUID(), UUID.randomUUID(), 10);
            when(paymentRepository.updatePayment(any())).thenReturn(payment);

            Payment updatedPayment = paymentService.updatePayment(payment);

            assertEquals(payment, updatedPayment);
            verify(paymentRepository, times(1)).updatePayment(any());
        } catch (PaymentNotFoundException exception) {
            fail("Should not thrown an exception");
        }
    }

    @Test
    @DisplayName("Fail to update payment")
    void failToUpdatePayment() throws PaymentNotFoundException {
        when(paymentRepository.updatePayment(any())).thenThrow(new PaymentNotFoundException());

        try {
            paymentService.updatePayment(new Payment(UUID.randomUUID(), UUID.randomUUID(), 20));
            fail("Should throw an exception");
        } catch (PaymentNotFoundException exception) {
            assertEquals(404, exception.getStatus().value());
            assertEquals(0, exception.getMessage().compareTo("Payment not found"));
            verify(paymentRepository, times(1)).updatePayment(any());
        }
    }

    @Test
    @DisplayName("Delete a Payment")
    void deleteAccount() {
        try {
            Mockito.doNothing().when(paymentRepository).deletePayment(any());

            paymentService.deletePayment(UUID.randomUUID());

            verify(paymentRepository, times(1)).deletePayment(any());
        } catch (PaymentNotFoundException exception) {
            fail("Should not thrown an exception");
        }
    }

    @Test
    @DisplayName("Fail to delete a Payment")
    void failToDeleteAccount() throws PaymentNotFoundException {
        Mockito.doThrow(
                new PaymentNotFoundException()
        ).when(paymentRepository).deletePayment(any());

        try {
            paymentService.deletePayment(UUID.randomUUID());

            fail("Should throw an exception");
        } catch (PaymentNotFoundException exception) {
            assertEquals(404, exception.getStatus().value());
            assertEquals(0, exception.getMessage().compareTo("Payment not found"));
            verify(paymentRepository, times(1)).deletePayment(any());
        }
    }

}
