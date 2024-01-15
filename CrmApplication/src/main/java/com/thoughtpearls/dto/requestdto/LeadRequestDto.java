package com.thoughtpearls.dto.requestdto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class LeadRequestDto {
    private String leadName;
    private String leadDescription;
    private Long statusId;
    private Long leadTypeId;
    @Temporal(TemporalType.DATE)
    private Date reminderDate;
    private String reminderTopic;
    private String link;
}
