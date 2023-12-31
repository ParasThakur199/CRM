package com.thoughtpearls.dto;

import com.thoughtpearls.enums.LeadType;
import com.thoughtpearls.enums.Status;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class LeadResponseDto {
    private long id;
    private String leadName;
    private String leadDescription;
    private String createdBy;
    private String updatedBy;
    private String link;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Status leadStatus;
    private LeadType leadType;
    private Date reminderDate;
    @Temporal(TemporalType.DATE)
    private String reminderTopic;
}
