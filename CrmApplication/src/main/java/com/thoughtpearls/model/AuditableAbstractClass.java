package com.thoughtpearls.model;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AuditableAbstractClass {
    private long createdBy;
    private long updatedBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
