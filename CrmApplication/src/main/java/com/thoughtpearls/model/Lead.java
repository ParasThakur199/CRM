package com.thoughtpearls.model;

import com.thoughtpearls.enums.LeadType;
import com.thoughtpearls.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`Lead`")
public class Lead extends AbstractClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String leadName;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private LeadType leadType;
    private LocalDate reminder_date;
    private String reminder_topic;
    @OneToMany(mappedBy = "lead")
    private List<Comments> comments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
