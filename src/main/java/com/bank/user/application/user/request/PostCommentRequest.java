package com.bank.user.application.user.request;

import lombok.Data;

@Data
public class PostCommentRequest {

    private String comment;
    private Long UserId;
    private String postId;
}
