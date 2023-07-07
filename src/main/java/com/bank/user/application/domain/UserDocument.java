package com.bank.user.application.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class UserDocument extends AuditInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID userDocumentId;

    private String documentName;

    private String documentLocation;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statusId", referencedColumnName = "statusId")
    private Status status;

}
