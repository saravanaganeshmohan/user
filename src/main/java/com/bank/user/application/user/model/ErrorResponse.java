package com.bank.user.application.user.model;
import lombok.Data;

@Data
public class ErrorResponse{
    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }
}
