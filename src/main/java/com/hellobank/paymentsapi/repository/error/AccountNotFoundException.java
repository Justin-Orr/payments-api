package com.hellobank.paymentsapi.repository.error;

public class AccountNotFoundException extends NotFoundException {
    public AccountNotFoundException() {
        super("Account not found");
    }
}
