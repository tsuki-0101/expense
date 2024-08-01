package com.shivam.expenseSplitter.services.impl;

import com.shivam.expenseSplitter.dao.entities.Expense;
import com.shivam.expenseSplitter.dao.entities.User;
import com.shivam.expenseSplitter.dao.repositories.ExpenseRepository;
import com.shivam.expenseSplitter.dao.repositories.UserRepository;
import com.shivam.expenseSplitter.exceptions.ResourceNotFoundException;
import com.shivam.expenseSplitter.request.ExpenseRequest;
import com.shivam.expenseSplitter.response.ExpenseResponse;
import com.shivam.expenseSplitter.services.ExpenseService;
import com.shivam.expenseSplitter.utils.TransformationUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ExpenseResponse createExpense(ExpenseRequest expenseRequest) {
        Expense expense = toExpense(expenseRequest);
        Expense savedExpense = expenseRepository.save(expense);
        return TransformationUtils.toExpenseResponse(savedExpense);
    }

    @Override
    public ExpenseResponse getExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                                  .orElseThrow(
                                          () -> new ResourceNotFoundException("Expense not found"));
        return TransformationUtils.toExpenseResponse(expense);
    }

    @Override
    public ExpenseResponse updateExpense(Long expenseId, ExpenseRequest expenseRequest) {
        Expense expense = expenseRepository.findById(expenseId)
                                  .orElseThrow(
                                          () -> new ResourceNotFoundException("Expense not found"));

        expense.setDescription(expenseRequest.getDescription());
        expense.setAmount(expenseRequest.getAmount());
        Optional<User> user = userRepository.findById(expenseRequest.getPaidById());
        expense.setPaidBy(user.get());
        Expense updatedExpense = expenseRepository.save(expense);
        return TransformationUtils.toExpenseResponse(updatedExpense);
    }

    @Override
    public boolean deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                                  .orElseThrow(
                                          () -> new ResourceNotFoundException("Expense not found"));
        expenseRepository.delete(expense);
        return true;
    }

    @Override
    public List<ExpenseResponse> listExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream().map(TransformationUtils::toExpenseResponse).toList();
    }

    @Override
    public void settleExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                                  .orElseThrow(
                                          () -> new ResourceNotFoundException("Expense not found"));
        expense.setSettled(true);
        expenseRepository.save(expense);
    }

    @Override
    public List<ExpenseResponse> listExpensesByUserId(Long userId) {
        boolean userExists = userRepository.existsById(userId);
        if (!userExists) {
            throw new ResourceNotFoundException("User not found");
        }
        List<Expense> allExpenses = expenseRepository.findExpensesByUserId(userId);
        return allExpenses.stream().map(TransformationUtils::toExpenseResponse)
                       .collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> listUserBalances(Long userId) {
        User user = userRepository.findById(userId)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "User not found with id: " + userId));
        List<Expense> expensesPaid = expenseRepository.findByPaidById(userId);
        List<Expense> sharedExpenses = expenseRepository.findBySharedWithId(userId);
        Map<String, Double> balances = new HashMap<>();
        for (Expense expense : expensesPaid) {
            double splitAmount = expense.getAmount() / (expense.getSharedWith().size()
                                                                + 1); // Including the payer
            for (User participant : expense.getSharedWith()) {
                balances.put(participant.getName(),
                             balances.getOrDefault(participant.getName(), 0.0) - splitAmount);
            }
        }
        for (Expense expense : sharedExpenses) {
            if (!expense.getPaidBy().getId().equals(userId)) { // Avoid double-counting
                double splitAmount = expense.getAmount() / (expense.getSharedWith().size()
                                                                    + 1); // Including the payer
                balances.put(expense.getPaidBy().getName(),
                             balances.getOrDefault(expense.getPaidBy().getName(), 0.0)
                                     + splitAmount);
            }
        }
        return balances;
    }

    @Override
    public Double calculateBalanceBetweenTwoUsers(Long userId, Long otherUserId) {
        if (!userRepository.existsById(userId) || !userRepository.existsById(otherUserId)) {
            throw new ResourceNotFoundException("One or both users not found");
        }
        Double netBalance = expenseRepository.findNetBalanceBetweenUsers(userId, otherUserId);
        return netBalance != null ? netBalance : 0.0;
    }

    private Expense toExpense(ExpenseRequest expenseRequest) {

        return Expense.builder()
                       .amount(expenseRequest.getAmount())
                       .description(expenseRequest.getDescription())
                       .isSettled(expenseRequest.getIsSettled())
                       .paidBy(userRepository
                                       .findById(expenseRequest.getPaidById())
                                       .orElseThrow(() -> new RuntimeException("User not found")))
                       .sharedWith(
                               expenseRequest.getSharedWithIds() != null
                                       && !expenseRequest.getSharedWithIds().isEmpty()
                                       ? expenseRequest.getSharedWithIds()
                                                 .stream().map(id -> userRepository
                                                                    .findById(id)
                                                                    .orElseThrow(() -> new RuntimeException("User not found")))
                                                 .collect(Collectors.toSet()) : null)
                       .build();
    }

    private void validateExpenseRequest(ExpenseRequest expenseRequest) {

    }

}
