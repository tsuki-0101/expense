package com.shivam.expenseSplitter.services;

import com.shivam.expenseSplitter.request.UserRequest;
import com.shivam.expenseSplitter.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse createUser(UserRequest UserRequest);

    UserResponse getUserById(Long userId);

    UserResponse updateUser(Long userId, UserRequest UserRequest);

    boolean deleteUser(Long userId);

    Page<UserResponse> listUsers(Pageable pageable);
}
