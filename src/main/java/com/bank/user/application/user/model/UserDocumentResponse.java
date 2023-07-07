package com.bank.user.application.user.model;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDocumentResponse {

    private UUID userDocumentId;
    private String documentName;
    private String documentLocation;

}
