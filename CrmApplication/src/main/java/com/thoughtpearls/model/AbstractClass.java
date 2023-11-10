package com.thoughtpearls.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractClass {
    private String created_by;
    private String updated_by;
    private LocalDateTime created_on;
    private LocalDateTime updated_on;
}
