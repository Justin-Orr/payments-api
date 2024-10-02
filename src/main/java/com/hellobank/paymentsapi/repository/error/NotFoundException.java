package com.hellobank.paymentsapi.repository.error;

import org.springframework.http.HttpStatusCode;

public class NotFoundException extends Exception {
    private final HttpStatusCode status;

    public NotFoundException(String message) {
        super(message);
        this.status = HttpStatusCode.valueOf(404);
    }

    public HttpStatusCode getStatus() {
        return status;
    }
}
