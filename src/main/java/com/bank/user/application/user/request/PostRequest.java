package com.bank.user.application.user.request;


import lombok.Data;

import java.util.UUID;

@Data
public class PostRequest {

    private Long userId;
    private String userDocumentId;
    private String description;
}
