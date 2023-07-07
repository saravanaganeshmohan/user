package com.bank.user.application.user.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRequest {

    private MultipartFile [] files;
}
