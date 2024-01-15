package com.thoughtpearls.dto.requestdto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RejectionRequestDto {
    private String reason;
    private List<Long> technologies;

}
