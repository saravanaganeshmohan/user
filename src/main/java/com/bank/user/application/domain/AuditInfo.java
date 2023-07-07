package com.bank.user.application.domain;

import com.google.gson.annotations.Expose;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditInfo {
    @Expose
    @CreatedDate
    @Column(name = "CREATED_DATE", updatable = false)
    private Instant createdDate;

    @Expose
    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @Expose
    @LastModifiedDate
    @Column(name = "MODIFIED_DATE")
    private Instant modifiedDate;

    @Expose
    @LastModifiedBy
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
}
