package com.bank.user.application.user.model;

import lombok.Data;

@Data
public class CommentResponse {

    private Long id;
    private Long postId;
    private Long userId;
    private String comment;
}
