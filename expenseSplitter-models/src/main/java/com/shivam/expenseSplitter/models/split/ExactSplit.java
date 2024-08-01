package com.shivam.expenseSplitter.models.split;

import com.shivam.expenseSplitter.models.SplitType;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExactSplit extends Split {

    Map<String, Double> userIdToAmountMap;

    protected ExactSplit(Map<String, Double> userIdToAmountMap) {
        super(SplitType.EXACT);
        this.userIdToAmountMap = userIdToAmountMap;
    }
}
