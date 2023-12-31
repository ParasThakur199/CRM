package com.thoughtpearls.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comments extends AuditableAbstractClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String textField;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="leadId")
    private Lead lead;
}
