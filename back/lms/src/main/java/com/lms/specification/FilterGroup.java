package com.lms.specification;

import java.util.List;

import lombok.Data;

@Data
public class FilterGroup {
    private LogicalOperator logicalOperator;
    private List<SearchFilter> filters;
}
