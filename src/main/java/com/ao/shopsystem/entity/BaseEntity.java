package com.ao.shopsystem.entity;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The base entity for all the entities. includes three times: createdAt, updatedAt, deletedAt
 * Created by ao on 2018-09-21
 */
@Data
@NoArgsConstructor
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @Column
    private ZonedDateTime updatedAt;

    @Column
    private ZonedDateTime deletedAt;

    @PrePersist
    public void persist() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void update() {
        this.updatedAt = ZonedDateTime.now();
    }
}
