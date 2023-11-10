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
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String textField;
    private String created_by;
    //    @Transient
//    private String updated_by;
//    @Transient
//    private LocalDateTime created_on;
//    @Transient
//    private LocalDateTime updated_on;
    @ManyToOne
    @JoinColumn(name="leadId")
    private Lead lead;
}
