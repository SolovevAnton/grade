package com.solovev.kiteshop.model.order;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {
    @CreatedDate
    @Column(name = "CREATED")
    private LocalDateTime created;

    @LastModifiedDate
    @Column(name = "UPDATED")
    private LocalDateTime updated;
}
