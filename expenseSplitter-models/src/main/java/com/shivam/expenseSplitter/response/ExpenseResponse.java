package com.shivam.expenseSplitter.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExpenseResponse {

    private Long id;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount must be positive")
    private Double amount;

    @NotNull(message = "Payer is required")
    private Long paidById;

    @NotNull(message = "Shared with users is required")
    private Set<Long> sharedWithIds;

    @NotNull(message = "Settlement status is required")
    private Boolean isSettled;

}
