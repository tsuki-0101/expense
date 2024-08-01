package com.shivam.expenseSplitter.models.split;

import com.shivam.expenseSplitter.models.SplitType;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PercentSplit extends Split{

    Map<String, Double> userIdToPercentMap;

    protected PercentSplit(Map<String, Double> userIdToPercentMap) {
        super(SplitType.PERCENT);
        this.userIdToPercentMap = userIdToPercentMap;
    }
}
