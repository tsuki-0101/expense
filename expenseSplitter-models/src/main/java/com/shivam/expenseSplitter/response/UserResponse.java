package com.shivam.expenseSplitter.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {

    Long id;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String mobileNumber;

    @NotBlank
    String name;

}