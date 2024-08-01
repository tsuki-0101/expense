package com.shivam.expenseSplitter.services.impl;

import com.shivam.expenseSplitter.dao.entities.User;
import com.shivam.expenseSplitter.dao.repositories.UserRepository;
import com.shivam.expenseSplitter.exceptions.ResourceNotFoundException;
import com.shivam.expenseSplitter.request.UserRequest;
import com.shivam.expenseSplitter.response.UserResponse;
import com.shivam.expenseSplitter.services.UserService;
import com.shivam.expenseSplitter.utils.TransformationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest UserRequest) {
        User user = TransformationUtils.toUserEntity(UserRequest);
        User savedUser = userRepository.save(user);
        return TransformationUtils.toUserResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return TransformationUtils.toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long userId, UserRequest UserRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(UserRequest.getName());
        user.setEmail(UserRequest.getEmail());
        User updatedUser = userRepository.save(user);
        return TransformationUtils.toUserResponse(updatedUser);
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
        return true;
    }

    @Override
    public Page<UserResponse> listUsers(Pageable pageable) {
        Page<User> users = userRepository.findAllByOrderByNameAsc(pageable);
        return users.map(TransformationUtils::toUserResponse);
    }

}