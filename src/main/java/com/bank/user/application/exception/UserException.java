package com.bank.user.application.exception;

import com.bank.user.application.user.model.ErrorResponse;
import com.google.gson.Gson;

public class UserException extends RuntimeException{

    public UserException(ErrorResponse message){
        super(new Gson().toJson(message));
    }
}
