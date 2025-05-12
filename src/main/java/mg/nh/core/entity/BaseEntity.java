package mg.nh.core.entity;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import mg.nh.core.provider.EntityIdProvider;
import mg.nh.core.util.IdGenerationService;

@MappedSuperclass
public abstract class BaseEntity implements EntityIdProvider {
    
    @Id
    private String id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Autowired
    private transient IdGenerationService idGenerationService;

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = idGenerationService.generateId(getIdPrefix());
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // To be implemented by subclasses
    public abstract String getIdPrefix();
}