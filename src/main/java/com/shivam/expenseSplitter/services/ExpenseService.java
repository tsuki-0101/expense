package com.shivam.expenseSplitter.services;

import com.shivam.expenseSplitter.request.ExpenseRequest;
import com.shivam.expenseSplitter.response.ExpenseResponse;
import java.util.List;
import java.util.Map;

public interface ExpenseService {

    ExpenseResponse createExpense(ExpenseRequest ExpenseRequest);

    ExpenseResponse getExpenseById(Long expenseId);

    ExpenseResponse updateExpense(Long expenseId, ExpenseRequest ExpenseRequest);

    boolean deleteExpense(Long expenseId);

    List<ExpenseResponse> listExpenses();

    void settleExpense(Long expenseId);

    List<ExpenseResponse> listExpensesByUserId(Long userId);

    Map<String, Double> listUserBalances(Long userId);

    Double calculateBalanceBetweenTwoUsers(Long userId, Long otherUserId);
}
