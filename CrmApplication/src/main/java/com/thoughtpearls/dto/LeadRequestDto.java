package com.thoughtpearls.dto;

import com.thoughtpearls.enums.LeadType;
import com.thoughtpearls.enums.Status;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LeadRequestDto {
    private String leadName;
    private String leadDescription;
    private Status leadStatus;
    private LeadType leadType;
    @Temporal(TemporalType.DATE)
    private Date reminderDate;
    private String reminderTopic;
    private String link;
}
