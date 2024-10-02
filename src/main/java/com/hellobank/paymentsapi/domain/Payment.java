package com.hellobank.paymentsapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

public class Payment {
    @JsonProperty("payment_id")
    private UUID paymentId;
    @JsonProperty("account_id")
    private UUID accountId;
    private double amount;

    public Payment() {
        // Needed for Jackson to deserialize objects from the REST Controller
    }

    public Payment(UUID paymentId, UUID accountId, double amount) {
        this.paymentId = paymentId;
        this.accountId = accountId;
        this.amount = amount;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Double.compare(getAmount(), payment.getAmount()) == 0 && Objects.equals(getPaymentId(), payment.getPaymentId()) && Objects.equals(getAccountId(), payment.getAccountId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPaymentId(), getAccountId(), getAmount());
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + paymentId +
                ", amount=" + amount +
                '}';
    }
}
