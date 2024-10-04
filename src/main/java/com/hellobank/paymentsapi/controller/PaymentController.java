package com.hellobank.paymentsapi.controller;

import com.hellobank.paymentsapi.controller.domain.PaymentListResponse;
import com.hellobank.paymentsapi.controller.domain.PaymentResponse;
import com.hellobank.paymentsapi.domain.Payment;
import com.hellobank.paymentsapi.repository.error.AccountNotFoundException;
import com.hellobank.paymentsapi.repository.error.PaymentNotFoundException;
import com.hellobank.paymentsapi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("v1/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestParam(name = "accountId") UUID accountId,
            @RequestParam(name = "amount") double amount
    ) {
        Payment payment = paymentService.createPayment(new Payment(UUID.randomUUID(), accountId, amount));
        return ResponseEntity.status(201).body(new PaymentResponse(payment));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<PaymentListResponse> getAllPayments() {
        List<Payment> payments = paymentService.getPayments();
        return ResponseEntity.status(200).body(new PaymentListResponse(payments));
    }

    @GetMapping(path = "/accounts", produces = "application/json")
    public ResponseEntity<PaymentListResponse> findPaymentsByAccountId(@RequestParam("accountId") UUID accountId) {
        try {
            List<Payment> payments = paymentService.findPaymentsByAccountId(accountId);
            return ResponseEntity.status(200).body(new PaymentListResponse(payments));
        } catch (AccountNotFoundException exception) {
            throw new ResponseStatusException(exception.getStatus(), exception.getMessage(), exception);
        }
    }

    @GetMapping(path = "/{paymentId}", produces = "application/json")
    public ResponseEntity<PaymentResponse> findPaymentByPaymentId(@PathVariable("paymentId") UUID paymentId) {
        try {
            Payment payment = paymentService.findPaymentByPaymentId(paymentId);
            return ResponseEntity.status(200).body(new PaymentResponse(payment));
        } catch (PaymentNotFoundException exception) {
            throw new ResponseStatusException(exception.getStatus(), exception.getMessage(), exception);
        }
    }

    @PutMapping(path = "/{paymentId}", produces = "application/json")
    public ResponseEntity<PaymentResponse> updatePayment(
            @PathVariable("paymentId") UUID paymentId,
            @RequestParam(name = "accountId") UUID accountId,
            @RequestParam(name = "amount") double amount
    ) {
        try {
            Payment payment = paymentService.updatePayment(new Payment(paymentId, accountId, amount));
            return ResponseEntity.status(200).body(new PaymentResponse(payment));
        } catch (PaymentNotFoundException exception) {
            throw new ResponseStatusException(exception.getStatus(), exception.getMessage(), exception);
        }
    }

    @DeleteMapping(path = "/{paymentId}", produces = "application/json")
    public ResponseEntity<PaymentResponse> deletePayment(@PathVariable("paymentId") UUID paymentId) {
        try {
            paymentService.deletePayment(paymentId);
            return ResponseEntity.status(200).body(new PaymentResponse("Successfully deleted payment"));
        } catch (PaymentNotFoundException exception) {
            throw new ResponseStatusException(exception.getStatus(), exception.getMessage(), exception);
        }
    }

}
