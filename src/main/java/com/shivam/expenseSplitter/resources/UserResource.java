package com.shivam.expenseSplitter.resources;

import com.shivam.expenseSplitter.request.UserRequest;
import com.shivam.expenseSplitter.response.UserResponse;
import com.shivam.expenseSplitter.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/expense-splitter/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest UserRequest) {
        var createdUser = userService.createUser(UserRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId,
                                                  @Valid @RequestBody UserRequest UserRequest) {
        var updatedUser = userService.updateUser(userId, UserRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        var response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> listUsers(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        var userResponses = userService.listUsers(pageable);
        return ResponseEntity.ok(userResponses);
    }

}
