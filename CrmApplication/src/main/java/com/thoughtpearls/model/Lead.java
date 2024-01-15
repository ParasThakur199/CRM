package com.thoughtpearls.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "LeadInfo")
public class Lead extends AuditableAbstractClass {

    private String leadName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String link;

    @Temporal(TemporalType.DATE)
    private Date reminderDate;          //DATE(remainder_date)= :date

    private String reminderTopic;

    @OneToMany(mappedBy = "lead", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "lead", cascade = CascadeType.REMOVE)
    private List<LeadStatusHistory> leadStatusHistories;

    @OneToOne(mappedBy = "lead")
    private Rejection rejection;

    @ManyToOne
    @JoinColumn(name = "statusId")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "leadTypeId")
    private LeadType leadType;

}