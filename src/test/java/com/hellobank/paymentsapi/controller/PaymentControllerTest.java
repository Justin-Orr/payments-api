package com.hellobank.paymentsapi.controller;

import com.hellobank.paymentsapi.domain.Payment;
import com.hellobank.paymentsapi.repository.error.AccountNotFoundException;
import com.hellobank.paymentsapi.repository.error.PaymentNotFoundException;
import com.hellobank.paymentsapi.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class) // Focus on testing the controller layer
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc; // Used for performing HTTP requests

    @MockBean
    private PaymentService paymentService; // Mocking the service layer

    private final String baseUrl = "/v1/payments";

    @Test
    void createPayment() throws Exception {
        UUID paymentId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        double amount = 10;
        Payment payment = new Payment(paymentId, accountId, amount);

        when(paymentService.createPayment(any())).thenReturn(payment);

        mockMvc.perform(
                        post(baseUrl)
                                .queryParam("accountId", accountId.toString())
                                .queryParam("amount", String.valueOf(amount))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.payment.paymentId").value(paymentId.toString())) // Path must start with a . or [
                .andExpect(jsonPath("$.payment.accountId").value(accountId.toString()))
                .andExpect(jsonPath("$.payment.amount").value(amount));
        verify(paymentService, times(1)).createPayment(any());
    }

    @Test
    void getAllPayments() throws Exception {
        UUID paymentId1 = UUID.randomUUID();
        UUID accountId1 = UUID.randomUUID();
        double amount1 = 10;
        Payment payment1 = new Payment(paymentId1, accountId1, amount1);

        UUID paymentId2 = UUID.randomUUID();
        UUID accountId2 = UUID.randomUUID();
        double amount2 = 20;
        Payment payment2 = new Payment(paymentId2, accountId2, amount2);

        ArrayList<Payment> payments = new ArrayList<>();
        payments.add(payment1);
        payments.add(payment2);

        when(paymentService.getPayments()).thenReturn(payments);

        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata.totalCount").value(2)) // Path must start with a . or [
                .andExpect(jsonPath("$.payments[0].paymentId").value(paymentId1.toString()))
                .andExpect(jsonPath("$.payments[0].accountId").value(accountId1.toString()))
                .andExpect(jsonPath("$.payments[0].amount").value(amount1))
                .andExpect(jsonPath("$.payments[1].paymentId").value(paymentId2.toString()))
                .andExpect(jsonPath("$.payments[1].accountId").value(accountId2.toString()))
                .andExpect(jsonPath("$.payments[1].amount").value(amount2));
        verify(paymentService, times(1)).getPayments();
    }

    @Test
    void findPaymentsByAccountId() throws Exception {
        UUID paymentId1 = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        double amount1 = 10;
        Payment payment1 = new Payment(paymentId1, accountId, amount1);

        UUID paymentId2 = UUID.randomUUID();
        double amount2 = 20;
        Payment payment2 = new Payment(paymentId2, accountId, amount2);

        ArrayList<Payment> payments = new ArrayList<>();
        payments.add(payment1);
        payments.add(payment2);

        when(paymentService.findPaymentsByAccountId(accountId)).thenReturn(payments);

        mockMvc.perform(
                    get(baseUrl + "/accounts")
                            .queryParam("accountId", accountId.toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metadata.totalCount").value(2)) // Path must start with a . or [
                .andExpect(jsonPath("$.payments[0].paymentId").value(paymentId1.toString()))
                .andExpect(jsonPath("$.payments[0].accountId").value(accountId.toString()))
                .andExpect(jsonPath("$.payments[0].amount").value(amount1))
                .andExpect(jsonPath("$.payments[1].paymentId").value(paymentId2.toString()))
                .andExpect(jsonPath("$.payments[1].accountId").value(accountId.toString()))
                .andExpect(jsonPath("$.payments[1].amount").value(amount2));
        verify(paymentService, times(1)).findPaymentsByAccountId(accountId);
    }

    @Test
    void failToFindPaymentsByAccountId() throws Exception {
        when(paymentService.findPaymentsByAccountId(any())).thenThrow(new AccountNotFoundException());

        mockMvc.perform(
                        get(baseUrl + "/accounts")
                                .queryParam("accountId", UUID.randomUUID().toString())
                )
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Account not found"));
        verify(paymentService, times(1)).findPaymentsByAccountId(any());
    }

    @Test
    void findPaymentByPaymentId() throws Exception {
        UUID paymentId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        double amount = 10;
        Payment payment = new Payment(paymentId, accountId, amount);

        when(paymentService.findPaymentByPaymentId(any())).thenReturn(payment);

        mockMvc.perform(get(baseUrl + "/" + paymentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payment.paymentId").value(paymentId.toString())) // Path must start with a . or [
                .andExpect(jsonPath("$.payment.accountId").value(accountId.toString()))
                .andExpect(jsonPath("$.payment.amount").value(amount));
        verify(paymentService, times(1)).findPaymentByPaymentId(any());
    }

    @Test
    void failToFindPaymentByPaymentId() throws Exception {
        when(paymentService.findPaymentByPaymentId(any())).thenThrow(new PaymentNotFoundException());

        mockMvc.perform(get(baseUrl + "/" + UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Payment not found"));
        verify(paymentService, times(1)).findPaymentByPaymentId(any());
    }

    @Test
    void updatePayment() throws Exception {
        UUID paymentId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        double amount = 10;
        Payment payment = new Payment(paymentId, accountId, amount);

        when(paymentService.updatePayment(any())).thenReturn(payment);

        mockMvc.perform(
                        put(baseUrl + "/" + paymentId)
                                .queryParam("accountId", accountId.toString())
                                .queryParam("amount", String.valueOf(amount))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.payment.paymentId").value(paymentId.toString())) // Path must start with a . or [
                .andExpect(jsonPath("$.payment.accountId").value(accountId.toString()))
                .andExpect(jsonPath("$.payment.amount").value(amount));
        verify(paymentService, times(1)).updatePayment(any());
    }

    @Test
    void failToUpdatePayment() throws Exception {
        when(paymentService.updatePayment(any())).thenThrow(new PaymentNotFoundException());

        mockMvc.perform(
                        put(baseUrl + "/" + UUID.randomUUID())
                                .queryParam("accountId", UUID.randomUUID().toString())
                                .queryParam("amount", String.valueOf(10))
                ).andExpect(status().isNotFound())
                .andExpect(status().reason("Payment not found"));
        verify(paymentService, times(1)).updatePayment(any());
    }

    @Test
    void deletePayment() throws Exception {
        Mockito.doNothing().when(paymentService).deletePayment(any());

        mockMvc.perform(delete(baseUrl + "/" + UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully deleted payment"));
        verify(paymentService, times(1)).deletePayment(any());
    }

    @Test
    void failToDeletePayment() throws Exception {
        Mockito.doThrow(new PaymentNotFoundException()).when(paymentService).deletePayment(any());

        mockMvc.perform(delete(baseUrl + "/" + UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Payment not found"));
        verify(paymentService, times(1)).deletePayment(any());
    }
}