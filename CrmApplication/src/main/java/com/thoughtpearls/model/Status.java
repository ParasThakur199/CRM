package com.thoughtpearls.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String code;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(unique = true,nullable = false)
    private Long indexNumber;


}
