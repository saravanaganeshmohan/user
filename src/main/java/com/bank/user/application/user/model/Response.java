package com.bank.user.application.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {
    private String message;
    private int httpStatus;
    private T payload;
}
