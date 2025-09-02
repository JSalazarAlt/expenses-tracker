package com.suyos.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    
    FOOD("Food"),
    TRANSPORTATION("Transportation"),
    UTILITIES("Utilities"),
    ENTERTAINMENT("Entertainment"),
    HEALTHCARE("Healthcare"),
    EDUCATION("Education"),
    PERSONAL_CARE("Personal Care"),
    MISCELLANEOUS("Miscellaneous");

    private final String description;

}
