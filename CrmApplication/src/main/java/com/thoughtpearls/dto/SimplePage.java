package com.thoughtpearls.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@JsonIgnoreProperties(
        {
                "pageable",
                "number",
                "numberOfElements",
                "first",
                "last",
                "empty",
                "sort"
        }
)

public class SimplePage<T> extends PageImpl<T> {

    public SimplePage(final List<T> content, final Long totalElements, final Pageable pageable) {
        super(content, pageable, totalElements);
    }

    public int getPage() {
        return getNumber();
    }

}
