package com.bank.user.application.user.request;

import lombok.Data;
import java.util.UUID;

@Data
public class DeleteRequest {
    private UUID userDocumentId;
}
