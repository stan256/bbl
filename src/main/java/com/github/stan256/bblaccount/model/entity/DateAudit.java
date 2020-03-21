package com.github.stan256.bblaccount.model.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DateAudit implements Serializable {
    @CreatedDate
    @PastOrPresent
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @PastOrPresent
    @Column(nullable = false)
    private Instant updatedAt;
}
