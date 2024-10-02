package com.hellobank.paymentsapi.repository;

import com.hellobank.paymentsapi.domain.Payment;
import com.hellobank.paymentsapi.repository.error.AccountNotFoundException;
import com.hellobank.paymentsapi.repository.error.PaymentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class PaymentsRepository {
    private static final ArrayList<Payment> payments = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(PaymentsRepository.class);

    public Payment insertPayment(Payment payment) {
        log.info("Inserting payment: {}", payment);
        payments.add(payment);
        return payment;
    }

    public List<Payment> getPayments() {
        log.info("Returning all payments: {} entities", payments.size());
        return payments;
    }

    public Payment findPaymentByPaymentId(UUID id) throws PaymentNotFoundException {
        for(Payment payment : payments) {
            if(payment.getPaymentId().compareTo(id) == 0) {
                return payment;
            }
        }
        log.info("Payment was not found during search for payment id: {}", id);
        throw new PaymentNotFoundException();
    }

    public List<Payment> findPaymentsByAccountId(UUID id) throws AccountNotFoundException {
        ArrayList<Payment> foundPayments = new ArrayList<>();
        for(Payment payment : payments) {
            if(payment.getAccountId().compareTo(id) == 0) {
                foundPayments.add(payment);
            }
        }
        if (foundPayments.isEmpty()) {
            log.info("No account with id: {}", id);
            throw new AccountNotFoundException();
        } else {
            log.info("{} entities found with account id: {}", foundPayments.size(), id);
            return foundPayments;
        }
    }

    public Payment updatePayment(Payment inputPayment) throws PaymentNotFoundException {
        for(Payment payment : payments) {
            if(payment.getPaymentId().compareTo(inputPayment.getPaymentId()) == 0) {
                log.info("Updating amount for payment {} from {} to {}", payment.getPaymentId(), payment.getAmount(), inputPayment.getAmount());
                payment.setAmount(inputPayment.getAmount());
                return inputPayment;
            }
        }
        log.info("No payment found found with id: {}", inputPayment.getPaymentId());
        throw new PaymentNotFoundException();
    }

    public void deletePayment(UUID id) throws PaymentNotFoundException {
        log.info("Deleting payment with id: {}", id);
        Payment payment = findPaymentByPaymentId(id);
        payments.remove(payment);
    }
}
