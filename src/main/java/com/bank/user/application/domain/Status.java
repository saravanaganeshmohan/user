package com.bank.user.application.domain;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Status extends AuditInfo implements Serializable {

    @Id
    private Long statusId;
    private String name;

    public Status(){}
    public Status(Long statusId){
        this.statusId =  statusId;
    }

    public Status(Long statusId, String name) {
        this.statusId = statusId;
        this.name = name;
    }
}
