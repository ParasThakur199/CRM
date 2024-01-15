package com.thoughtpearls.dto.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchParameterRequestDto {
    private String searchString;
    private Integer pageNo;
    private Integer pageSize;
    private String sortBy="createdOn";
    private String sortOrder="desc";
    private String leadType;
    private String status;
    private Long userId;
}
