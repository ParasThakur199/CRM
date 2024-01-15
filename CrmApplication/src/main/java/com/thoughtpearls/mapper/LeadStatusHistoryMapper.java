package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.responsedto.LeadStatusHistoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.thoughtpearls.model.LeadStatusHistory;

@Mapper(componentModel = "spring")
public interface LeadStatusHistoryMapper {
    @Mapping(source = "lead.id", target = "leadId")
    LeadStatusHistoryResponseDto entityToDto(LeadStatusHistory leadStatusHistory);
}