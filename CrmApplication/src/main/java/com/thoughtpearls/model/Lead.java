package com.thoughtpearls.model;

import com.thoughtpearls.enums.LeadType;
import com.thoughtpearls.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Leads")
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
    private Date reminderDate;
    private String reminderTopic;
    @OneToMany(mappedBy = "lead")
    private List<Comments> comments;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
