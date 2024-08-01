package com.shivam.expenseSplitter.models.split;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.shivam.expenseSplitter.models.SplitType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "splitType")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "EQUAL", value = EqualSplit.class),
        @JsonSubTypes.Type(name = "PERCENT", value = ExactSplit.class),
        @JsonSubTypes.Type(name = "EXACT", value = ExactSplit.class),
})
public abstract class Split {

    @NotNull
    SplitType splitType;

    protected Split(SplitType splitType) {
        this.splitType = splitType;
    }

}

