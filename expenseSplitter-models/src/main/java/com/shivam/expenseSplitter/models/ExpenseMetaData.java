package com.shivam.expenseSplitter.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExpenseMetaData {

    String name;
    String description;
}