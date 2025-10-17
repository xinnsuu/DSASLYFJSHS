package com.xinnsuu.seatflow.model.enums;

public enum GradeLevel {
    ELEVEN("11"),
    TWELVE("12");

    private final String displayValue;

    GradeLevel(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}