package com.thoughtpearls.dto.responsedto;

import com.thoughtpearls.dto.responsedto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeadStatusHistoryResponseDto {
    private Long id;
    private String previousStatus;
    private String updatedStatus;
    private LocalDateTime dateTime;
    private UserResponseDto user;
    private long leadId;
}
