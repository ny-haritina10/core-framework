// File: BaseEntity.java
package nh.core.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import nh.core.services.SequenceService;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    protected String id;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    protected BaseEntity() 
    { }

    protected BaseEntity(String prefix, String entityName, SequenceService sequenceService) {
        this.id = createPrimaryKey(prefix, entityName, sequenceService);
    }

    // create primary key by combining prefix and sequence
    protected String createPrimaryKey(String prefix, String entityName, SequenceService sequenceService) {
        if (prefix == null || !prefix.matches("[A-Z]{3}")) {
            throw new IllegalArgumentException("prefix must be exactly 3 uppercase letters");
        }
        long sequence = sequenceService.getNextSequence(entityName);
        return String.format("%s%06d", prefix, sequence);
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}