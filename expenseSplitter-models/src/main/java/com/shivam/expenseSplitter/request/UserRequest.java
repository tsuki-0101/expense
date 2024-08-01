package com.shivam.expenseSplitter.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserRequest {

    @NotBlank
    @Email
    String email;

    @NotBlank
    String mobileNumber;

    @NotBlank
    String name;

}
