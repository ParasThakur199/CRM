package com.thoughtpearls.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rejection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "rejectionId"),
            inverseJoinColumns = @JoinColumn(name = "technologyId")
    )
    private List<Technology> technologies;

    @OneToOne
    @JoinColumn(name = "leadId")
    private Lead lead;

}
