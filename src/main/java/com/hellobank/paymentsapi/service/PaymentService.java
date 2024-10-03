package com.hellobank.paymentsapi.service;

import com.hellobank.paymentsapi.domain.Payment;
import com.hellobank.paymentsapi.repository.PaymentRepository;
import com.hellobank.paymentsapi.repository.error.AccountNotFoundException;
import com.hellobank.paymentsapi.repository.error.PaymentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.insertPayment(payment);
    }

    public List<Payment> getPayments() {
        return paymentRepository.getPayments();
    }

    public Payment findPaymentByPaymentId(UUID id) throws PaymentNotFoundException {
        return paymentRepository.findPaymentByPaymentId(id);
    }

    public List<Payment> findPaymentsByAccountId(UUID id) throws AccountNotFoundException {
        return paymentRepository.findPaymentsByAccountId(id);
    }

    public Payment updatePayment(Payment payment) throws PaymentNotFoundException {
        return paymentRepository.updatePayment(payment);
    }

    public void deletePayment(UUID paymentId) throws PaymentNotFoundException {
        paymentRepository.deletePayment(paymentId);
    }
}
