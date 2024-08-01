package com.shivam.expenseSplitter.resources;

import com.shivam.expenseSplitter.request.ExpenseRequest;
import com.shivam.expenseSplitter.response.ExpenseResponse;
import com.shivam.expenseSplitter.services.ExpenseService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseResource {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(
            @Valid @RequestBody ExpenseRequest ExpenseRequest) {
        final var createdExpense = expenseService.createExpense(ExpenseRequest);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long expenseId,
                                                        @Valid @RequestBody ExpenseRequest ExpenseRequest) {
        final var updatedExpense = expenseService.updateExpense(expenseId, ExpenseRequest);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long expenseId) {
        final var expense = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(expense);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> listExpenses() {
        final var expenses = expenseService.listExpenses();
        return ResponseEntity.ok(expenses);
    }

    @PostMapping("/{expenseId}/settle")
    public ResponseEntity<Void> settleExpense(@PathVariable Long expenseId) {
        expenseService.settleExpense(expenseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseResponse>> listExpensesByUserId(@PathVariable Long userId) {
        final var expenses = expenseService.listExpensesByUserId(userId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/balances/{userId}")
    public ResponseEntity<Map<String, Double>> listUserBalances(@PathVariable Long userId) {
        Map<String, Double> balances = expenseService.listUserBalances(userId);
        return ResponseEntity.ok(balances);
    }

    @GetMapping("/balance/{userId}/{otherUserId}")
    public ResponseEntity<Double> calculateBalanceBetweenTwoUsers(@PathVariable Long userId,
                                                                  @PathVariable Long otherUserId) {
        Double balance = expenseService.calculateBalanceBetweenTwoUsers(userId, otherUserId);
        return ResponseEntity.ok(balance);
    }
}