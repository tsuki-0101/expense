package com.shivam.expenseSplitter.utils;

import com.shivam.expenseSplitter.dao.entities.Expense;
import com.shivam.expenseSplitter.dao.entities.User;
import com.shivam.expenseSplitter.request.ExpenseRequest;
import com.shivam.expenseSplitter.request.UserRequest;
import com.shivam.expenseSplitter.response.ExpenseResponse;
import com.shivam.expenseSplitter.response.UserResponse;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransformationUtils {


    public static ExpenseRequest toExpenseRequest(Expense expense) {
        return ExpenseRequest.builder()
                       .description(expense.getDescription())
                       .amount(expense.getAmount())
                       .paidById(expense.getPaidBy().getId())
                       .sharedWithIds(expense.getSharedWith().stream().map(User::getId).collect(
                               Collectors.toSet()))
                       .isSettled(expense.isSettled())
                       .build();
    }

    public static ExpenseResponse toExpenseResponse(Expense expense) {
        return ExpenseResponse.builder()
                       .id(expense.getId())
                       .description(expense.getDescription())
                       .amount(expense.getAmount())
                       .paidById(expense.getPaidBy().getId())
                       .sharedWithIds(expense.getSharedWith().stream().map(User::getId).collect(
                               Collectors.toSet()))
                       .isSettled(expense.isSettled())
                       .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                       .id(user.getId())
                       .name(user.getName())
                       .email(user.getEmail())
                       .mobileNumber(user.getMobileNumber())
                       .build();
    }

    public static User toUserEntity(UserRequest userRequest) {
        return User.builder()
                       .email(userRequest.getEmail())
                       .mobileNumber(userRequest.getMobileNumber())
                       .name(userRequest.getName())
                       .build();
    }

}
