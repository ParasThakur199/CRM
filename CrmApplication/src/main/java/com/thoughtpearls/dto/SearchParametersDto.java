package com.thoughtpearls.dto;

import com.thoughtpearls.enums.LeadType;
import com.thoughtpearls.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SearchParametersDto {
    private String searchString;
    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private String sortOrder;
    private LeadType leadType;
    private Status status;
    private LocalDate reminderDate;
}
