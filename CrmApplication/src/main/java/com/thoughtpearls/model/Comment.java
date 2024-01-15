package com.thoughtpearls.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Comment extends AuditableAbstractClass {

    @Column(columnDefinition = "TEXT")
    private String textField;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "leadId")
    private Lead lead;

}
