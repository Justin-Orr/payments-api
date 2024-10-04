package com.hellobank.paymentsapi.controller.domain;

import com.hellobank.paymentsapi.domain.Payment;

import java.util.List;

public class PaymentListResponse {
    private List<Payment> payments;
    private MetaData metadata;

    public PaymentListResponse(List<Payment> payments) {
        this.payments = payments;
        this.metadata = new MetaData(payments.size());
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
        this.metadata = new MetaData(payments.size());
    }

    public MetaData getMetadata() {
        return metadata;
    }

    public record MetaData(int totalCount) {}
}
