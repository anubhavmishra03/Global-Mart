package com.example.eCommerce.Response;

public class RegResponse {
    String message;
    Boolean error;

    public RegResponse(String message, Boolean error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getError() {
        return error;
    }
}
