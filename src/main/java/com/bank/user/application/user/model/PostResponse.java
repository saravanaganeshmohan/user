package com.bank.user.application.user.model;


import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private Long userId;
    private String description;
    private String userDocumentId;

}
