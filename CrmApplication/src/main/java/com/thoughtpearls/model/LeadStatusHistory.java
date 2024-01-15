package com.thoughtpearls.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeadStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String previousStatus;
    private String updatedStatus;
    private LocalDateTime dateTime;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JsonIgnore
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "leadId")
    @JsonIgnore
    private Lead lead;
}
