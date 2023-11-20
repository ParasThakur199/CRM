package com.thoughtpearls.model;

import com.thoughtpearls.enums.LeadType;
import com.thoughtpearls.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Leads")
public class Lead extends AuditableAbstractClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String leadName;
    private String description;
    private String link;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private LeadType leadType;
    private Date reminderDate;
    private String reminderTopic;
    @OneToMany(mappedBy = "lead",cascade = CascadeType.REMOVE)
    private List<Comments> comments;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "userId")
    private User user;
}