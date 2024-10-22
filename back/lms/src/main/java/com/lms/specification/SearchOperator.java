package com.lms.specification;

import lombok.Getter;

@Getter
public enum SearchOperator {
    GT("GREATER_THAN"),
    GTE("GREATER_THAN_OR_EQUAL"),
    LT("LESS_THAN"),
    LTE("LESS_THAN_OR_EQUAL"),
    EQUAL("EQUAL"),
    NOT_EQUAL("NOT_EQUAL"),
    STARTS_WITH("STARTS_WITH"),
    ENDS_WITH("ENDS_WITH"),
    LIKE("LIKE"),
    LIKE_IGNORE_CASE("LIKE_IGNORE_CASE"),
    EMPTY_FILTER("EMPTY_FILTER"),
    IN("IN"),
    BETWEEN("BETWEEN");

    SearchOperator(String operation) {
        this.operation = operation;
    }

    private final String operation;

}
