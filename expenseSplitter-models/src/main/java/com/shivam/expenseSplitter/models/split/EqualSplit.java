package com.shivam.expenseSplitter.models.split;

import com.shivam.expenseSplitter.models.SplitType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EqualSplit extends Split {

    protected EqualSplit() {
        super(SplitType.EQUAL);
    }
}
