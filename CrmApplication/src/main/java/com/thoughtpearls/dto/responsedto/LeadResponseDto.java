package com.thoughtpearls.dto.responsedto;

import com.thoughtpearls.model.LeadType;
import com.thoughtpearls.model.Status;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeadResponseDto {
    private Long id;
    private String leadName;
    private String leadDescription;
    private UserResponseDto createdBy;
    private UserResponseDto updatedBy;
    private String link;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Status status;
    private LeadType leadType;
    private Date reminderDate;
    @Temporal(TemporalType.DATE)
    private String reminderTopic;


}
